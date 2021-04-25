import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import java.util.Arrays;

/**
 * @author Nathan Jesus
 */
public class CorrelacaoNormalizada {

    // Indice de cada banda no pixel
    public static final int RED = 2;
    public static final int GREEN = 1;
    public static final int BLUE = 0;


    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imagem = Imgcodecs.imread("./img/woman.png");
        Mat mascara = Imgcodecs.imread("./img/woman_eye.png");

        double[][] v = {
                {1, 3, 7},
                {5, 3, 1},
                {2, 4, 0},
                {4, 4, 4}
        };
        double[][] h = {
                {5, 3, 1},
                {2, 4, 0},
                {4, 4, 4}
        };

        double[][] resultado = calculaCorrelacaoNormBanda(v, h, v[0].length, v.length, h[0].length, h.length);

        System.out.println(Arrays.deepToString(calculaCorrelacaoNormBanda(v, h, v[0].length, v.length, h[0].length, h.length)));

        // Tamanho das imagens
        int widthV = imagem.width();
        int heightV = imagem.height();

        int widthH = mascara.width();
        int heightH = mascara.height();

        // Arrays R, G e B da imagem
        double[][] imagemR = retornaMatrizBanda(imagem, RED, widthV, heightV);
        double[][] imagemG = retornaMatrizBanda(imagem, GREEN, widthV, heightV);
        double[][] imagemB = retornaMatrizBanda(imagem, BLUE, widthV, heightV);

        // Arrays R, G e B da máscara
        double[][] mascaraR = retornaMatrizBanda(mascara, RED, widthH, heightH);
        double[][] mascaraG = retornaMatrizBanda(mascara, GREEN, widthH, heightH);
        double[][] mascaraB = retornaMatrizBanda(mascara, BLUE, widthH, heightH);

//        System.out.println(Arrays.deepToString(calculaCorrelacaoNormBanda(imagemR, mascaraR, widthV, heightV, widthH, heightH)));


        // Array com a média das correlações de R, G e B da imagem de entrada
        int[][] s = new int[widthV][heightV];


        Imgcodecs.imwrite("img/result/corrxnorm.jpg", imagem);
    }

    // Retorna um array com o resultado da normalizacao em todos os pontos
    public static double[][] calculaCorrelacaoNormBanda(
            double[][] bandaImagem, double[][] bandaMascara, int widthV, int heightV, int widthH, int heightH) {

        double[][] resultado = new double[heightV][widthV];

        for(int i = 0; i < heightV; i++) {
            for (int j = 0; j < widthV; j++) {
                resultado[i][j] = calculaCorrelacaoNormPixel(bandaImagem, bandaMascara, widthH, heightH, i, j);
            }
        }

        return resultado;
    }

    // Calcula o r resultante da correlacao normalizada para um ponto
    public static double calculaCorrelacaoNormPixel(
            double[][] v, double[][] h, int widthH, int heightH, int x, int y) {
        // Coordenadas X e Y do pivô da máscara (de tamanho impar);
        int inicioX = x - (heightH - 1)/2;
        int inicioY = y - (widthH - 1)/2;

        if (widthH % 2 == 0) // se a mascara tem largura par
            inicioX = x - heightH/2;
        if(heightH % 2 == 0) // se a mascara tem altura par
            inicioY = y - widthH/2;

        double mediaV = calculaMediaMatriz(v, widthH, heightH, inicioX, inicioY);
        double mediaH = calculaMediaMatriz(h, widthH, heightH, 0, 0);

        // diferencaV = vetorV - mediaV, onde mediaV eh uma constante
        double[][] diferencaV = calculaDiferencaVetorConstante(v, mediaV, widthH, heightH, inicioX, inicioY);
        double[][] diferencaH = calculaDiferencaVetorConstante(h, mediaH, widthH, heightH, 0, 0);

        // Norma do vetor diverencaV
        double normaV = calculaNormaVetor(diferencaV, widthH, heightH);
        double normaH = calculaNormaVetor(diferencaH, widthH, heightH);

        double resultado = calculaProdutoInternoMatrizes(
                diferencaV, diferencaH, widthH, heightH) / (normaV * normaH);

        return resultado;
    }


    public static double calculaMediaMatriz(
            double[][] v, int widthH, int heightH, int inicioX, int inicioY) {
        double soma = 0;
        int widthV = v[0].length;
        int heightV = v.length;

        for(int i = 0; i < heightH; i++) {
            int indiceX = i + inicioX;
            if(indiceX < 0 || indiceX > heightV - 1){ // Extensao c/ zeros
                soma += 0;
                continue;
            }
           for(int j = 0; j < widthH; j++) {
               int indiceY = j + inicioY;
               if(indiceY < 0 || indiceY > widthV - 1) // extensao c/ zeros
                   soma += 0;
               else
                   soma += v[indiceX][indiceY];
           }
        }

        return soma/(widthH * heightH);
    }

    //Calcula o produto interno entre dois vetores
    public static double calculaProdutoInternoMatrizes(double[][] v, double[][] h, int widthH, int heightH) {
        double resultado = 0;

        //Calcula o produto interno ponto a ponto
        for (int i = 0; i < heightH; i++){
            for (int j = 0; j < widthH; j++) {
                resultado += v[i][j] * h[i][j];
            }
        }

        return resultado;
    }

    //Diminui as coordenadas do vetor por uma constante
    public static double[][] calculaDiferencaVetorConstante(
            double[][] vetor, double constante, int width, int height, int inicioX, int inicioY) {
        double[][] resultado = new double[height][width];
        int widthV = vetor[0].length;
        int heightV = vetor.length;

        for (int i = 0; i < height; i++) {
            int indiceX = i + inicioX;
            for (int j = 0; j < width; j++) {
                int indiceY = j + inicioY;
                if(indiceX < 0 || indiceX > heightV - 1 || indiceY < 0  || indiceY > widthV - 1)
                    resultado[i][j] = 0 - constante;
                else
                    resultado[i][j] = vetor[indiceX][indiceY] - constante;

            }
        }


        return resultado;
    }

    // Calcula a normal de um vetor
    public static double calculaNormaVetor(double[][] vetor, int width, int height)  {

        double resultado = 0;

        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                resultado += Math.pow(vetor[i][j], 2);
            }
        }

        resultado = Math.pow(resultado, 0.5);

        return resultado;
    }

    // Retorna um array com a banda desejada (R, G ou B) de uma imagem
    public static double[][] retornaMatrizBanda(Mat img, int bandIndex, int width, int height) {
        double[][] resultado = new double[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultado[i][j] = img.get(i, j)[bandIndex];
            }
        }

        return resultado;
    }

}

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
                {1, 3, 7, 4},
                {5, 3, 1, 9},
                {0, 9, 7, 5},
                {12, 3, 4, 1}

        };
        double[][] h = {
                {1, 9},
                {7, 5}
        };

//        double[][] resultado = calculaCorrelacaoNormBanda(v, h, v[0].length, v.length, h[0].length, h.length);

//        System.out.println(Arrays.deepToString(calculaCorrelacaoNormBanda(v, h, v[0].length, v.length, h[0].length, h.length)));

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

        double[][] correlacaoR = calculaCorrelacaoNormBanda(imagemR, mascaraR, widthV, heightV, widthH, heightH);
        double[][] correlacaoG = calculaCorrelacaoNormBanda(imagemG, mascaraG, widthV, heightV, widthH, heightH);
        double[][] correlacaoB = calculaCorrelacaoNormBanda(imagemB, mascaraB, widthV, heightV, widthH, heightH);

        // Array com a média das correlações de R, G e B da imagem de entrada
        double[] resultado = calculaMediaTresMatrizes(correlacaoR, correlacaoG, correlacaoB);


        imprimeCorrelacao(imagem, (int) resultado[0], (int) resultado[1], widthH, heightH);

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

        if (heightH % 2 == 0) // se a mascara tem largura par
            inicioX = x - heightH/2;
        if(widthH % 2 == 0) // se a mascara tem altura par
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
            double[][] vetor, double constante, int widthH, int heightH, int inicioX, int inicioY) {
        double[][] resultado = new double[heightH][widthH];
        int widthV = vetor[0].length;
        int heightV = vetor.length;

        for (int i = 0; i < heightH; i++) {
            int indiceX = i + inicioX;
            for (int j = 0; j < widthH; j++) {
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

    // Calcula a média entre 3 pontos da mesma coordenada em 3 matrizes te largura width e altura height
    public static double[] calculaMediaTresMatrizes(double[][] m1, double[][] m2, double[][] m3) {
        //Armazena as coordenadas x e y do valor mais próximo de 1 e o seu valor ao fim da média;
        double[] resultado = new double[3];
        double maior = -20;
        int height = m2.length;
        int width = m2[0].length;

        for (int i = 0; i < height; i++) {
            for(int j = 0; j < width; j++) {
                double media = (m1[i][j] + m2[i][j] + m3[i][j]) / 3;
                 if(maior < media) {
                     resultado[0] = i;
                     resultado[1] = j;
                     resultado[2] = media;
                     maior = media;
                 }
            }
        }

        return resultado;
    }


    // Imprime a marca verde na correlação
    public static void imprimeCorrelacao(Mat imagem, int x, int y, double widthH, double heightH) {
        // Coordenadas X e Y do pivô da máscara (de tamanho impar);
        int inicioX = (int) (x - (heightH - 1)/2);
        int inicioY = (int) (y - (widthH - 1)/2);

        if (heightH % 2 == 0) // se a mascara tem largura par
            inicioX = (int) (x - heightH/2);
        if(widthH % 2 == 0) // se a mascara tem altura par
            inicioY = (int) (y - widthH/2);

        double[] green = {0, 255, 0};

        for (int i = inicioY; i < inicioY + widthH; i++) {
            imagem.put(inicioX, i, green);
            imagem.put(inicioX + (int) heightH, i, green);
        }
        for (int i = inicioX; i < inicioX + heightH; i++) {
            imagem.put(i, inicioY, green);
            imagem.put(i, inicioY + (int) widthH, green);
        }

        Imgcodecs.imwrite("img/result/normxcorr.jpg", imagem);
    }

}

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author Nathan Jesus
 */
public class CorrelacaoNormalizada {

    public static void main(String[] args){

        double[][] v = {
                {1, 3, 7},
                {5, 3, 1},
                {2, 4, 0}
        };
        double[][] h = {
                {1, 4, 1},
                {3, 6, 6},
                {5, 0, 2}
        };

        int larguraV = v[0].length;
        int alturav = v.length;

        int larguraH = h[0].length;
        int alturaH = h.length;

        System.out.println("largura v: " + v[0].length);
        System.out.println("altura v: " + v.length);

        System.out.println(calculaCorrelacaoNormalizada(v, h, larguraH, alturaH));

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img1 = Imgcodecs.imread("./img/woman.png");
        Mat img2 = Imgcodecs.imread("./img/woman_eye.png");

        // Tamanho das imagens
        int height1 = img1.height();
        int width1 = img1.width();

        int height2 = img1.height();
        int width2 = img2.width();

        // Coordenadas X e Y do pivô da máscara (de tamanho impar);
        int pivoX = (width2 - 1)/2;
        int pivoY = (height2 - 1)/2;


        // Array com a média das correlações de R, G e B da imagem de entrada
        int[][] s = new int[width1][height1];


        Imgcodecs.imwrite("img/result/corrxnorm.jpg", img1);
    }

    // Calcula o r resultante da correlacao normalizada
    public static double calculaCorrelacaoNormalizada(double[][] v, double[][] h, int widthH, int heightH) {

        double mediaV = calculaMediaMatriz(v, widthH, heightH);
        double mediaH = calculaMediaMatriz(h, widthH, heightH);

        // diferencaV = vetorV - mediaV, onde mediaV eh uma constante
        double[][] diferencaV = calculaDiferencaVetorConstante(v, mediaV, widthH, heightH);
        double[][] diferencaH = calculaDiferencaVetorConstante(h, mediaH, widthH, heightH);

        // Norma do vetor diverencaV
        double normaV = calculaNormaVetor(diferencaV, widthH, heightH);
        double normaH = calculaNormaVetor(diferencaH, widthH, heightH);

        double resultado = calculaProdutoInternoMatrizes(diferencaV, diferencaH, widthH, heightH) / (normaV * normaH);

        return resultado;
    }


    public static double calculaMediaMatriz(double[][] v, int widthH, int heightH) {
        double soma = 0;

        for(int i = 0; i < heightH; i++) {
           for(int j = 0; j < widthH; j++) {
               soma += v[i][j];
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
    public static double[][] calculaDiferencaVetorConstante(double[][] vetor, double constante, int width, int height) {
        double[][] resultado = new double[height][width];

        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultado[i][j] = vetor[i][j] - constante;
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

}

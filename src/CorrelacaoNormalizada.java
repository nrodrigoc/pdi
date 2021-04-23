import commons.RGB;
import executer.Correlacao;
import executer.FiltroTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoNormalizada {

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img1 = Imgcodecs.imread("./img/woman.png");
        Mat img2 = Imgcodecs.imread("./img/woman_eye.png");

        // Tamanho das imagens
        int height1 = img1.height();
        int width1 = img1.width();

        int height2 = img1.height();
        int width2 = img2.width();

        // Coordenadas X e Y do pivô da máscara (de tamanho impar);
        int pivoX = (width2 + 1)/2;
        int pivoY = (height2 + 1)/2;


        // Array com a média das correlações de R, G e B da imagem de entrada
        int[][] s = new int[width1][height1];


        Imgcodecs.imwrite("img/result/newGrayScale.jpg", img1);
    }


    public double calculaCorrelacao(double[] v, double[] u) {
        double mediaV = calculaMediaArray(v);
        double mediaU = calculaMediaArray(u);

        // diferencaV = vetorV - mediaV
        double[] diferencaV = calculaDiferencaVetor(v, mediaV);
        double[] diferencaU = calculaDiferencaVetor(u, mediaU);





        return 0;
    }


    public double calculaMediaArray(double[] v) {
        double soma = 0;

        for (double value : v) {
            soma += value;
        }

        return soma/(v.length);
    }

    //Diminui as coordenadas do vetor por uma constante
    public double[] calculaDiferencaVetor(double[] vetor, double constante) {
        double[] resultado = new double[vetor.length];

        for (int i = 0; i < vetor.length; i++) {
            resultado[i] = vetor[i] - constante;
        }

        return resultado;
    }

    // Calcula a normal de um vetor
    public double calculaModuloVetor(double[] vetor)  {


        return 0;
    }

}

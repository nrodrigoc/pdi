import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

/**
 * @author Nathan Jesus
 */
public class CorrelacaoNormalizada {

    public static void main(String[] args){

        double[] v1 = {4, 1, 3};
        double[] v2 = {3, 7, 5};

        System.out.println(calculaCorrelacaoNormalizada(v1, v2));

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

    // Calcula o r resultante da correlacao normalizada
    public static double calculaCorrelacaoNormalizada(double[] v, double[] u) {
        double mediaV = calculaMediaArray(v);
        double mediaU = calculaMediaArray(u);

        // diferencaV = vetorV - mediaV, onde mediaV eh uma constante
        double[] diferencaV = calculaDiferencaVetorConstante(v, mediaV);
        double[] diferencaU = calculaDiferencaVetorConstante(u, mediaU);

        // Norma do vetor diverencaV
        double normaV = calculaNormaVetor(diferencaV);
        double normaU = calculaNormaVetor(diferencaU);

        double resultado = calculaProdutoInternoVetores(diferencaV, diferencaU) / (normaV * normaU);

        return resultado;
    }


    public static double calculaMediaArray(double[] v) {
        double soma = 0;

        for (double value : v) {
            soma += value;
        }

        return soma/(v.length);
    }

    //Calcula o produto interno entre dois vetores
    public static double calculaProdutoInternoVetores(double[] v, double[] u) {
        double resultado = 0;

        for(int i = 0; i < v.length; i++) {
            resultado += v[i] * u[i];
        }

        return resultado;
    }

    //Diminui as coordenadas do vetor por uma constante
    public static double[] calculaDiferencaVetorConstante(double[] vetor, double constante) {
        double[] resultado = new double[vetor.length];

        for (int i = 0; i < vetor.length; i++) {
            resultado[i] = vetor[i] - constante;
        }

        return resultado;
    }

    // Calcula a normal de um vetor
    public static double calculaNormaVetor(double[] vetor)  {

        double resultado = 0;

        for (double v : vetor) {
            resultado += Math.pow(v, 2);
        }

        resultado = Math.pow(resultado, 0.5);

        return resultado;
    }

}

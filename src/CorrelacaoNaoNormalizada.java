import executer.Correlacao;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoNaoNormalizada {

    // Indice de cada banda no pixel
    public static final int RED = 2;
    public static final int GREEN = 1;
    public static final int BLUE = 0;

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat imagem = Imgcodecs.imread("./img/woman.png");
        Mat mascara = Imgcodecs.imread("./img/woman_eye.png");

        Correlacao correlacao = new Correlacao();

        int widthH = mascara.width();
        int heightH = mascara.height();

        // Arrays R, G e B da máscara
        double[][] mascaraR = retornaMatrizBandaSobreResolucao(mascara, RED, widthH, heightH);
        double[][] mascaraG = retornaMatrizBandaSobreResolucao(mascara, GREEN, widthH, heightH);
        double[][] mascaraB = retornaMatrizBandaSobreResolucao(mascara, BLUE, widthH, heightH);

        double[][] mediaRGB = getMediaTresMatrizes(mascaraR, mascaraG, mascaraB);

        Mat copia = correlacao.correlacao(imagem, mediaRGB);

        double[] coordenadas = getCoordenadasDoValorMaisBranco(copia);

        CorrelacaoNormalizada.imprimeCorrelacao(imagem, (int) coordenadas[0], (int) coordenadas[1], widthH, heightH, "nao_normalizada");
    }

    public static double[][] getMediaTresMatrizes(double[][] m1, double[][] m2, double[][] m3) {
        int height = m2.length;
        int width = m2[0].length;

        double[][] resultado = new double[height][width];

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultado[i][j] = (m1[i][j] + m2[i][j] + m3[i][j]) / 3;
            }
        }

        return resultado;
    }

    public static double[] getCoordenadasDoValorMaisBranco(Mat imagem) {
        double[] resultado = new double[2];
        double maior = -500;

        int width = imagem.width();
        int height = imagem.height();

        for(int i = 0; i < height; i++){
            for (int j = 0; j < width; j++) {
                double media = (imagem.get(i, j)[0] + imagem.get(i, j)[1] + imagem.get(i, j)[2]) / 3;
                if(maior < media) {
                    maior = media;
                    resultado[0] = i;
                    resultado[1] = j;
                }
            }
        }

        return resultado;
    }

    // Retorna um array com a banda desejada (R, G ou B) de uma imagem divido pela sua resolução
    public static double[][] retornaMatrizBandaSobreResolucao(Mat img, int bandIndex, int width, int height) {
        double[][] resultado = new double[height][width];

        double constante = 1 / ((double) width * height);

        for(int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                resultado[i][j] = img.get(i, j)[bandIndex] * constante;
            }
        }

        return resultado;
    }

}

import executer.Correlacao;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoTesteSobel {

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        Mat img = Imgcodecs.imread("./img/dog.jpg");

        Correlacao correlacao = new Correlacao();

        double[][] mascI = new double[3][3];
        double[][] mascII = new double[3][3];

        mascI[0] = new double[]{-1.0, 0, 1.0};
        mascI[1] = new double[]{-2.0, 0, 2.0};
        mascI[2] = new double[]{-1.0, 0, 1.0};

        mascII[0] = new double[]{1.0, 2.0, 1.0};
        mascII[1] = new double[]{0, 0, 0};
        mascII[2] = new double[]{-1.0, -2.0, -1.0};


        Mat mat = correlacao.correlacao(img, mascI);
        Mat mat1 = correlacao.correlacao(mat, mascII);

        HighGui.namedWindow("Teste Sobel");
        HighGui.imshow("Teste Sobel", mat1);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/teste_sobel.jpg", mat1);
    }
}

import executer.Correlacao;
import executer.FiltroTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoMain {

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/win.jpg");

        Correlacao correlacao = new Correlacao();
        Mat mat = correlacao.fazCorrelacao(20, 20, FiltroTypes.MEDIA, img);

        HighGui.namedWindow("Imagem grayscale");
        HighGui.imshow("Imagem grayscale", mat);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/newGrayScale.jpg", img);
    }

}

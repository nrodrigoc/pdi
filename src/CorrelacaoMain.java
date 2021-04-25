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

        FiltroTypes filtroTypes = FiltroTypes.MEDIA;

        Correlacao correlacao = new Correlacao();
        Mat mat = correlacao.fazCorrelacao(25, 25, filtroTypes, img);

        HighGui.namedWindow("Imagem grayscale");
        HighGui.imshow("Imagem grayscale", mat);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/"+filtroTypes+".jpg", img);
    }

}

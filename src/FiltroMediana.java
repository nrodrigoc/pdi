import executer.Correlacao;
import executer.FiltroTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class FiltroMediana {

    public static void main(String[] args) {

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/s_p.png");

        Correlacao correlacao = new Correlacao();

        Mat mat = correlacao.fazCorrelacao(4, 4, FiltroTypes.MEDIANA, img);

        Imgcodecs.imwrite("img/result/salt_peper_mediana.jpg", mat);

    }
}

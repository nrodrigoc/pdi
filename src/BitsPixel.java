import executer.BitPixelComponentTypes;
import executer.BitsPixelComponente;
import executer.Correlacao;
import executer.FiltroTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class BitsPixel {

    public static void main(String[] args){


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/colo.jpg");

        BitsPixelComponente bitsPixelComponente = new BitsPixelComponente();
        BitPixelComponentTypes bitPixelComponentTypes = BitPixelComponentTypes.COMPONENT_4;

        Mat mat = bitsPixelComponente.mudarNumeroDeValores(img, bitPixelComponentTypes);



        HighGui.namedWindow("Imagem com valores de pixel mudados:");
        HighGui.imshow("Imagem com valores de pixel mudados:", mat);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/valor_bit_"+bitPixelComponentTypes+".jpg", img);

    }
}

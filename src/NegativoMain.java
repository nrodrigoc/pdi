import executer.Negativo;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class NegativoMain {
    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/colo.jpg");

        Negativo negativo = new Negativo();
        Mat mat = negativo.transformarEmNegativo(img);

        HighGui.namedWindow("Imagem Em negativo");
        HighGui.imshow("Imagem Em negativo", mat);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/negativo.jpg", mat);

    }

}

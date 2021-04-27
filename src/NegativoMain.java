import commons.RGB;
import commons.YIQ;
import executer.Negativo;
import executer.NegativoTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class NegativoMain {
    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/paliasso.png");

        executer.Negativo negativo = new executer.Negativo();

        NegativoTypes negativoTypes = NegativoTypes.BRILHO;

        Mat mat = negativo.fazNegativo(img, negativoTypes );


        HighGui.namedWindow("Imagem Em negativo");
        HighGui.imshow("Imagem Em negativo", mat);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/negativo"+negativoTypes+".jpg", mat);

    }
}

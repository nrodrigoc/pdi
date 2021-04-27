import commons.RGB;
import commons.YIQ;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Conversor {

    public static void main(String[] args){
        System.out.println("Conversor RGB - YIQ:\n");

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/colo.jpg");


        for(int i=0; i < img.height(); i++){
            for(int j= 0 ; j< img.width(); j++){
                double[] doubles = img.get(i,j);
                RGB rgb = new RGB(doubles);
                YIQ yiq = rgb.convertToYiq();
                img.put(i,j,yiq.getInArray());
            }
        }

        HighGui.namedWindow("Imagem yiq");
        HighGui.imshow("Imagem yiq", img);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/yiq.jpg", img);

    }

}

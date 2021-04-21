import commons.RGB;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Main {
    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);


        Mat img = Imgcodecs.imread("./img/colo.jpg");

        int  x = 5/2;

        int height = img.height();
        int width = img.width();

        System.out.println(height);
        System.out.println(width);


        for(int i = 0 ; i < height; i++){
            for(int j=0; j<width ; j++){
                RGB rgb = new RGB(img.get(i, j));
                double [] grey = rgb.doGreyScaleColor();

                img.put(i,j, grey);

            }
        }

        HighGui.namedWindow("Imagem grayscale");
        HighGui.imshow("Imagem grayscale", img);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/newGrayScale.jpg", img);
    }
}

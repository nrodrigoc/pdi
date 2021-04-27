package utils;

import commons.RGB;
import commons.YIQ;
import org.opencv.core.Mat;

public class ImageUtils {

    public static double [][][] transformaImgEmVetorDePixelsYIQ(Mat img){

        double [][][] matriz = new double[img.height()][img.width()][3];

        for(int i = 0 ; i< img.height(); i++){
            for(int j= 0 ; j < img.width();j++){
                RGB rgb;
                double[] doubles = img.get(i, j);
                rgb = new RGB(doubles);
                YIQ yiq = rgb.convertToYiq();

                double [] finalValue = {yiq.getQ(), yiq.getI(), yiq.getY()};
                matriz[i][j] = finalValue;
            }
        }
        return matriz;
    }

    public static Mat transformaVetorEmMatRGB(double[][][] matriz, Mat img){

        for(int i = 0 ; i< matriz.length; i++){
            for(int j= 0 ; j < matriz[0].length;j++){
                YIQ yiq;

                double[] doubles = matriz[i][j];
                yiq = new YIQ(doubles);
                RGB rgb = yiq.convertToRgb();
                double [] finalValue = {rgb.getB(), rgb.getG(), rgb.getR()};

                img.put(i,j, finalValue);

            }
        }
        return img;
    }

}

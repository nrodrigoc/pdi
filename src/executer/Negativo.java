package executer;

import org.opencv.core.Mat;

public class Negativo {


    public Mat transformarEmNegativo(Mat img){

        int h = img.height();
        int w = img.width();

        Mat mat = img.clone();

        for(int i=0; i < h; i++){
            for(int j= 0 ; j< w; j++){
                double[] doubles = mat.get(i, j);
                double[] pixel = this.negativarPixel(doubles);
                mat.put(i,j,pixel);
            }
        }

        return mat;

    }


    public double[] negativarPixel(double[] pixel){

        double [] newPixel = new double[3];

        newPixel[0] = (255 - pixel[0]);
        newPixel[1] = (255 - pixel[1]);
        newPixel[2] = (255 - pixel[2]);

        return  newPixel;
    }


}

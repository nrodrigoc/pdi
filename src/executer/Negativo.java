package executer;

import org.opencv.core.Mat;
import utils.ImageUtils;

public class Negativo {



    public Mat fazNegativo(Mat img, NegativoTypes negativoTypes){
        Mat mat;

        switch (negativoTypes){
            case CORES: mat = this.transformarEmNegativoCores(img);
                break;
            case BRILHO: mat = this.transformarEmNegativoBrilho(img);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + negativoTypes);
        }

        return mat;
    }


    private Mat transformarEmNegativoBrilho(Mat img){

        double [][][] matriz = ImageUtils.transformaImgEmVetorDePixelsYIQ(img);

        int h = matriz.length;
        int w = matriz[0].length;

        for(int i=0; i < h; i++){
            for(int j= 0 ; j< w; j++){
                double[] doubles = matriz[i][j];
                double[] pixel = this.negativarPixelY(doubles);
                matriz[i][j] = pixel;
            }
        }

        Mat finalImage = ImageUtils.transformaVetorEmMatRGB(matriz, img);
        return finalImage;
    }

    private Mat transformarEmNegativoCores(Mat img){

        int h = img.height();
        int w = img.width();

        for(int i=0; i < h; i++){
            for(int j= 0 ; j< w; j++){
                double[] doubles = img.get(i,j);
                double[] pixel = this.negativarPixelRGB(doubles);
                img.put(i,j,pixel);
            }
        }
        return img;
    }

    public double[] negativarPixelY(double[] pixel){
        pixel[2] = (255 - pixel[2]);
        return  pixel;
    }

    public double[] negativarPixelRGB(double[] pixel){
        pixel[0] = (255 - pixel[0]);
        pixel[1] = (255 - pixel[1]);
        pixel[2] = (255 - pixel[2]);
        return  pixel;
    }

}

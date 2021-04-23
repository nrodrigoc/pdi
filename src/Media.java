import commons.RGB;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Media {

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/woman.png");


        int height = img.height();
        int width = img.width();

        int fator = 30;
        int divisao = fator*fator;

        System.out.println(height);
        System.out.println(width);

        int isPar = 0;
        int isImpar = fator % 2;
        if(isImpar == 0){
            isPar = 1;
        }

        for(int i = 0 ; i < height; i++){
            for(int j=0; j<width ; j++){

                double[] mediaTotalAutal = new double[3];

                int cont =0;

                int inicialI = i - (fator/2);
                int inicialJ = j - (fator/2);

                int finalI = i + ((fator/2) - isPar);
                int finalJ = i + ((fator/2) - isPar);

                for(int ii = i - (fator/2); ii <= i + ((fator/2) - isPar); ii++){
                    for(int jj = j - (fator/2); jj <= j + ((fator/2) - isPar); jj++){


                        cont ++;
                        if(ii < 0 || jj <0 || ii >= height || jj >= width){
                        }else{
                            double[] doubles = img.get(ii, jj);

                            mediaTotalAutal[0] += doubles[0]/divisao;
                            mediaTotalAutal[1] += doubles[1]/divisao;
                            mediaTotalAutal[2] += doubles[2]/divisao;

                        }
                    }
                }
                img.put(i,j,mediaTotalAutal);
            }
        }

        HighGui.namedWindow("Imagem grayscale");
        HighGui.imshow("Imagem grayscale", img);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/newGrayScale.jpg", img);

    }

}

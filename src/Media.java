import commons.RGB;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class Media {

    public static void main(String[] args){

        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/win.jpg");

        Mat result = Imgcodecs.imread("./img/color.jpg");


        int height = img.height();
        int width = img.width();
        int fator = 21;
        int divisao = fator*fator;

        System.out.println(height);
        System.out.println(width);

        int isImpar = fator % 2;

        for(int i = 0 ; i < height; i++){
            for(int j=0; j<width ; j++){

                double[] mediaTotalAutal = new double[3];
                //System.out.println("J: "+j);
                //System.out.println("------------Iniciando contagem------------");

                int cont =0;

                int inicialI = i - (fator/2);
                int inicialJ = j - (fator/2);
                int finalI = i + (fator/2) + isImpar;
                int finalJ = i + (fator/2) + isImpar;

                for(int ii = i - (fator/2); ii<= i + (fator/2); ii++){
                    for(int jj = j - (fator/2); jj<= j + (fator/2); jj++){


                        //System.out.println("Contagem: "+cont+  " ii = "+ii +" jj= "+jj);
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
                //System.out.println("Pixel "+i+"x"+j+" valor= ["+mediaTotalAutal[0]+","+mediaTotalAutal[1]+","+mediaTotalAutal[2]+"]");
                img.put(i,j,mediaTotalAutal);
            }
        }

        HighGui.namedWindow("Imagem grayscale");
        HighGui.imshow("Imagem grayscale", img);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/newGrayScale.jpg", img);

    }

}

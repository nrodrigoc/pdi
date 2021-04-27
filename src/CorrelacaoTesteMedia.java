import commons.RGB;
import executer.Correlacao;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoTesteMedia {

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img = Imgcodecs.imread("./img/win.jpg");

        Correlacao correlacao = new Correlacao();

        double[][] masc = new double[25][25];

        double value = 1.0/625.0;

        //Construindo uma mascara
        for(int i=0; i<25; i++){
            for(int j=0; j<25; j++){
                masc[i][j] = value;
            }
        }

        Mat correlacao1 = correlacao.correlacao(img, masc);

        HighGui.namedWindow("Teste média:");
        HighGui.imshow("Teste média:", correlacao1);
        HighGui.waitKey(0);

        Imgcodecs.imwrite("img/result/teste_media.jpg", correlacao1);
    }

}

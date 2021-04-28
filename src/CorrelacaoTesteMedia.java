import commons.RGB;
import executer.Correlacao;
import executer.FiltroTypes;
import executer.constants.CorrelacaoConstants;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.highgui.HighGui;
import org.opencv.imgcodecs.Imgcodecs;

public class CorrelacaoTesteMedia {

    public static void main(String[] args){
        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);

        Mat img = Imgcodecs.imread("./img/win.jpg");

        Correlacao correlacao = new Correlacao();

        int height = 31;
        int width = 31;

        double[][] masc = new double[height][width];

        double value = 1.0/(height*width);

        //Construindo uma mascara
        for(int i=0; i<height; i++){
            for(int j=0; j<width; j++){
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

import executer.Correlacao;
import executer.FiltroTypes;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

public class Comparator {

    public static void main(String[] args){


        System.loadLibrary(Core.NATIVE_LIBRARY_NAME);
        Mat img = Imgcodecs.imread("./img/win.jpg");

        long startTime =System.currentTimeMillis();
        Correlacao correlacao = new Correlacao();
        Mat mat = correlacao.fazCorrelacao(25, 25, FiltroTypes.MEDIA, img);
        long endTime =System.currentTimeMillis();

        long startTime1 =System.currentTimeMillis();
        Mat mat1 = correlacao.fazCorrelacao(25, 1, FiltroTypes.MEDIA, img);
        mat1 = correlacao.fazCorrelacao(1, 25, FiltroTypes.MEDIA, mat1);
        long endTime1 =System.currentTimeMillis();

        Imgcodecs.imwrite("img/result/25_25.jpg", mat);
        Imgcodecs.imwrite("img/result/25_1_1_25.jpg", mat1);

        System.out.println("Tempo do 25x25: "+ (endTime-startTime));
        System.out.println("Tempo do 25x1 seguido por 1x25: "+ (endTime1-startTime1));

    }

}

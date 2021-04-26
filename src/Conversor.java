import commons.RGB;
import commons.YIQ;

public class Conversor {

    public static void main(String[] args){
        System.out.println("Conversor RGB - YIQ:\n");

        RGB rgb = new RGB(255,255,2);
        YIQ yiq = rgb.convertToYiq();

        System.out.println("Convertendo o RGB em YIQ:");
        System.out.println(yiq.toString()+"\n");

        System.out.println("Convertendo o YIQ em RGB:");
        RGB rgb1 = yiq.convertToRgb();
        System.out.println(rgb1.toString());

    }

}

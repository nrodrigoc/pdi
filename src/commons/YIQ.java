package commons;

import utils.Constants;

public class YIQ {

    private double y;
    private double i;
    private double q;

    public YIQ(double y, double i, double q) {
        this.y = y;
        this.i = i;
        this.q = q;
    }

    public YIQ(double[] y) {
        this.y = y[2];
        this.i = y[1];
        this.q = y[0];
    }

    public RGB convertToRgb(){
        double preR;
        double preG;
        double preB;

        preR = Constants.VALUE_CONVERT_TO_RGB_RED_FACTOR_Y * y
                + Constants.VALUE_CONVERT_TO_RGB_RED_FACTOR_I * i
                + Constants.VALUE_CONVERT_TO_RGB_RED_FACTOR_Q * q;

        preG = Constants.VALUE_CONVERT_TO_RGB_GREEN_FACTOR_Y * y
                - Constants.VALUE_CONVERT_TO_RGB_GREEN_FACTOR_I * i
            - Constants.VALUE_CONVERT_TO_RGB_GREEN_FACTOR_Q * q;

        preB = Constants.VALUE_CONVERT_TO_RGB_BLUE_FACTOR_Y * y
                - Constants.VALUE_CONVERT_TO_RGB_BLUE_FACTOR_I * i
                + Constants.VALUE_CONVERT_TO_RGB_BLUE_FACTOR_Q * q;

        RGB rgb = new RGB(preR,preG,preB);
        return rgb;
    }


    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public double getI() {
        return i;
    }

    public void setI(double i) {
        this.i = i;
    }

    public double getQ() {
        return q;
    }

    public void setQ(double q) {
        this.q = q;
    }

    @Override
    public String toString() {
        return "YIQ{" +
                "y=" + y +
                ", i=" + i +
                ", q=" + q +
                '}';
    }
}

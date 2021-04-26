package commons;

import utils.Constants;

public class RGB {

    private double r;
    private double g;
    private double b;

    public RGB(double r, double g, double b) {
        this.r = (r > 255 ? 255.0 : r);
        this.r = (r <= 0 ? 0 : r);

        this.g = (g > 255 ? 255.0 : g);
        this.g = (g <= 0 ? 0 : g);

        this.b = (b > 255 ? 255.0 : b);
        this.b = (b <= 0 ? 0 : b);

    }

    public YIQ convertToYiq(){
        double preY =
                Constants.VALUE_CONVERT_TO_YIQ_Y_FACTOR_R * r
                + Constants.VALUE_CONVERT_TO_YIQ_Y_FACTOR_G * g
                + Constants.VALUE_CONVERT_TO_YIQ_Y_FACTOR_B * b;

        double preI = Constants.VALUE_CONVERT_TO_YIQ_I_FACTOR_R * r
                - Constants.VALUE_CONVERT_TO_YIQ_I_FACTOR_G * g
                - Constants.VALUE_CONVERT_TO_YIQ_I_FACTOR_B * b;

        double preQ = Constants.VALUE_CONVERT_TO_YIQ_Q_FACTOR_R * r
                - Constants.VALUE_CONVERT_TO_YIQ_Q_FACTOR_G * g
                + Constants.VALUE_CONVERT_TO_YIQ_Q_FACTOR_B * b;

        return new YIQ( preY, preI, preQ);
    }

    public RGB(double [] rgb){
        r = rgb[2];
        g = rgb[1];
        b = rgb[0];
    }

    public double [] doGreyScaleColor(){

        double grey = (r+g+b)/3;
        double [] greyScale = new double[3];

        greyScale[0] = grey;
        greyScale[1] = grey;
        greyScale[2] = grey;

        return greyScale;
    }

    public double getR() {
        return r;
    }

    public void setR(double r) {
        this.r = r;
    }

    public double getG() {
        return g;
    }

    public void setG(double g) {
        this.g = g;
    }

    public double getB() {
        return b;
    }

    public void setB(double b) {
        this.b = b;
    }

    @Override
    public String toString() {
        return "RGB{" +
                "r=" + r +
                ", g=" + g +
                ", b=" + b +
                '}';
    }
}

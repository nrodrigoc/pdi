package executer;

import org.opencv.core.Mat;

public class BitsPixelComponente {

    public Mat mudarNumeroDeValores(Mat img, BitPixelComponentTypes bitPixelComponentTypes ) {
        Mat mat = img.clone();

        switch (bitPixelComponentTypes) {
            case COMPONENT_4:
                mat = this.mudaValoresComponentBitPixel(img, 4);
                break;
            case COMPONENT_6:
                mat = this.mudaValoresComponentBitPixel(img,6);
                break;
            case COMPONENT_8:
                mat = this.mudaValoresComponentBitPixel(img, 8);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + bitPixelComponentTypes);
        }

        return mat;
    }

    private Mat mudaValoresComponentBitPixel(Mat img, int numBits){

        int maxValor = ((int) Math.pow(2,numBits)) -1;

        for(int i=0; i< img.height(); i++){
            for(int j=0; j< img.width(); j++){
                double[] pixel = img.get(i, j);

                pixel[0] = this.setValue(maxValor, pixel[0]);
                pixel[1] = this.setValue(maxValor, pixel[1]);
                pixel[2] = this.setValue(maxValor, pixel[2]);

                img.put(i,j,pixel);
            }
        }

        return img;
    }

    private double setValue(int maxValue, double valorAtual){

        double valorFinal = (maxValue*valorAtual)/255;
        return valorFinal;
    }



}

package executer;

import executer.constants.CorrelacaoConstants;
import order.QuickSort;
import org.opencv.core.Mat;

public class Correlacao {

    public Mat fazCorrelacao(int m, int n, FiltroTypes filtroType, Mat img){

        Mat mat = new Mat();

        switch (filtroType){
            case MEDIA: mat = this.media(m,n,img);
                break;
            case MEDIANA: mat = this.mediana(m,n,img);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + filtroType);
        }

        return mat;
    }

    private Mat media(int m, int n, Mat img){
        int height = img.height();
        int width = img.width();

        Mat finalMat = img.clone();

        int fatorM = m;
        int fatorN = n;

        int divisao = fatorM*fatorN;

        int isImpar = divisao % 2;
        int isPar = isImpar == 0? 1 : 0;

        for(int i = 0 ; i < height; i++){
            for(int j=0; j<width ; j++){

                double[] mediaTotalAutal = new double[3];

                for(int ii = i - (fatorM/2); ii <= i + ((fatorM/2) - isPar); ii++){
                    for(int jj = j - (fatorN/2); jj <= j + ((fatorN/2) - isPar); jj++){

                        if(ii < 0 || jj <0 || ii >= height || jj >= width){
                        }else{
                            double[] doubles = img.get(ii, jj);

                            mediaTotalAutal[0] += doubles[0]/divisao;
                            mediaTotalAutal[1] += doubles[1]/divisao;
                            mediaTotalAutal[2] += doubles[2]/divisao;

                        }
                    }
                }
                finalMat.put(i,j,mediaTotalAutal);
            }
        }
        return finalMat;
    }


    private Mat mediana(int m, int n, Mat img){
        int height = img.height();
        int width = img.width();

        Mat finalMat = img.clone();

        int fatorM = m;
        int fatorN = n;

        int divisao = fatorM*fatorN;

        int isImpar = divisao % 2;
        int isPar = isImpar == 0? 1 : 0;

        for(int i = 0 ; i < height; i++){
            for(int j=0; j<width ; j++){

                double [] medianaR = new double[divisao];
                double [] medianaG = new double[divisao];
                double [] medianaB = new double[divisao];

                int cont = 0;
                for(int ii = i - (fatorM/2); ii <= i + ((fatorM/2) - isPar); ii++){
                    for(int jj = j - (fatorN/2); jj <= j + ((fatorN/2) - isPar); jj++){

                        if(ii < 0 || jj <0 || ii >= height || jj >= width){
                            medianaR[cont] = 0;
                            medianaG[cont] = 0;
                            medianaB[cont] = 0;
                        }else{
                            double[] doubles = img.get(ii, jj);
                            medianaR[cont] = doubles[0];
                            medianaG[cont] = doubles[1];
                            medianaB[cont] = doubles[2];
                        }
                        cont++;
                    }
                }
                QuickSort.quickSort(medianaR,0,divisao-1);
                QuickSort.quickSort(medianaG,0,divisao-1);
                QuickSort.quickSort(medianaB,0,divisao-1);

                double [] finals = new double[3];

                finals[0] = medianaR[(divisao/2) -1 +isImpar];
                finals[1] = medianaG[(divisao/2) -1 +isImpar];
                finals[2] = medianaB[(divisao/2) -1 +isImpar];

                finalMat.put(i,j,finals);
            }
        }
        return finalMat;
    }

    public Mat correlacao(Mat img, double[][] masc){


        int height = img.height();
        int width = img.width();

        int fatorM = masc.length;
        int fatorN = masc[0].length;

        int isImpar = (fatorM*fatorN) % 2;
        int isPar = isImpar == 0? 1 : 0;

        Mat finalMat = img.clone();

        for(int i = 0 ; i < height; i++){
            for(int j = 0; j< width ; j++){

                double[] pixelFinal = new double[3];
                int contII=0;

                for(int ii = i - (fatorM/2); ii <= i + ((fatorM/2) - isPar); ii++){
                    int contJJ=0;
                    for(int jj = j - (fatorN/2); jj <= j + ((fatorN/2) - isPar); jj++){

                        if(ii < 0 || ii >= height || jj < 0 || jj >= width ){

                        }else {
                            double[] pixelPreSoma = img.get(ii, jj);

                            pixelFinal[0] += masc[contII][contJJ] * pixelPreSoma[0];
                            pixelFinal[1] += masc[contII][contJJ] * pixelPreSoma[1];
                            pixelFinal[2] += masc[contII][contJJ] * pixelPreSoma[2];

                        }
                        contJJ++;
                    }
                    contII++;
                }
                finalMat.put(i,j,pixelFinal);
            }
        }
        return finalMat;
    }




}

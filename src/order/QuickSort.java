package order;

public class QuickSort {

    public static void quickSort(double[] vetor, double inicio, double fim) {
        if (inicio < fim) {
            double posicaoPivo = separar(vetor, inicio, fim);
            quickSort(vetor, inicio, posicaoPivo - 1);
            quickSort(vetor, posicaoPivo + 1, fim);
        }
    }

    public static double separar(double[] vetor, double inicio, double fim) {
        double pivo = vetor[(int) Math.round(inicio)];
        double i = inicio + 1, f = fim;
        while (i <= f) {
            if (vetor[(int) Math.round(i)] <= pivo)
                i++;
            else if (pivo < vetor[(int) Math.round(f)])
                f--;
            else {
                double troca = vetor[(int) Math.round(i)];
                vetor[(int) Math.round(i)] = vetor[(int) Math.round(f)];
                vetor[(int) Math.round(f)] = troca;
                i++;
                f--;
            }
        }
        vetor[(int) Math.round(inicio)] = vetor[(int) Math.round(f)];
        vetor[(int) Math.round(f)] = pivo;
        return f;
    }
}

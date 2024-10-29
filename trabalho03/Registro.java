package trabalho03;
import java.util.Random;

class Registro {
    int codigo;

    public Registro(int codigo) {
        this.codigo = codigo;
    }

    public int getCodigo() {
        return this.codigo;
    }

    public static Registro[] gerarAleatorio(Registro vetor[], long seed) {
        Random rand = new Random(seed);
        for (int i = 0; i < vetor.length; i++) {
            int aleatorio = rand.nextInt(900000000) + 100000000;
            vetor[i] = new Registro(aleatorio);
        }
        return vetor;
    }
}

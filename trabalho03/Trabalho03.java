package trabalho03;
import java.util.LinkedList;

public class Trabalho03 {

    static long insereResto(int chave, LinkedList<Integer>[] tabela) {
        int i = chave % tabela.length;
        long colisoes = 0;

        if (tabela[i] == null) {
            tabela[i] = new LinkedList<>();
        } else {
            colisoes = tabela[i].size();
        }

        tabela[i].add(chave);
        return colisoes;
    }

    static long buscaResto(int chave, LinkedList<Integer>[] tabela) {
        int i = chave % tabela.length;
        long comparacoes = 1;

        if (tabela[i] != null) {
            for (int valor : tabela[i]) {
                if (valor == chave) {
                    return comparacoes;
                }
                comparacoes++;
            }
        }
        return comparacoes;
    }

    public static float elapsedTime(long endTime, long startTime) {
        return (endTime - startTime) / 1e6f;
    }

    public static void main(String[] args) {
        int[] tamanhosTabelas = {10, 100, 1000};
        int[] tamanhosDados = {1000000, 5000000, 20000000};
        long seed = 12345;

        Registro r = new Registro(0);

        for (int tamTabela : tamanhosTabelas) {
            for (int tamDados : tamanhosDados) {

                LinkedList<Integer>[] tabela = new LinkedList[tamTabela];
                Registro[] conjunto = new Registro[tamDados];

                conjunto = r.gerarAleatorio(conjunto, seed);
                long somaColisoes = 0;
                long somaComparacoes = 0;

                // Inserção
                long startTime = System.nanoTime();
                for (Registro reg : conjunto) {
                    somaColisoes += insereResto(reg.getCodigo(), tabela);
                }
                long endTime = System.nanoTime();
                float tempoInsercao = elapsedTime(endTime, startTime);

                // Busca
                startTime = System.nanoTime();
                for (int i = 0; i < 5; i++) {
                    somaComparacoes += buscaResto(conjunto[i].getCodigo(), tabela);
                }
                endTime = System.nanoTime();
                float tempoBusca = elapsedTime(endTime, startTime);

                System.out.println("Tamanho da Tabela: " + tamTabela);
                System.out.println("Tamanho dos Dados: " + tamDados);
                System.out.println("Colisões: " + somaColisoes);
                System.out.printf("Tempo de Inserção: %.2f ms\n", tempoInsercao);
                System.out.printf("Tempo de Busca: %.2f ms\n", tempoBusca);
                System.out.println("Comparações: " + somaComparacoes);
                System.out.println("------------------------");
            }
        }
    }
}

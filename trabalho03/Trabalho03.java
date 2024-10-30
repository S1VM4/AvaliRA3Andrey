package trabalho03;
import java.util.LinkedList;
import java.util.Random;

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

    static long insereMultiplicacao(int chave, LinkedList<Integer>[] tabela) {
        double A = 0.6180339887; // constante sugerida para hashing por multiplicação
        int i = (int) (tabela.length * ((chave * A) % 1));
        long colisoes = 0;

        if (tabela[i] == null) {
            tabela[i] = new LinkedList<>();
        } else {
            colisoes = tabela[i].size();
        }

        tabela[i].add(chave);
        return colisoes;
    }

    static long insereDobramento(int chave, LinkedList<Integer>[] tabela) {
        int parte1 = chave / 1000;
        int parte2 = chave % 1000;
        int i = (parte1 + parte2) % tabela.length;
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

    static long buscaMultiplicacao(int chave, LinkedList<Integer>[] tabela) {
        double A = 0.6180339887;
        int i = (int) (tabela.length * ((chave * A) % 1));
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

    static long buscaDobramento(int chave, LinkedList<Integer>[] tabela) {
        int parte1 = chave / 1000;
        int parte2 = chave % 1000;
        int i = (parte1 + parte2) % tabela.length;
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

                // Teste com cada função hash
                for (String funcaoHash : new String[]{"resto", "multiplicacao", "dobramento"}) {
                    long somaColisoes = 0;
                    long somaComparacoes = 0;

                    // Inserção
                    long startTime = System.nanoTime();
                    for (Registro reg : conjunto) {
                        switch (funcaoHash) {
                            case "resto" -> somaColisoes += insereResto(reg.getCodigo(), tabela);
                            case "multiplicacao" -> somaColisoes += insereMultiplicacao(reg.getCodigo(), tabela);
                            case "dobramento" -> somaColisoes += insereDobramento(reg.getCodigo(), tabela);
                        }
                    }
                    long endTime = System.nanoTime();
                    float tempoInsercao = elapsedTime(endTime, startTime);

                    // Busca
                    startTime = System.nanoTime();
                    for (int i = 0; i < 5; i++) {
                        switch (funcaoHash) {
                            case "resto" -> somaComparacoes += buscaResto(conjunto[i].getCodigo(), tabela);
                            case "multiplicacao" -> somaComparacoes += buscaMultiplicacao(conjunto[i].getCodigo(), tabela);
                            case "dobramento" -> somaComparacoes += buscaDobramento(conjunto[i].getCodigo(), tabela);
                        }
                    }
                    endTime = System.nanoTime();
                    float tempoBusca = elapsedTime(endTime, startTime);

                    System.out.println("Tamanho da Tabela: " + tamTabela);
                    System.out.println("Tamanho dos Dados: " + tamDados);
                    System.out.println("Função Hash: " + funcaoHash);
                    System.out.println("Colisões: " + somaColisoes);
                    System.out.printf("Tempo de Inserção: %.2f ms\n", tempoInsercao);
                    System.out.printf("Tempo de Busca: %.2f ms\n", tempoBusca);
                    System.out.println("Comparações: " + somaComparacoes);
                    System.out.println("------------------------");

                    // Limpar a tabela para o próximo teste de função hash
                    tabela = new LinkedList[tamTabela];
                }
            }
        }
    }
}

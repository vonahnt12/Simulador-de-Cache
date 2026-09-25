//modela um conjunto da cache

import java.util.Random;

public class CacheSet {
    private CacheLine[] posicoes; // o conjunto é um vetor de blocos
    private Random random; // utilizado para política R

    public CacheSet(int assoc) {
        posicoes = new CacheLine[assoc]; // número de blocos no conjunto é igual a assoc
        for(int i = 0; i < assoc; i++) {
            posicoes[i] = new CacheLine(); // instancia um bloco para cada posicao do vetor
        }
        this.random = new Random();
    }

    // Percorre o conjunto e procura posição válida com tag do parâmetro
    public int procurarPosicao(int tag) {
        for (int i = 0; i < posicoes.length; i++) {
            if (posicoes[i].isValido() && posicoes[i].getTag() == tag) {
                return i; //retorna posição encontrada, hit
            }
        }
        return -1; //bloco não encontrado, miss
    }

    // Percorre o conjunto e procura posição inválida: validade = 0
    public int procurarPosicaoInvalida() {
        for(int i = 0; i < posicoes.length; i++) {
            if(!posicoes[i].isValido()) {
                return i; //retorna posição inválida encontrada
            }
        }
        return -1; //nenhuma posição inválida no conjunto
    }
    //encontra qual bloco deve ser substituido na cache de acordo com a política escolhida
    public int posicaoParaSubstituir(String politica) {
        switch(politica) {
            case "R": // Random: escolhe uma posição aleatória de acordo com tamanho do conjunto
                return random.nextInt(posicoes.length);
            case "F": // FIFO - busca posição com menor contador
            case "L": //LRU -  busca posição com menor contador, atualiza no hit
                int posicao = 0;
                long menorContador = posicoes[0].getContador();
                for (int i = 1; i < posicoes.length; i++) {
                    if (posicoes[i].getContador() < menorContador) {
                        menorContador = posicoes[i].getContador();
                        posicao = i;
                    }
                }
                return posicao; //retorna posição a ser substituida
            default:
                return 0; //padrao substitui a primeira
        }
    }
    //retorna true se o conjunto está cheio
    public boolean isFull() {
        for(CacheLine posicao: posicoes) {
            if(!posicao.isValido()) {
                return false;
            }
        }
        return true;
    }

    //retorna o bloco do conjunto na posição index
    public CacheLine getPosicao(int index) {
        return posicoes[index];
    }
}

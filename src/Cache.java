//classe que modela o funcionamento da cache

public class Cache {
    private CacheSet[] conjuntos; // a cache é um vetor de conjuntos de tamanho nsets
    private int nsets;
    private int assoc;
    private int bsize;
    private String politicaSubst;
    private long contadorGlobal = 0;

    private int totalAcessos = 0;
    private int totalHits = 0;
    private int missCompulsorio = 0;
    private int missCapacidade = 0;
    private int missConflito = 0;



    public Cache(int nsets, int assoc, int bsize, String politicaSubst) {
        this.nsets = nsets;
        this.assoc = assoc;
        this.bsize = bsize;
        this.politicaSubst = politicaSubst;

        conjuntos = new CacheSet[nsets];
        for(int i = 0; i < nsets; i++) {
            conjuntos[i] = new CacheSet(assoc); //instancia um conjunto para cada posicao do vetor
        }

    }

    //recebe um endereço e simula um acesso à cache
    public void acessar(int endereco) {
        totalAcessos++;

        // calcula numero de bits de offset e indice
        int nBitsOffset = log2(bsize);
        int nBitsIndice = log2(nsets);

        //extrai o indice e tag do endereco
        int indice = (endereco >> nBitsOffset) & ((1 << nBitsIndice) - 1);
        int tag = endereco >> (nBitsOffset + nBitsIndice);

        //encontra o conjunto de acordo com o indice encontrado
        CacheSet conjunto = conjuntos[indice];

        //procura o bloco com a mesma tag no conjunto
        int posicaoHit = conjunto.procurarPosicao(tag);

        //hit
        if(posicaoHit >= 0) {
            totalHits++;
            //atualiza contador se for LRU
            if(politicaSubst.equals("L")) {
                contadorGlobal++;
                conjunto.getPosicao(posicaoHit).setContador(contadorGlobal);
            }
            //FIFO nao atualiza contador no hit
            return;
        }

        //miss Compulsório
        //procura uma posicao que ainda nao foi inicializada
        int posicaoInvalida = conjunto.procurarPosicaoInvalida();
        if (posicaoInvalida >= 0) {
            missCompulsorio++;

            CacheLine posicao = conjunto.getPosicao(posicaoInvalida);
            posicao.setValido(true);
            posicao.setTag(tag);
            contadorGlobal++;
            posicao.setContador(contadorGlobal);
            return;
        }
        //miss não compulsório
        //descobrir se é miss de capacidade ou conflito
        boolean isCapacidade = true;
        for(CacheSet set : conjuntos) {
            if (!set.isFull()) {
                isCapacidade = false;
                break;
            }
        }

        if (isCapacidade) {
            missCapacidade++;
        } else {
            missConflito++;
        }

        //tratamento de miss capacidade ou conflito
        int posicaoSubstituir = conjunto.posicaoParaSubstituir(politicaSubst);
        CacheLine posicao = conjunto.getPosicao(posicaoSubstituir);
        posicao.setTag(tag);
        contadorGlobal++;
        posicao.setContador(contadorGlobal);
    }


    public void imprimirEstatisticas(int flag_saida) {
        double taxaHit = (double) totalHits / totalAcessos;
        double taxaMiss = 1.0 - taxaHit;

        int totalMiss = totalAcessos - totalHits;
        double taxaComp = (double)missCompulsorio / totalMiss;
        double taxaCap = (double)missCapacidade / totalMiss;
        double taxaConf = (double)missConflito / totalMiss;

        if(flag_saida == 0) {
            System.out.println("Total de acessos: " + totalAcessos);
            System.out.printf("Taxa de hits = %.4f%n", taxaHit);
            System.out.printf("Taxa de miss = %.4f%n", taxaMiss);
            System.out.printf("Taxa de miss compulsório = %.4f%n", taxaComp);
            System.out.printf("Taxa de miss de capacidade = %.4f%n", taxaCap);
            System.out.printf("Taxa de miss de conflito = %.4f%n", taxaConf);
            System.out.println("Misses totais: " + totalMiss);
            System.out.println("Misses Compulsórios: " + missCompulsorio);
            System.out.println("Misses Capacidade: " + missCapacidade);
            System.out.println("Misses Conflito: " + missConflito);
        } else {
            System.out.printf("%d %.4f %.4f %.4f %.4f %.4f%n",
                    totalAcessos, taxaHit, taxaMiss, taxaComp, taxaCap, taxaConf);
        }
    }

    private int log2(int x) {
        return 31 - Integer.numberOfLeadingZeros(x);
    }
}

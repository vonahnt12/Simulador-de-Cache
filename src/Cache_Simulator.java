import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Cache_Simulator {

    public static void main(String[] args) {
        // Verifica se o número correto de argumentos foi passado
        if (args.length != 6) {
            System.out.println("Uso: java CacheSimulator <nsets> <bsize> <assoc> <substituicao> <flag_saida> <arquivo_entrada>");
            return;
        }

        // Leitura dos argumentos da linha de comando
        int nsets = Integer.parseInt(args[0]);
        int bsize = Integer.parseInt(args[1]);
        int assoc = Integer.parseInt(args[2]);
        String politica = args[3];
        int flag_saida = Integer.parseInt(args[4]);
        String caminhoArquivo = args[5];

        // Leitura do arquivo e armazenamento dos endereços
        List<Integer> enderecos = new ArrayList<>();

        try (DataInputStream dis = new DataInputStream(new FileInputStream(caminhoArquivo))) {

            //enquanto houver, lê os dados disponíveis
            while(dis.available() > 0) {
                int endereco = dis.readInt(); // Lê 4 bytes e converte para int
                enderecos.add(endereco);
            }

        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo binário: " + e.getMessage());
        }

        Cache cache = new Cache(nsets, assoc, bsize, politica);

        //acessa os enderecos da lista na cache
        for(int endereco : enderecos) {
            cache.acessar(endereco);
        }

        cache.imprimirEstatisticas(flag_saida);

    }
}
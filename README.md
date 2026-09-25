#  Simulador de Memória Cache

Este projeto é um simulador de memória cache desenvolvido em **Java**. O programa simula a arquitetura, o mapeamento e o comportamento de uma cache, processando ficheiros binários de rastreio (*trace files*) com acessos à memória e calculando estatísticas de desempenho (*hits* e *misses*).

---

##  Funcionalidades e Conceitos

- **Mapeamento de Cache:** Organização por conjuntos (*sets*) e linhas (*lines*).
- **Políticas de Substituição:** Suporte a diferentes algoritmos de substituição (ex.: LRU, Random, FIFO).
- **Processamento de Traces:** Leitura de endereços a partir de ficheiros binários de entrada (`.bin`).
- **Relatório de Desempenho:** Exibição do total de acessos, *hits* (sucessos), *misses* (falhas) e respetivas taxas percentuais.

---

##  Estrutura do Repositório

```text
.
├── src/
│   ├── Cache_Simulator.java   # Classe principal (ponto de entrada)
│   ├── Cache.java             # Lógica e estrutura da memória cache
│   ├── CacheSet.java          # Gestão dos conjuntos associativos
│   └── CacheLine.java         # Representação das linhas de cache
├── bin_100.bin                # Ficheiro de teste (100 acessos)
├── bin_1000.bin               # Ficheiro de teste (1.000 acessos)
├── bin_10000.bin              # Ficheiro de teste (10.000 acessos)
├── vortex.in.sem.persons.bin  # Ficheiro de teste de elevada complexidade
└── README.md                  # Documentação do projeto
```

---

##  Como Compilar e Executar

### Pré-requisitos
- **Java Development Kit (JDK)** versão 8 ou superior instalado no sistema.

---

### 1. Compilação
Abre o terminal ou linha de comandos na pasta raiz do projeto e executa o seguinte comando para compilar as classes da pasta `src`:

```bash
javac src/*.java
```

---

### 2. Execução

Para executar o simulador, passa os parâmetros da arquitetura da cache e o ficheiro binário de teste através da linha de comandos:

```bash
java -cp src Cache_Simulator <nsets> <bsize> <assoc> <subst> <flag_out> <arquivo.bin>
```

####  Significado dos Parâmetros:

| Parâmetro | Descrição | Exemplo / Opções |
| :--- | :--- | :--- |
| `<nsets>` | Número de conjuntos da cache | `256`, `512`, `1024` |
| `<bsize>` | Tamanho do bloco/linha em bytes | `4`, `8`, `32`, `64` |
| `<assoc>` | Grau de associatividade (linhas por conjunto) | `1` (direto), `2`, `4`, `8` |
| `<subst>` | Política de substituição de bloco | `L` (LRU), `R` (Random), `F` (FIFO) |
| `<flag_out>` | Formato da saída | `0` (resumo) ou `1` (detalhado) |
| `<arquivo.bin>`| Caminho do ficheiro binário de rastreio | `bin_100.bin`, `vortex.in.sem.persons.bin` |

---

###  Exemplos de Execução

- **Mapeamento Direto com 256 conjuntos e blocos de 4 bytes:**
  ```bash
  java -cp src Cache_Simulator 256 4 1 R 0 bin_100.bin
  ```

- **Cache Conjunta Associativa (4-way) com política LRU:**
  ```bash
  java -cp src Cache_Simulator 128 16 4 L 0 bin_1000.bin
  ```

- **Teste com ficheiro de grande dimensão:**
  ```bash
  java -cp src Cache_Simulator 512 32 2 L 0 vortex.in.sem.persons.bin
  ```

---

##  Autor

Desenvolvido por **Eduardo Alencastro von Ahnt**.

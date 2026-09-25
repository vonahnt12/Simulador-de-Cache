//Classe que modela um bloco da cache

public class CacheLine {
    private int tag;
    private boolean valido;
    private long contador; //para política de substituição

    public CacheLine() {
        this.valido = false;
        this.tag = -1;
        this.contador = 0;
    }

    public int getTag() {
        return tag;
    }

    public void setTag(int tag) {
        this.tag = tag;
    }

    //retorna true se o bit de validade é 1
    public boolean isValido() {
        return valido;
    }

    public void setValido(boolean valido) {
        this.valido = valido;
    }

    public long getContador() {
        return contador;
    }

    public void setContador(long contador) {
        this.contador = contador;
    }
}

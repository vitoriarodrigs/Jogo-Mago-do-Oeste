package Classes.Feitico;

public class Magia extends TrechoDeCodigo{

    private String tipo;
    private String nome;
    private int valor;

    public Magia (String tipo, String nome, int custo, int valor){
        super(custo);
        this.valor= valor;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public int getValor() {
        return valor;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(int valor) {
        this.valor = valor;
    }
}

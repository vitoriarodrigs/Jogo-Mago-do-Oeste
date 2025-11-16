package Classes.Feitico;

public class Magia extends TrechoDeCodigo{

    private String tipo;
    private String nome;
    private int poder;

    public Magia (String tipo, String nome, int custo, int poder){
        super(custo);
        this.poder= poder;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo;
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(int poder) {
        this.poder = poder;
    }
}

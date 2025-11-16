package Classes.Feitico;

public class Magia extends TrechoDeCodigo{

    private TipoMagia tipo;
    private String nome;
    private int poder;

    public Magia (TipoMagia tipo, String nome, int custo, int poder){
        super(custo);
        this.poder= poder;
        this.nome = nome;
        this.tipo = tipo;
    }

    public String getTipo() {
        return tipo.toString();
    }

    public String getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public void setTipo(TipoMagia tipo) {
        this.tipo = tipo;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public void setValor(int poder) {
        this.poder = poder;
    }
}

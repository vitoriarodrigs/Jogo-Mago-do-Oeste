package Classes.Feitico;

public class Magia extends TrechoDeCodigo{

    private TipoMagia tipo;
    private NomeMagia nome;
    private int poder;

    public Magia (TipoMagia tipo, NomeMagia nome, int custo, int poder){
        super(custo);
        this.poder= poder;
        this.nome = nome;
        this.tipo = tipo;
    }

    public TipoMagia getTipo() {
        return tipo;
    }

    public NomeMagia getNome() {
        return nome;
    }

    public int getPoder() {
        return poder;
    }

    public void setTipo(TipoMagia tipo) {
        this.tipo = tipo;
    }

    public void setNome(NomeMagia nome) {
        this.nome = nome;
    }

    public void setValor(int poder) {
        this.poder = poder;
    }
}

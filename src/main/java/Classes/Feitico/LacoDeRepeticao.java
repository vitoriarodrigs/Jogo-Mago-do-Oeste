package Classes.Feitico;

public class LacoDeRepeticao extends TrechoDeCodigo{

    private int duracao;

    public LacoDeRepeticao ( int duracao, int custo){
        super(custo);
        this.duracao = duracao;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }
}

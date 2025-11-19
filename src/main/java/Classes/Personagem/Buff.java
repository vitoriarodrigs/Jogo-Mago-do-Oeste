package Classes.Personagem;

public class Buff {
    private int duracao;
    private TipoBuff tipo;
    private int poder;

    public Buff(TipoBuff tipo, int duracao, int poder) {
        this.tipo = tipo;
        this.duracao = duracao;
        this.poder = poder;
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public TipoBuff getTipo() {
        return tipo;
    }

    public void setTipo(TipoBuff tipo) {
        this.tipo = tipo;
    }

    public int getPoder() {
        return poder;
    }

    public void setPoder(int poder) {
        this.poder = poder;
    }

    public void diminuirDuracao(int segundos){
        duracao -=segundos;
    }

}

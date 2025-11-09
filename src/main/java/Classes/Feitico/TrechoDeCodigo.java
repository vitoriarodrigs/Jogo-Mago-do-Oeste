package Classes.Feitico;

public abstract class TrechoDeCodigo {

    private int custo;

    public TrechoDeCodigo (int custo){
        this.custo = custo;
    }

    public int getCusto() {
        return custo;
    }

    public void setCusto(int custo) {
        this.custo = custo;
    }
}

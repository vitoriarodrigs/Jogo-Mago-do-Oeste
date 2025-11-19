package Classes.Feitico;

import java.util.ArrayList;

public class LacoDeRepeticao extends TrechoDeCodigo{

    protected int duracao;
    protected ArrayList<Magia> magias;

    public LacoDeRepeticao ( int duracao, int custo){
        super(custo);
        this.duracao = duracao;
        this.magias = new ArrayList<>();
    }

    public int getDuracao() {
        return duracao;
    }

    public void setDuracao(int duracao) {
        this.duracao = duracao;
    }

    public ArrayList<Magia> getMagias() {
        return magias;
    }

    public void setMagia(Magia magia){
        if(magias.size() == 3){
            return;
        }
        magias.add(magia);

    }
}

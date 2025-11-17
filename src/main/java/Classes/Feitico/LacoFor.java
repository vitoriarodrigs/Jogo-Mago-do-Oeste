package Classes.Feitico;

import java.util.ArrayList;

public class LacoFor extends LacoDeRepeticao{

    private ArrayList<Magia> magias;

    public LacoFor(int duracao, int custo) {
        super(duracao, custo);
        this.magias = new ArrayList<>();
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

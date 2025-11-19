package Classes.Feitico;

import java.util.ArrayList;

public class LacoFor extends LacoDeRepeticao{

    private ArrayList<Magia> magias;

    public LacoFor(int duracao, int custo) {
        super(duracao, custo);
        this.magias = new ArrayList<>();
    }

}

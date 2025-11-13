package Classes.Personagem.Inimigo;

import Classes.Personagem.Personagem;

public abstract class Inimigo extends Personagem {

    protected int colldownDeAtaque;

    public Inimigo(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima);
        this.colldownDeAtaque = colldownDeAtaque;
    }

    public int getColldownDeAtaque() {
        return colldownDeAtaque;
    }

    public void setColldownDeAtaque(int colldownDeAtaque) {
        this.colldownDeAtaque = colldownDeAtaque;
    }
}

package Classes.Personagem.Inimigo;

import Classes.Personagem.Personagem;
import Classes.Personagem.Player;

import java.util.Random;

public abstract class Inimigo extends Personagem {

    protected int colldownDeAtaque;
    protected String sprite;

    public Inimigo(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima);
        this.colldownDeAtaque = colldownDeAtaque;
        this.sprite = "";
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public int getColldownDeAtaque() {
        return colldownDeAtaque;
    }

    public void setColldownDeAtaque(int colldownDeAtaque) {
        this.colldownDeAtaque = colldownDeAtaque;
    }
    public void atacar( Player jogador){

    }
}

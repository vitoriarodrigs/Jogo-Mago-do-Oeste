package Classes.Personagem;

import Classes.Feitico.LacoDeRepeticao;

public abstract class Personagem {

    protected int hpAtual;
    protected int hpMaximo;
    protected int manaAtual;
    protected int manaMaxima;

    public Personagem (int hpMaximo, int manaMaxima){
        this.hpMaximo = hpMaximo;
        this.hpAtual = hpMaximo;
        this.manaAtual = manaMaxima;
        this.manaMaxima = manaMaxima;
    }

    public int getManaAtual() {
        return manaAtual;
    }

    public int getManaMaxima() {
        return manaMaxima;
    }

    public void restaurarMana( int mana){
        manaAtual += mana;
        if (manaAtual > manaMaxima){
            manaAtual = manaMaxima;
        }
    }
    public void gastarManaAtual(int mana){
        this.manaAtual -=mana;
    }
    public void tomarDano(int dano) {
        hpAtual -= dano;
        if (hpAtual < 0) hpAtual = 0;
    }

    public void restaurarHp(int cura) {
        hpAtual += cura;
        if (hpAtual > hpMaximo) hpAtual = hpMaximo;
    }

    public int getHpAtual() {
        return hpAtual;
    }

    public int getHpMaximo() {
        return hpMaximo;
    }
}

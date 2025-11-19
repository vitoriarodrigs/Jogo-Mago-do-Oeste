package Classes.Personagem;

import Classes.Feitico.LacoDeRepeticao;

import java.util.ArrayList;
import java.util.List;

public abstract class Personagem {

    protected int hpAtual;
    protected int hpMaximo;
    protected int manaAtual;
    protected int manaMaxima;
    protected ArrayList<Buff> buffs;

    public Personagem (int hpMaximo, int manaMaxima){
        this.hpMaximo = hpMaximo;
        this.hpAtual = hpMaximo;
        this.manaAtual = manaMaxima;
        this.manaMaxima = manaMaxima;
        this.buffs = new ArrayList<>();
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


    public ArrayList<Buff> getBuffs() {
        return buffs;
    }

    public void setBuffs(ArrayList<Buff> buffs) {
        this.buffs = buffs;
    }
    public void addBuff(Buff buff){
        buffs.add(buff);
    }

    public void atualizarTempoBuffs() {
        List<Buff> remover = new ArrayList<>();

        for (Buff buff : buffs) {
            buff.diminuirDuracao(1);
            if (buff.getDuracao() <= 0) {
                remover.add(buff);
            }
        }

        buffs.removeAll(remover);
    }
}

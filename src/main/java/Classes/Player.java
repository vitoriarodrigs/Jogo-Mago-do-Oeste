package Classes;

import Classes.Feitico.LacoDeRepeticao;
import Classes.Feitico.LacoFor;
import Classes.Feitico.Magia;
import Classes.Feitico.TrechoDeCodigo;

public class Player {

    private int hpAtual;
    private int hpMaximo;
    private int manaAtual;
    private int manaMaxima;

    private LacoDeRepeticao laco = null;

    public Player (int hpMaximo, int manaMaxima){
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
    public void comprarFeitico(int custo){
        if (custo <= manaAtual){
            manaAtual -= custo;
        }
        if(manaAtual < 0){
            manaAtual = 0;
        }
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

    public LacoDeRepeticao getLaco() {
        return laco;
    }

    public void setLaco(LacoDeRepeticao laco) {
        this.laco = laco;
    }
    public void removerLaco(){
        laco = null;
    }
    public void comprarMagiaBase(LacoDeRepeticao laco){
        if(manaAtual >= laco.getCusto()){
            manaAtual -= laco.getCusto();
            setLaco(laco);
        }else {
            System.out.println("o jogoador não tem mana suficiente");
        }
    }
    public void comprarMagia(Magia magia){
        if(laco == null){
            System.out.println("o jogador precisa comprar um laço de repetição primeiro");
            return;
        }else{
            if(manaAtual >= laco.getCusto()) {
                manaAtual -= laco.getCusto();
                if (laco instanceof LacoFor) {
                    ((LacoFor) laco).setMagia(magia);
                }
            }else{
                System.out.println("o jogoador não tem mana suficiente");
            }
        }

    }
}

package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Feitico.TipoMagia;
import Classes.Personagem.Buff;
import Classes.Personagem.Personagem;
import Classes.Personagem.Player;
import Classes.Personagem.TipoBuff;

import java.util.ArrayList;
import java.util.Random;

public abstract class Inimigo extends Personagem {

    protected int colldownDeAtaque;
    protected String sprite;
    protected String lancarMagiaSprite;
    protected String magiaFracaSprite;
    protected String magiaForteSprite;
    protected String cenarioSprite;
    protected TipoAtaque ataqueEscolhido;
    protected int danoDoAtaque;
    protected ModoAtaque modoDeAtaque;
    protected int precoDoAtaque;
    protected NomeMagia elemento;
    protected int colldownAtual;
    protected boolean atacando;
    protected String infoEstrategia;

    public Inimigo(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima);
        this.colldownDeAtaque = colldownDeAtaque;
        this.sprite = "";
        this.lancarMagiaSprite = "";
        this.magiaFracaSprite = "";
        this.magiaForteSprite = "";
        this.cenarioSprite = "";
        this.ataqueEscolhido = null;
        this.modoDeAtaque = null;
        this.precoDoAtaque = 10;
        this.elemento = null;
        this.danoDoAtaque = 0;
        this.colldownAtual = colldownDeAtaque;
        this.atacando = false;
        this.infoEstrategia = "";
    }

    public String getSprite() {
        return sprite;
    }

    public void setSprite(String sprite) {
        this.sprite = sprite;
    }

    public NomeMagia getElemento() {
        return elemento;
    }

    public String getLancarMagiaSprite() {
        return lancarMagiaSprite;
    }

    public String getMagiaFracaSprite() {
        return magiaFracaSprite;
    }

    public String getMagiaForteSprite() {
        return magiaForteSprite;
    }

    public String getCenarioSprite() {
        return cenarioSprite;
    }
    public int getColldownAtual() {
        return colldownAtual;
    }

    public String getInfoEstrategia() {
        return infoEstrategia;
    }

    public boolean isAtacando() {
        return atacando;
    }

    public void setAtacando(boolean atacando) {
        this.atacando = atacando;
    }

    public void atualizarCowldown(){
        if(colldownAtual == 0){
            colldownAtual = colldownDeAtaque + getPoderBuff(TipoBuff.WATER_DEBUFF);
        }
        if(atacando == true){
            return;
        }
        if(colldownAtual >0){
            colldownAtual -=1;
        }
    }

    public TipoAtaque getAtaqueEscolhido() {
        return ataqueEscolhido;
    }

    public ModoAtaque getModoDeAtaque() {
        return modoDeAtaque;
    }

    public void setAtaqueEscolhido(TipoAtaque ataqueEscolhido) {
        this.ataqueEscolhido = ataqueEscolhido;
    }

    public void setModoDeAtaque(ModoAtaque modoDeAtaque) {
        this.modoDeAtaque = modoDeAtaque;
    }

    public int getPrecoDoAtaque() {
        return precoDoAtaque;
    }

    public void setPrecoDoAtaque(int precoDoAtaque) {
        this.precoDoAtaque = precoDoAtaque;
    }

    public int getDanoDoAtaque() {
        return danoDoAtaque;
    }

    public void setDanoDoAtaque(int danoDoAtaque) {
        this.danoDoAtaque = danoDoAtaque;
    }

    public int getColldownDeAtaque() {
        return colldownDeAtaque;
    }

    public void setColldownDeAtaque(int colldownDeAtaque) {
        this.colldownDeAtaque = colldownDeAtaque;
    }

    public boolean podeAtacar(int custo){

        if(manaAtual >= custo + getPoderThunder()){
            return true;
        }else{
            return false;
        }
    }
    public void definirAtaque(){
    }
    public void atacar( Player jogador){
    }

}

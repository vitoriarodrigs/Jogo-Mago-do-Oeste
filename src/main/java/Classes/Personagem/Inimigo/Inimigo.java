package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Feitico.TipoMagia;
import Classes.Personagem.Personagem;
import Classes.Personagem.Player;

import java.util.Random;

public abstract class Inimigo extends Personagem {

    protected int colldownDeAtaque;
    protected String sprite;
    protected String lancarMagiaSprite;
    protected String magiaFracaSprite;
    protected String magiaForteSprite;
    protected TipoAtaque ataqueEscolhido;
    protected ModoAtaque modoDeAtaque;
    protected int precoDoAtaque;
    protected NomeMagia elemento;

    public Inimigo(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima);
        this.colldownDeAtaque = colldownDeAtaque;
        this.sprite = "";
        this.lancarMagiaSprite = "";
        this.magiaForteSprite = "";
        this.magiaForteSprite = "";
        this.ataqueEscolhido = null;
        this.modoDeAtaque = null;
        this.precoDoAtaque = 10;
        this.elemento = null;
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

    public int getColldownDeAtaque() {
        return colldownDeAtaque;
    }

    public void setColldownDeAtaque(int colldownDeAtaque) {
        this.colldownDeAtaque = colldownDeAtaque;
    }
    public boolean podeAtacar(int custo){
        if(manaAtual >= custo){
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

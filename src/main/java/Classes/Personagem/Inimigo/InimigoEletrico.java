package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Personagem.Player;

import java.util.Random;

public class InimigoEletrico extends Inimigo{

    public InimigoEletrico(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/magoEletrico.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaEletrico.png";
        this.magiaFracaSprite = "/images/Efeitos/ataqueEletricoFraco.png";
        this.magiaForteSprite = "/images/Efeitos/ataqueEletricoForte.png";
        this.cenarioSprite = "/images/Backgrounds/backgroundMagoEletrico.jpg";
        this.elemento = NomeMagia.THUNDER;
    }
    @Override
    public void definirAtaque(){
        escolher();
        while (podeAtacar(precoDoAtaque) == false){
            escolher();
        }
    }
    public void escolher (){
        Random random = new Random();

        int escolha = random.nextInt(2)+1;

        if(escolha == 1){
            setAtaqueEscolhido(TipoAtaque.FRACO);
            setModoDeAtaque(ModoAtaque.HITS);
            setPrecoDoAtaque(4);
            if(2 - getPoderThunder() > 0){
                setDanoDoAtaque(2 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }else{
            setAtaqueEscolhido(TipoAtaque.FORTE);
            setModoDeAtaque(ModoAtaque.HITS);
            setPrecoDoAtaque(8);
            if(4 - getPoderThunder() > 0){
                setDanoDoAtaque(4 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }
    }
    @Override
    public void atacar (Player jogador){
        if(ataqueEscolhido == TipoAtaque.FRACO){
            ataqueFraco(jogador);
        }else{
            ataqueForte(jogador);
        }
    }

    public void ataqueFraco(Player jogador){
        setColldownDeAtaque(5 + getPoderWater());
        if(2 - getPoderThunder() > 0){
            jogador.tomarDano(2 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
    public void ataqueForte(Player jogador){
        setColldownDeAtaque(5 + getPoderWater());
        if(4 - getPoderThunder() > 0){
            jogador.tomarDano(4 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
}

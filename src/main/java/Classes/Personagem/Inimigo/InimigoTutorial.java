package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Feitico.TipoMagia;
import Classes.Personagem.Player;

import java.util.Random;

public class InimigoTutorial extends Inimigo{

    public InimigoTutorial(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/personagemTutorial.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaTutorial.png";
        this.magiaFracaSprite = "/images/Efeitos/magiaGeloPequena.png";
        this.magiaForteSprite = "/images/Efeitos/magiaGeloGrande.png";
        this.elemento = NomeMagia.GELO;
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
            setModoDeAtaque(ModoAtaque.HORIZONTAL_ESQUERDA);
            setPrecoDoAtaque(4);
            if(5 - getPoderThunder() > 0){
                setDanoDoAtaque(5 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }else{
            setAtaqueEscolhido(TipoAtaque.FORTE);
            setModoDeAtaque(ModoAtaque.DIAGONAL_ESQUERDA);
            setPrecoDoAtaque(8);
            if(10 - getPoderThunder() > 0){
                setDanoDoAtaque(5 - getPoderThunder());
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
            if(5 - getPoderThunder() > 0){
                jogador.tomarDano(5 - getPoderThunder());
            }else{
                jogador.tomarDano(0);
            }
    }
    public void ataqueForte(Player jogador){
            setColldownDeAtaque(5 + getPoderWater());
            if(10 - getPoderThunder() > 0){
                jogador.tomarDano(10 - getPoderThunder());
            }else{
                jogador.tomarDano(0);
            }
    }
}

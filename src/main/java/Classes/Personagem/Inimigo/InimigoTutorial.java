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
        Random random = new Random();

        int escolha = random.nextInt(2)+1;

        if(escolha == 1){
            setAtaqueEscolhido(TipoAtaque.FRACO);
            setModoDeAtaque(ModoAtaque.HORIZONTAL_ESQUERDA);
            setPrecoDoAtaque(4);
        }else{
            setAtaqueEscolhido(TipoAtaque.FRACO);
            setModoDeAtaque(ModoAtaque.HORIZONTAL_ESQUERDA);
            setPrecoDoAtaque(4);
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
        if (manaAtual >= 4){

            setColldownDeAtaque(5);

            jogador.tomarDano(5);
            return;
        }else{
            setColldownDeAtaque(0);
            return;
        }

    }
    public void ataqueForte(Player jogador){
        if (manaAtual >= 8){
            setColldownDeAtaque(5);
            jogador.tomarDano(10);
            return;
        }else{
            setColldownDeAtaque(0);
            return;
        }
    }
}

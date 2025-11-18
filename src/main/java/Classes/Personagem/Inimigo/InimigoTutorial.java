package Classes.Personagem.Inimigo;

import Classes.Personagem.Player;

import java.util.Random;

public class InimigoTutorial extends Inimigo{

    public InimigoTutorial(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/personagemTutorial.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaTutorial.png";
    }
    @Override
    public void atacar( Player jogador){
        Random random = new Random();

        int escolha = random.nextInt(2)+1;

        if(escolha == 1){
            ataqueFraco(jogador);
        }else{
            ataqueForte(jogador);
        }
    }

    public void ataqueFraco(Player jogador){
        if (manaAtual >= 4){
            gastarManaAtual(4);
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
            gastarManaAtual(8);
            setColldownDeAtaque(5);
            jogador.tomarDano(10);
            return;
        }else{
            setColldownDeAtaque(0);
            return;
        }
    }
}

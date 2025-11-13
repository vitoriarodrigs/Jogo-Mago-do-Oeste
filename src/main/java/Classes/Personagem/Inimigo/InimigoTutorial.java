package Classes.Personagem.Inimigo;

import java.util.Random;

public class InimigoTutorial extends Inimigo{

    public InimigoTutorial(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
    }

    public void atacar(){
        Random random = new Random();

        int escolha = random.nextInt(2)+1;

        if(escolha == 1){
            ataqueFraco();
        }else{
            ataqueForte();
        }
    }

    public int ataqueFraco(){
        if (manaAtual >= 4){
            gastarManaAtual(4);
            setColldownDeAtaque(5000);
            return 5;
        }else{
            setColldownDeAtaque(0);
            return 0;
        }

    }
    public int ataqueForte(){
        if (manaAtual >= 8){
            gastarManaAtual(8);
            setColldownDeAtaque(5000);
            return 10;
        }else{
            setColldownDeAtaque(0);
            return 0;
        }
    }
}

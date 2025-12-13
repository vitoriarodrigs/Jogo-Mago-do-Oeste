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
        this.cenarioSprite = "/images/Backgrounds/fundoTutorial.png";
        this.elemento = NomeMagia.GELO;
        this.pergaminho = ("/images/Hud/IconeMapa.png");
        this.pergaminhoInfo.add("Mapa dos Magos Cardinais");
        this.pergaminhoInfo.add("Este mapa possui a localização de todos os magos cardinais, diz a lenda que ao serem derrotaos, concedem conhecmentos antigos esquecidos.");
        this.dialogoVitoria.add("Caramba você é bem forte!");
        this.dialogoVitoria.add("Nestas terras distantes existem 3 grandes magos, chamados de Magos Cardinais.");
        this.dialogoVitoria.add("Porém, infelizmente eu não sou um deles.");
        this.dialogoVitoria.add("Eles são bem fortes, mas acredito que você consiga os vencer.");
        this.dialogoVitoria.add("Diz a lenda que caso você os enfrente e vença, eles te darão um pergaminho contendo conhecimento antigo.");
        this.dialogoVitoria.add("Nessas terras, nunca vi ninguem conseguir os vencer.");
        this.dialogoVitoria.add("Eu possuo um mapa com a localização de todos esses magos.");
        this.dialogoVitoria.add("De qualquer forma, acho que isso seria mais útil a você do que a mim.");
        this.dialogoVitoria.add("Aqui, pegue o mapa.");
        this.dialogoVitoria.add("Preciso continuar meu treinamento.");
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
                setDanoDoAtaque(10 - getPoderThunder());
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

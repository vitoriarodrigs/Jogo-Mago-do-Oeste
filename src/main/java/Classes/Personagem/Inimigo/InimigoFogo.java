package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Personagem.Buff;
import Classes.Personagem.Player;
import Classes.Personagem.TipoBuff;

import java.util.Random;

public class InimigoFogo extends Inimigo{

    private int escudoFogoMaximo;
    private int escudoFogoAtual;

    public InimigoFogo(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);

        this.sprite = "/images/Personagens/magoFogo.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaFogo.png";
        this.magiaFracaSprite = "/images/Efeitos/ataqueFogoFraco.png";
        this.magiaForteSprite = "/images/Efeitos/ataqueFogoForte.png";
        this.cenarioSprite = "/images/Backgrounds/backgroundMagoFogo.jpg";
        this.elemento = NomeMagia.FOGO;
        this.escudoFogoMaximo = 100;
        this.escudoFogoAtual = escudoFogoMaximo;
        this.infoEstrategia = "Ataque com Debuffs para quebrar o escudo do oponente";
    }
    public int getEscudoFogoAtual() {
        return escudoFogoAtual;
    }

    public void setEscudoFogoAtual(int escudoFogoAtual) {
        this.escudoFogoAtual = escudoFogoAtual;
    }

    public int getEscudoFogoMaximo() {
        return escudoFogoMaximo;
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
            setModoDeAtaque(ModoAtaque.DIAGONAL_ESQUERDA);
            setPrecoDoAtaque(4);
            if(8 - getPoderThunder() > 0){
                setDanoDoAtaque(8 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }else{
            setAtaqueEscolhido(TipoAtaque.FORTE);
            setModoDeAtaque(ModoAtaque.FADE_IN);
            setPrecoDoAtaque(8);
            if(3 - getPoderThunder() > 0){
                setDanoDoAtaque(3 - getPoderThunder());
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
        if(8 - getPoderThunder() > 0){
            jogador.tomarDano(8 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
    public void ataqueForte(Player jogador) {
        if (3 - getPoderThunder() > 0) {
            Buff debuff = new Buff(TipoBuff.FIRE_DEBUFF, 10, 3 - getPoderThunder());
            jogador.addBuff(debuff);

        }
    }
    @Override
    public void tomarDano(int dano){
        if(escudoFogoAtual > 0){
            return;
        }
        if(escudoFogoAtual == 0){
            if(hpAtual - dano > 0){
                hpAtual -= dano;
            }else{
                hpAtual = 0;
            }
        }
    }

    @Override
    public void atualizarTempoBuffs() {
        if(escudoFogoAtual > 0){
            if(escudoFogoAtual - getPoderBuffsTotal() < 0){
                escudoFogoAtual = 0;
            }else{
                escudoFogoAtual -= getPoderBuffsTotal();
            }
        }
        super.atualizarTempoBuffs();
    }
}

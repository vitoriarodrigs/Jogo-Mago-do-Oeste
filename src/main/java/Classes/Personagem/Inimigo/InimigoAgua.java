package Classes.Personagem.Inimigo;

import Classes.Feitico.*;
import Classes.Personagem.Buff;
import Classes.Personagem.Player;
import Classes.Personagem.TipoBuff;

import java.util.Random;

public class InimigoAgua extends Inimigo{

    private int escudoAguaMaximo;
    private int escudoAguaAtual;
    private LacoDeRepeticao laco;
    private LacoDeRepeticao lacoHeroi;

    public InimigoAgua(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/magoAgua.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaAgua.png";
        this.magiaFracaSprite = "/images/Efeitos/ataqueAguaFraco.png";
        this.cenarioSprite = "/images/Backgrounds/backgroundMagoAgua.jpg";
        this.elemento = NomeMagia.WATER;
        this.escudoAguaMaximo = 25;
        this.escudoAguaAtual = escudoAguaMaximo;
        this.laco = null;
        this.lacoHeroi = null;
        sortearFraqueza();
    }

    public int getEscudoAguaMaximo() {
        return escudoAguaMaximo;
    }

    public void setEscudoAguaMaximo(int escudoAguaMaximo) {
        this.escudoAguaMaximo = escudoAguaMaximo;
    }

    public int getEscudoAguaAtual() {
        return escudoAguaAtual;
    }

    public void setEscudoAguaAtual(int escudoAguaAtual) {
        this.escudoAguaAtual = escudoAguaAtual;
    }

    public void setLacoHeroi(LacoDeRepeticao lacoHeroi) {
        this.lacoHeroi = lacoHeroi;
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
            if(8 - getPoderThunder() > 0){
                setDanoDoAtaque(8 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }else{
            setAtaqueEscolhido(TipoAtaque.FORTE);
            setModoDeAtaque(ModoAtaque.DRAW_CARD);
            setPrecoDoAtaque(8);

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

    public boolean podeTomarDano(LacoDeRepeticao lacoRecebido){

        if(lacoRecebido.getClass() == laco.getClass()){
            for(Magia magiaRecebida :lacoRecebido.getMagias()){
                for(Magia magia :laco.getMagias()){
                    if(magiaRecebida.getNome() == magia.getNome()){
                        return true;
                    }
                }
            }
        }

        return false;
    }

    @Override
    public void tomarDano(int dano){

        if(escudoAguaAtual == 0){
            if(hpAtual - dano > 0){
                hpAtual -= dano;
            }else{
                hpAtual = 0;
            }
        }
    }
    public Magia sortearMagiaFraqueza(){
        Random random = new Random();
        int magiaSorteada = random.nextInt(3)+1;

        switch (magiaSorteada){
            case 1: Magia magia1 = new Magia(TipoMagia.ATAQUE,NomeMagia.FOGO,1,1);
            return magia1;

            case 2: Magia magia2 = new Magia(TipoMagia.ATAQUE,NomeMagia.WATER,1,1);
                return magia2;

            case 3: Magia magia3 = new Magia(TipoMagia.ATAQUE,NomeMagia.THUNDER,1,1);
                return magia3;
        }
        return null;
    }

    public void sortearFraqueza(){
        Random random = new Random();
        int lacoSorteado = random.nextInt(2)+1;

        Magia magia1 = sortearMagiaFraqueza();
        Magia magia2 = sortearMagiaFraqueza();

        while (magia1.getNome() == magia2.getNome()){
            magia2 = sortearMagiaFraqueza();
        }


        if(lacoSorteado == 1){
            LacoFor laco1 = new LacoFor(1,1);
            laco1.setMagia(magia1);
            laco1.setMagia(magia2);
            this.laco = laco1;

            this.infoEstrategia = "Quebre o escudo do oponente com laço [For] com "+
                    " ["+laco.getMagias().get(0).getNome()+"]"+
                    " ou ["+laco.getMagias().get(1).getNome()+"]";

        }else{
            LacoWhile laco2 = new LacoWhile(1,1);
            laco2.setMagia(magia1);
            laco2.setMagia(magia2);
            this.laco = laco2;

            this.infoEstrategia = "Quebre o escudo do oponente com laço [While] com"+
                    " ["+laco.getMagias().get(0).getNome()+"]"+
                    " ou ["+laco.getMagias().get(1).getNome()+"]";
        }

    }
}

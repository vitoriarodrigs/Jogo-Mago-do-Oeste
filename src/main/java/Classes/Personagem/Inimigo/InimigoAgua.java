package Classes.Personagem.Inimigo;

import Classes.Feitico.*;
import Classes.Personagem.Buff;
import Classes.Personagem.Player;
import Classes.Personagem.TipoBuff;

import java.util.Random;

public class InimigoAgua extends Inimigo{

    private int escudoAguaMaximo;
    private int escudoAguaAtual;
    private LacoDeRepeticao lacoFraqueza;
    private int magiaFortePity;

    public InimigoAgua(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/magoAgua.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaAgua.png";
        this.magiaFracaSprite = "/images/Efeitos/ataqueAguaFraco.png";
        this.magiaForteSprite = "/images/Efeitos/ataqueAguaFraco.png";
        this.cenarioSprite = "/images/Backgrounds/backgroundMagoAgua.jpg";
        this.elemento = NomeMagia.WATER;
        this.escudoAguaMaximo = 25;
        this.escudoAguaAtual = escudoAguaMaximo;
        this.lacoFraqueza = null;
        this.magiaFortePity =0;
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

    public LacoDeRepeticao getLacoFraqueza() {
        return lacoFraqueza;
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

        if(magiaFortePity == 3){
            escolha = 2;
            magiaFortePity = 0;
        }

        if(escolha == 1){

            magiaFortePity ++;

            setAtaqueEscolhido(TipoAtaque.FRACO);
            setModoDeAtaque(ModoAtaque.HORIZONTAL_ESQUERDA);
            setPrecoDoAtaque(4);
            if(15 - getPoderThunder() > 0){
                setDanoDoAtaque(15 - getPoderThunder());
            }else{
                setDanoDoAtaque(0);
            }
        }else{
            magiaFortePity = 0;

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
        if(15 - getPoderThunder() > 0){
            jogador.tomarDano(15 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
    public void ataqueForte(Player jogador) {
        return;
    }


    @Override
    public void tomarDano(LacoDeRepeticao laco,Magia magia){
        if(escudoAguaAtual > 0){
            if(laco.getClass() == lacoFraqueza.getClass()){
                for(Magia magiaFraqueza : lacoFraqueza.getMagias()){
                    if(magia.getNome() == magiaFraqueza.getNome()){
                        escudoAguaAtual--;
                        break;
                    }
                }
            }
        }

        if(escudoAguaAtual == 0){
            if(hpAtual - magia.getPoder() > 0){
                hpAtual -= magia.getPoder();
            }else{
                hpAtual = 0;
            }
        }
    }
    @Override
    public void tomarDano(int dano){
        if(escudoAguaAtual > 0){
            return;
        }
        if(escudoAguaAtual == 0){
            if(hpAtual - dano > 0){
                hpAtual -= dano;
            }else{
                hpAtual = 0;
            }
        }
    }

    @Override
    public void atualizarTempoBuffs() {
        if(escudoAguaAtual > 0){
            if(lacoFraqueza instanceof LacoWhile){
                for (Buff buff : buffs){
                    for(Magia magia : lacoFraqueza.getMagias()){
                        if(magia.getNome() == NomeMagia.FOGO && buff.getTipo() == TipoBuff.FIRE_DEBUFF ||
                                magia.getNome() == NomeMagia.WATER && buff.getTipo() == TipoBuff.WATER_DEBUFF||
                                magia.getNome() == NomeMagia.THUNDER && buff.getTipo() == TipoBuff.THUNDER_DEBUFF )
                        {
                            if(escudoAguaAtual -1 > 0){
                                escudoAguaAtual --;
                            }else{
                                escudoAguaAtual = 0;
                            }
                        }
                    }
                }
            }
        }

        super.atualizarTempoBuffs();
    }

    public int sortearCarta(){
        Random random = new Random();
        int numeroRand = random.nextInt(6)+1;

        if(escudoAguaAtual > 0){
            while (numeroRand == 2){
                numeroRand = random.nextInt(6)+1;
            }
        }
        return numeroRand;
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
            lacoFraqueza = laco1;

            this.infoEstrategia = "Quebre o escudo do oponente com laço [For] com "+
                    " ["+lacoFraqueza.getMagias().get(0).getNome()+"]"+
                    " ou ["+lacoFraqueza.getMagias().get(1).getNome()+"]";

        }else{
            LacoWhile laco2 = new LacoWhile(1,1);
            laco2.setMagia(magia1);
            laco2.setMagia(magia2);
            lacoFraqueza = laco2;

            this.infoEstrategia = "Quebre o escudo do oponente com laço [While] com"+
                    " ["+lacoFraqueza.getMagias().get(0).getNome()+"]"+
                    " ou ["+lacoFraqueza.getMagias().get(1).getNome()+"]";
        }

    }
}

package Classes.Personagem.Inimigo;

import Classes.Feitico.NomeMagia;
import Classes.Personagem.Player;

import java.util.Random;

public class InimigoEletrico extends Inimigo{

    private int escudoEletricoMaximo;
    private int escudoEletricoAtual;

    public InimigoEletrico(int hpMaximo, int manaMaxima, int colldownDeAtaque) {
        super(hpMaximo, manaMaxima, colldownDeAtaque);
        this.sprite = "/images/Personagens/magoEletrico.png";
        this.lancarMagiaSprite="/images/Efeitos/lancarMagiaEletrico.png";
        this.magiaFracaSprite = "/images/Efeitos/ataqueEletricoFraco.png";
        this.magiaForteSprite = "/images/Efeitos/ataqueEletricoForte.png";
        this.cenarioSprite = "/images/Backgrounds/backgroundMagoEletrico.jpg";
        this.elemento = NomeMagia.THUNDER;
        this.escudoEletricoMaximo = 25;
        this.escudoEletricoAtual = escudoEletricoMaximo;
        this.infoEstrategia = "Ataque várias vezes para quebrar o escudo do oponente";
        this.pergaminho = ("/images/Hud/pergaminhoThunder.png");
        this.pergaminhoInfo.add("Pergaminho de Vetor");
        this.pergaminhoInfo.add("Este pergaminho possui conhecimentos antigos sobre Vetores.");
        this.dialogoVitoria.add("Maldição, fui derrotada!");
        this.dialogoVitoria.add("Como pode existir um mago capáz de resistir aos meus rápidos golpes elétricos?");
        this.dialogoVitoria.add("Na programação quando precisamos salvar vários valores sem a necessidade de criar várias variáveis utilizamos [VETORES].");
        this.dialogoVitoria.add("Eles são bastante úteis, pois permitem agrupar qualquer tipo de valor, facilitando na orgamização e otmização de um código.");
        this.dialogoVitoria.add("Imagine que um vetor é como um conjunto de magias, e ao comprar esse conjunto, todas as magias preenchem seu laço de repetição.");
        this.dialogoVitoria.add("Isso agilizaria bastante a criação de feitiços.");
        this.dialogoVitoria.add("Dependendo da linguágem, existem diferentes bibliotecas que facilitam na ordenação dos vetores.");
        this.dialogoVitoria.add("Ok, sei reconhecer alguem forte e você é merecedor do meu pergaminho, pegue.");
        this.dialogoVitoria.add("Preciso continuar meu treinamento.");
    }

    public int getEscudoEletricoAtual() {
        return escudoEletricoAtual;
    }

    public void setEscudoEletricoAtual(int escudoEletricoAtual) {
        this.escudoEletricoAtual = escudoEletricoAtual;
    }

    public int getEscudoEletricoMaximo() {
        return escudoEletricoMaximo;
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
        //setColldownDeAtaque(5 + getPoderWater());
        if(2 - getPoderThunder() > 0){
            jogador.tomarDano(2 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
    public void ataqueForte(Player jogador){
        //setColldownDeAtaque(5 + getPoderWater());
        if(4 - getPoderThunder() > 0){
            jogador.tomarDano(4 - getPoderThunder());
        }else{
            jogador.tomarDano(0);
        }
    }
    @Override
    public void tomarDano(int dano){
        if(escudoEletricoAtual > 0){
            escudoEletricoAtual --;
            return;
        }
        if(escudoEletricoAtual == 0){
            if(hpAtual - dano > 0){
                hpAtual -= dano;
            }else{
                hpAtual = 0;
            }
        }
    }

}

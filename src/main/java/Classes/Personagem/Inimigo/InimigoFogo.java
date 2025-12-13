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
        this.elemento = NomeMagia.FIRE;
        this.escudoFogoMaximo = 100;
        this.escudoFogoAtual = escudoFogoMaximo;
        this.infoEstrategia = "Ataque com Debuffs para quebrar o escudo do oponente";
        this.pergaminho = ("/images/Hud/pergaminhoFire.png");
        this.pergaminhoInfo.add("Pergaminho de Função");
        this.pergaminhoInfo.add("Este pergaminho possui conhecimentos antigos sobre Funções.");
        this.dialogoVitoria.add("Fui derrotado, isso foi inesperado.");
        this.dialogoVitoria.add("Quem diria que existiria um mago capaz de sobreviver ao [Feitiço de Debuff] criado por mim.");
        this.dialogoVitoria.add("Na programação quando precisamos utilizar trecho de código em diversas partes de um código maior, utilizamos [FUNÇÕES].");
        this.dialogoVitoria.add("Elas agilizam o trabalho, ajudam a evitar erros e organizar o código, pois todas as repetições desse trecho vem de apenas um lugar.");
        this.dialogoVitoria.add("Imagine que uma função é como um feitiço que você deixa salvo e pode o lançar sem precisar o montar.");
        this.dialogoVitoria.add("São tantas as possibilidades... Bom, você é merecedor do meu pergaminho, pegue.");
        this.dialogoVitoria.add("Agora irei aprimorar meu feitiço, adeus.");
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

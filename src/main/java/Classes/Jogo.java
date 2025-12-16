package Classes;

import Classes.Personagem.Inimigo.*;
import Classes.Personagem.Player;

import java.util.ArrayList;

public class Jogo {
    public static Jogo instancia;

    private Player jogador;
    private ArrayList<Inimigo> inimigos;
    //colocar os inimigos
    private String cenaAtual;
    private int inimigoAtual;
    private boolean[] pergaminhos;

    protected Jogo (){
        this.jogador = new Player(100,10);
        this.inimigos = new ArrayList<>();
        this.inimigos.add(new InimigoTutorial(200,10,5));
        this.inimigos.add(new InimigoEletrico(170,10,5));
        this.inimigos.add(new InimigoFogo(170,10,8));
        this.inimigos.add(new InimigoAgua(100,10,5));
        pergaminhos = new boolean[]{
                false,false,false,false
        };
        this.cenaAtual = "";
        this.inimigoAtual = 0;
    }

    //metodo do padrão singleton
    public static Jogo getInstancia(){
        if(instancia == null){
            instancia = new Jogo();
        }
        return instancia;
    }
    public void restaurarValoresIniciais(){
        Player jogador = new Player(100,10);
        setJogador(jogador);

        this.inimigos.set(0,new InimigoTutorial(200,10,5));
        this.inimigos.set(1,new InimigoEletrico(170,10,5));
        this.inimigos.set(2,new InimigoFogo(170,10,8));
        this.inimigos.set(3,new InimigoAgua(100,10,5));
    }

    public Player getJogador() {
        return jogador;
    }

    public void setJogador(Player jogador) {
        this.jogador = jogador;
    }

    public Inimigo getInimigo(int i){
        return inimigos.get(i);
    }

    public Inimigo getInimigoAtual(){
        if(inimigos.get(inimigoAtual) != null){
            return inimigos.get(inimigoAtual);
        }
        return null;
    }
    public int getNumeroInimgigoAtual(){
        return inimigoAtual;
    }
    public void atualizarInimigoAtual(int i){
        this.inimigoAtual = i;
    }
    public void atualizarPergaminho(int i){
        pergaminhos[i] = true;
    }
    public boolean verificarPergaminhos(){
        for(int i = 0; i <pergaminhos.length;i++){
            if(pergaminhos[i] == false){
                return false;
            }
        }
        return true;
    }
    public static void clear() {
        instancia = null;
    }

    public boolean getPergaminho(int i){
       if(i< pergaminhos.length && i >=0){
           return pergaminhos[i];
       }
       return false;
    }
    //sprites dos inimigos e cenas

    public String getCenaAtual() {
        return cenaAtual;
    }

    public void setCenaAtual(String cenaAtual) {
        this.cenaAtual = cenaAtual;
    }

    //fazer metodo com switch para retornar o endereço da cena atual com o inimigo atual(anda faltam as imagens)
}

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
        this.inimigos.add(new InimigoEletrico(50,10,5));
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

    public Player getJogador() {
        return jogador;
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

    public Boolean getPergaminho(int i){
       if(i< pergaminhos.length && i >=0){
           return pergaminhos[i];
       }
       return null;
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

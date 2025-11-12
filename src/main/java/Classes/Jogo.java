package Classes;

public class Jogo {
    public static Jogo instancia;

    private Player jogador;
    //colocar os inimigos
    private String cenaAtual;
    private String inimigoAtual;
    private boolean[] pergaminhos;

    protected Jogo (){
        this.jogador = new Player(100,10);
        pergaminhos = new boolean[]{
                false,false,false,false
        };
        this.cenaAtual = "";
        this.inimigoAtual = "";
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

    public String getInimigoAtual() {
        return inimigoAtual;
    }

    public void setCenaAtual(String cenaAtual) {
        this.cenaAtual = cenaAtual;
    }

    public void setInimigoAtual(String inimigoAtual) {
        this.inimigoAtual = inimigoAtual;
    }

    //fazer metodo com switch para retornar o endereço da cena atual com o inimigo atual(anda faltam as imagens)
}

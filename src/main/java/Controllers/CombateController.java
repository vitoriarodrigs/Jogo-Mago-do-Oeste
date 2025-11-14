package Controllers;

import Classes.Feitico.LacoDeRepeticao;
import Classes.Feitico.LacoFor;
import Classes.Feitico.Magia;
import Classes.Feitico.TrechoDeCodigo;
import Classes.Jogo;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.text.Font;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class CombateController {
    private Player jogador = Jogo.getInstancia().getJogador();
    private Inimigo inimigo = Jogo.getInstancia().getInimigo(0); //arrumar depois
    private ArrayList<TrechoDeCodigo>trechos = new ArrayList<>();
    private int buyTimer = 5;


    @FXML
    private Rectangle Hp1;

    @FXML
    private Rectangle HpBase;

    @FXML
    private Button heal;

    @FXML
    private Button perderHp;

    @FXML
    private Rectangle mana;

    @FXML
    private Rectangle manaBase1;

    @FXML
    private Button lancarFeitico;

    @FXML
    private Label manaQuant;

    @FXML
    private ImageView jogadorIMG;

    @FXML
    private Pane dotesPositionPane;

    @FXML
    private Pane magiasDisponiveisPane;

    @FXML
    private HBox caixaDeMagias;

    @FXML
    private ImageView lacoAtual;

    @FXML
    private Label lacoAtualText;

    @FXML
    private Label timerLabel;

    @FXML
    public void initialize() {
        // Atualiza as barras na inicialização
        atualizarBarraHp();
        atualizarBarraMana();
        loadMagias();
        atualizarMagiasDisponiveis();


        // Timeline para restaurar 1 ponto de mana a cada 1 segundo
        Timeline manaRegen = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            jogador.restaurarMana(1);
            atualizarBarraMana();
        }));
        manaRegen.setCycleCount(Timeline.INDEFINITE); // roda para sempre
        manaRegen.play();

        Timeline timerToBuy = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            //10 por que precisa ter um ultimo lugar pra mostrar o contador e cada item são 2 itens.
            if(trechos.size() < 6){
                if(buyTimer <= 5 && buyTimer != 0){
                    buyTimer -= 1;
                }else{
                    createMagia();
                    atualizarMagiasDisponiveis();
                    buyTimer = 5;
                }
            }else{
                buyTimer = 5;
            }
            timerLabel.setText(String.valueOf(buyTimer));
        }));
        timerToBuy.setCycleCount(Timeline.INDEFINITE); // roda para sempre
        timerToBuy.play();

        Image gif = new Image(getClass().getResource("/images/oia.gif").toExternalForm());
        jogadorIMG.setImage(gif);
    }
    public void loadMagias(){
        LacoFor laco1 = new LacoFor(3,4);
        Magia magia = new Magia("Ataque","Fogo",2,6);

        trechos.add(laco1);
        trechos.add(magia);




    }
    public void createMagia(){
        //implementar melhor quando tiver mais variedades de magias

        String escolha;

        Random random = new Random();
        int sorteado = random.nextInt(2) + 1;
        switch (sorteado){
            case 1 : Magia magia = new Magia("Ataque","Fogo",2,6);
                     trechos.add(magia);
                     return;
            case 2 : LacoFor laco = new LacoFor(3,4);
                     trechos.add(laco);
                     return;
        }
        atualizarMagiasDisponiveis();

    }
    public void atualizarMagiasDisponiveis (){

        if(magiasDisponiveisPane.getChildren().size() > 0){
            magiasDisponiveisPane.getChildren().clear();
        }

        for (int i = 0; i < trechos.size(); i++) {
            TrechoDeCodigo trecho = trechos.get(i);

            final int index = i;

            Label label = new Label();
            label.setFont(new Font("Arial", 25)); // aumenta o tamanho da fonte
            label.setTextFill(Color.WHITE); // cor do texto
             // deixa em negrito
            label.setWrapText(true);
            if(trecho.getCusto() < 10){
                label.setText("0"+String.valueOf(trecho.getCusto()));
            }else{
                label.setText(String.valueOf(trecho.getCusto()));
            }

            String endereco = "";

            // Define o texto da Label conforme o tipo
            if (trecho instanceof LacoFor laco) {
               endereco = "/images/Magias/for.png";
            } else if (trecho instanceof Magia magia) {
                if(magia.getNome().equals("Fogo")){
                    endereco = "/images/Magias/fire.png";
                }else{
                    endereco = "/images/Magias/fire.png";
                }
            } else {
                endereco = "desconhecido";
            }
            Image imagem = new Image(endereco);

            ImageView img = new ImageView(imagem);

            img.setOnMouseClicked(event -> {

                if(trecho instanceof Magia){
                    if(jogador.getLaco() != null){
                        if(jogador.getLaco() instanceof LacoFor){
                            if(caixaDeMagias.getChildren().size() == 3){
                                return;
                            }
                        }
                    }
                }
                if(trecho instanceof LacoDeRepeticao){
                    if(jogador.getLaco() != null){
                        return;
                    }
                }

                trechos.remove(index);
                magiasDisponiveisPane.getChildren().remove(img);
                magiasDisponiveisPane.getChildren().remove(label);
                comprarFeitico(trecho,index);

            });

            // Procura um círculo com o mesmo ID do índice
            for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                if (child instanceof javafx.scene.shape.Circle circle) {
                    if (circle.getId() != null && circle.getId().equals(String.valueOf(i))) {

                        label.setLayoutX(circle.getLayoutX() + 50);
                        label.setLayoutY(circle.getLayoutY() - 20);

                        img.setLayoutX(circle.getLayoutX() - 25);
                        img.setLayoutY(circle.getLayoutY()- 30);
                    }
                }
            }
            magiasDisponiveisPane.getChildren().add(img);
            magiasDisponiveisPane.getChildren().add(label);

            if(trechos.size() < 6 && i+1 == trechos.size()){
                Image imgTimer = new Image("/images/Hud/timerBox.png");

                ImageView imgVTimer = new ImageView(imgTimer);

                for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                    if (child instanceof javafx.scene.shape.Circle circle) {
                        if (circle.getId() != null && circle.getId().equals(String.valueOf(i+1))) {

                            timerLabel.setLayoutX(circle.getLayoutX() + 30);
                            timerLabel.setLayoutY(circle.getLayoutY() - 25);

                            imgVTimer.setLayoutX(circle.getLayoutX() - 25);
                            imgVTimer.setLayoutY(circle.getLayoutY()- 30);
                        }
                    }
                }
                magiasDisponiveisPane.getChildren().add(imgVTimer);
            }

        }
        if(trechos.size() == 0){
            Image imgTimer = new Image("/images/Hud/timerBox.png");

            ImageView imgVTimer = new ImageView(imgTimer);

            for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                if (child instanceof javafx.scene.shape.Circle circle) {
                    if (circle.getId() != null && circle.getId().equals(String.valueOf(0))) {

                        timerLabel.setLayoutX(circle.getLayoutX() + 30);
                        timerLabel.setLayoutY(circle.getLayoutY() - 25);

                        imgVTimer.setLayoutX(circle.getLayoutX() - 25);
                        imgVTimer.setLayoutY(circle.getLayoutY()- 30);
                    }
                }
            }
            magiasDisponiveisPane.getChildren().add(imgVTimer);
        }
    }

    public void atualizarMagiaJogador(){
        LacoDeRepeticao laco = jogador.getLaco();

        if(laco == null){
            return;
        }
        if(caixaDeMagias.getChildren().size()>0){
            caixaDeMagias.getChildren().clear();
        }

        lacoAtual.setVisible(true);
        lacoAtualText.setVisible(true);

        if(laco instanceof LacoFor){
            Image img = new Image("/images/Hud/caixaFor.png");
            lacoAtual.setImage(img);
            lacoAtualText.setText("for(int i = 0: i < "+laco.getDuracao()+"; i++)");


            if(((LacoFor) laco).getMagias().size() > 0){
                for(Magia magia : ((LacoFor) laco).getMagias()){
                    if(magia.getNome().equals("Fogo")){
                        Image magiaImg = new Image("/images/Magias/fire.png");
                        ImageView magiaIV = new ImageView(magiaImg);
                        caixaDeMagias.getChildren().add(magiaIV);
                    }
                }
            }
        }
    }


    public void healOnAction(ActionEvent actionEvent){
        jogador.restaurarHp(10);
        atualizarBarraHp();

    }
    public void perderHpOnAction(ActionEvent actionEvent){
        jogador.tomarDano(10);
        atualizarBarraHp();
    }

    private void atualizarBarraHp() {
        double proporcao = (double) jogador.getHpAtual() / jogador.getHpMaximo();
        Hp1.setWidth(HpBase.getWidth() * proporcao);
    }
    private void atualizarBarraMana(){
        double proporcao = (double) jogador.getManaAtual() / jogador.getManaMaxima();
        mana.setWidth(manaBase1.getWidth() * proporcao);
        if(jogador.getManaAtual() < 10){
            manaQuant.setText("0"+String.valueOf(jogador.getManaAtual()));
        }else{
            manaQuant.setText(String.valueOf(jogador.getManaAtual()));
        }

    }

    void comprarFeitico(TrechoDeCodigo trecho, int i) {
        jogador.comprarFeitico(trecho);


        atualizarMagiasDisponiveis();
        atualizarBarraMana();
        atualizarMagiaJogador();
    }
}

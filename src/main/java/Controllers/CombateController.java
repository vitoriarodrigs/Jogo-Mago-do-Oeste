package Controllers;

import Classes.Feitico.LacoDeRepeticao;
import Classes.Feitico.LacoFor;
import Classes.Feitico.Magia;
import Classes.Feitico.TrechoDeCodigo;
import Classes.Jogo;
import Classes.Player;
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

public class CombateController {
    private Player jogador = Jogo.getInstancia().getJogador();
    private ArrayList<TrechoDeCodigo>trechos = new ArrayList<>();


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

        Image gif = new Image(getClass().getResource("/images/oia.gif").toExternalForm());
        jogadorIMG.setImage(gif);
    }
    public void loadMagias(){
        LacoFor laco1 = new LacoFor(3,4);
        Magia magia = new Magia("Ataque","Fogo",2,6);

        trechos.add(laco1);
        trechos.add(magia);
        trechos.add(magia);
        trechos.add(magia);
        trechos.add(magia);


    }
    public void atualizarMagiasDisponiveis (){

        if(magiasDisponiveisPane.getChildren().size() > 0){
            magiasDisponiveisPane.getChildren().clear();
        }

        for (int i = 0; i < trechos.size(); i++) {
            TrechoDeCodigo trecho = trechos.get(i);

            final int index = i;

            Label label = new Label();
            label.setFont(new Font("Arial", 31)); // aumenta o tamanho da fonte
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
                label.setText("Trecho desconhecido");
            }
            Image imagem = new Image(endereco);

            ImageView img = new ImageView(imagem);

            // 🔹 Aqui você adiciona o evento de clique
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

                trechos.remove(index);
                magiasDisponiveisPane.getChildren().remove(img);
                magiasDisponiveisPane.getChildren().remove(label);
                comprarFeitico(trecho,index);

            });

            // Procura um círculo com o mesmo ID do índice
            for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                if (child instanceof javafx.scene.shape.Circle circle) {
                    if (circle.getId() != null && circle.getId().equals(String.valueOf(i))) {

                        label.setLayoutX(circle.getLayoutX() + 77);
                        label.setLayoutY(circle.getLayoutY() - 15);

                        img.setLayoutX(circle.getLayoutX() - 25);
                        img.setLayoutY(circle.getLayoutY()- 30);
                    }
                }
            }
            magiasDisponiveisPane.getChildren().add(img);
            magiasDisponiveisPane.getChildren().add(label);

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
    @FXML
    void comprarFeiticoOnAction(ActionEvent event) {
        jogador.comprarFeitico(trechos.get(0));
        atualizarBarraMana();
    }
    void comprarFeitico(TrechoDeCodigo trecho, int i) {
        jogador.comprarFeitico(trecho);


        atualizarMagiasDisponiveis();
        atualizarBarraMana();
        atualizarMagiaJogador();
    }
}

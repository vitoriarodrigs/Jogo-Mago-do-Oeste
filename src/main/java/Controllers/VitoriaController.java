package Controllers;

import Classes.Animator;
import Classes.Jogo;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Inimigo.InimigoAgua;
import Classes.Personagem.Inimigo.InimigoFogo;
import Classes.Personagem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;

public class VitoriaController  {

    private Player jogador = Jogo.getInstancia().getJogador();
    private Inimigo inimigo = Jogo.getInstancia().getInimigoAtual();
    private Animator animator = new Animator();
    private int dialogoAtual = 1;
    private ArrayList<String> textoFinal = new ArrayList<>();
    private boolean endGame = false;
    private int textoFinalAtual = 1;

    @FXML
    private Button avancarButton;

    @FXML
    private ImageView combateBg;

    @FXML
    private Button confirmarButton;

    @FXML
    private Pane dialogoPane;

    @FXML
    private Text dialogoText;

    @FXML
    private ImageView enemyItemImg;

    @FXML
    private ImageView enemySprite;

    @FXML
    private ImageView fundoEscuro;

    @FXML
    private ImageView jogadorIMG;

    @FXML
    private ImageView pergaminhoAberto;

    @FXML
    private Pane pergaminhoPane;

    @FXML
    private Text pergaminhoTitleText;

    @FXML
    private Text pergaminhoDescriptionText;

    @FXML
    private ImageView transictionBox;

    @FXML
    public void initialize() {
        if(Jogo.getInstancia().verificarPergaminhos() == false){
            Image heroImg = new Image(getClass().getResource("/images/Personagens/personagemPrincipal.png").toExternalForm());
            jogadorIMG.setImage(heroImg);
            Image enemyImg = new Image(getClass().getResource(inimigo.getSprite()).toExternalForm());
            if(inimigo instanceof InimigoAgua){
                enemySprite.setPreserveRatio(false);
                enemySprite.setFitWidth(300);
                enemySprite.setFitHeight(300);
            }
            enemySprite.setImage(enemyImg);
            Image img = new Image(inimigo.getCenarioSprite());
            combateBg.setImage(img);
            dialogoText.setText(inimigo.getDialogoVitoria().get(0));

            pergaminhoTitleText.setText(inimigo.getPergaminhoInfo().get(0));
            pergaminhoDescriptionText.setText(inimigo.getPergaminhoInfo().get(1));
        }else{
            endGame = true;

            textoFinal.add("Tendo derroado todos os Magos Cadinais o Mago aprendeu diversos conhecimentos poderosos antigos.");
            textoFinal.add("Entretanto, após derrotar cada um dos grandes magos dessas terras, ele percebeu que todos reconheceram suas fraquezas e continuaram se aprimorando.");
            textoFinal.add("Portanto, o Mago se sentiu inspirado e continuou sua jornada partindo para outras terras em busca de mais conhecimento.");
            textoFinal.add("Sempre há algo novo para se aprender.");

            dialogoText.setText(textoFinal.get(0));

            Image imgfinal = new Image("images/Backgrounds/fundoFinal.png");
            combateBg.setImage(imgfinal);
            jogadorIMG.setOpacity(0);
        }

        animator.fadeOut(transictionBox);
    }

    @FXML
    void avancarDialogo(ActionEvent event) {
        if(endGame == false){
            if (dialogoAtual < inimigo.getDialogoVitoria().size()) {
                dialogoText.setText(
                        inimigo.getDialogoVitoria().get(dialogoAtual)
                );
                dialogoAtual++;
            }else{
                enemyLeft();
            }
        }else{
            if (textoFinalAtual < textoFinal.size()) {
                dialogoText.setText(
                        textoFinal.get(textoFinalAtual)
                );
                textoFinalAtual++;
            }
        }
    }

    @FXML
    void confirmarPergaminho(ActionEvent event) {
        //ao clicar no confirmar do pergamino > direcionar para tela de menu

        //se possuir todos os pergaminhos > tela final.
      if(Jogo.getInstancia().verificarPergaminhos()){
          avancarTelaFinal();
      }else{
          avancarTelaMapa();
      }

    }
    public void enemyLeft(){
        if(inimigo instanceof InimigoAgua){
            animator.enemyRotateSlideLeft(enemySprite, ()->onEnemyLeft());
        }else {
            animator.enemyFlipLeft(enemySprite,()->onEnemyLeft() );
        }
    }
    public void onEnemyLeft(){

        Jogo.getInstancia().atualizarPergaminho(Jogo.getInstancia().getNumeroInimgigoAtual());
        Image image = new Image(inimigo.getPergaminho());
        enemyItemImg.setImage(image);
        dialogoPane.setVisible(false);
        dialogoPane.setDisable(true);
        animator.appearPergaminho(enemyItemImg,fundoEscuro,pergaminhoPane);

    }
    public void avancarTelaFinal(){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Combate/Vitoria.fxml"));
            Parent root = loader.load();
            Stage stage = (Stage) combateBg.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private void avancarTelaMapa() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/Mapa.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) combateBg.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

package Controllers;

import Classes.Animator;
import Classes.Jogo;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Inimigo.InimigoAgua;
import Classes.Personagem.Inimigo.InimigoFogo;
import Classes.Personagem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class VitoriaController  {

    private Player jogador = Jogo.getInstancia().getJogador();
    private Inimigo inimigo = Jogo.getInstancia().getInimigoAtual();
    private Animator animator = new Animator();
    private int dialogoAtual = 0;

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

        animator.fadeOut(transictionBox);
    }

    @FXML
    void avancarDialogo(ActionEvent event) {
        if (dialogoAtual < inimigo.getDialogoVitoria().size()) {
            dialogoText.setText(
                    inimigo.getDialogoVitoria().get(dialogoAtual)
            );
            dialogoAtual++;
        }else{
            enemyLeft();
        }
    }

    @FXML
    void confirmarPergaminho(ActionEvent event) {
        //ao clicar no confirmar do pergamino > direcionar para tela de menu
    }
    public void enemyLeft(){
        if(inimigo instanceof InimigoAgua){
            animator.enemyRotateSlideLeft(enemySprite, ()->onEnemyLeft());
        }else {
            animator.enemyFlipLeft(enemySprite,()->onEnemyLeft() );
        }
    }
    public void onEnemyLeft(){
        Image image = new Image(inimigo.getPergaminho());
        enemyItemImg.setImage(image);
        dialogoPane.setVisible(false);
        dialogoPane.setDisable(true);
        animator.appearPergaminho(enemyItemImg,fundoEscuro,pergaminhoPane);

    }
}

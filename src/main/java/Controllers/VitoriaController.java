package Controllers;

import Classes.Animator;
import Classes.Jogo;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Player;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class VitoriaController  {

    private Player jogador = Jogo.getInstancia().getJogador();
    private Inimigo inimigo = Jogo.getInstancia().getInimigoAtual();
    private Animator animator = new Animator();

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
    private Text estrategiaText;

    @FXML
    private ImageView fundoEscuro;

    @FXML
    private ImageView jogadorIMG;

    @FXML
    private ImageView pergaminhoAberto;

    @FXML
    private Pane pergaminhoPane;

    @FXML
    private ImageView transictionBox;

    @FXML
    public void initialize() {

    }

    @FXML
    void avancarDialogo(ActionEvent event) {

    }

    @FXML
    void confirmarPergaminho(ActionEvent event) {

    }
}

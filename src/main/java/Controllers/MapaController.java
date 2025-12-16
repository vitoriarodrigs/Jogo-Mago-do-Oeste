package Controllers;

import Classes.Jogo;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;

public class MapaController {

    @FXML
    private Button fase1Btn;

    @FXML
    private Button fase2Btn;

    @FXML
    private Button fase3Btn;

    @FXML
    private ImageView fundo;

    @FXML
    private void irParaFase1(ActionEvent event) {
        Jogo.getInstancia().atualizarInimigoAtual(1);
        carregarTela();
    }

    @FXML
    private void irParaFase2(ActionEvent event) {
        Jogo.getInstancia().atualizarInimigoAtual(2);
        carregarTela();
    }

    @FXML
    private void irParaFase3(ActionEvent event) {
        Jogo.getInstancia().atualizarInimigoAtual(3);
        carregarTela();
    }

    private void carregarTela() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Combate/Combate.fxml"));
            Parent root = loader.load();

            Stage stage = (Stage) fundo.getScene().getWindow();
            stage.setScene(new Scene(root));
            stage.show();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

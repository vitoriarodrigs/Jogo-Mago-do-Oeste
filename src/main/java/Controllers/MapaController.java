package Controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MapaController {

    @FXML
    private Button fase1Btn;

    @FXML
    private Button fase2Btn;

    @FXML
    private Button fase3Btn;

    @FXML
    private void irParaFase1(ActionEvent event) {
        System.out.println("Indo para a Fase 1");
        carregarTela("fase1.fxml");
    }

    @FXML
    private void irParaFase2(ActionEvent event) {
        System.out.println("Indo para a Fase 2");
        carregarTela("fase2.fxml");
    }

    @FXML
    private void irParaFase3(ActionEvent event) {
        System.out.println("Indo para a Fase 3");
        carregarTela("fase3.fxml");
    }

    private void carregarTela(String fxml) {
        try {
            System.out.println("Carregando: " + fxml);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogoLoopsController {

    @FXML
    private Label dialogLabel;

    @FXML
    private ImageView personagemImage;

    @FXML
    private ImageView backgroundImage;

    private List<String> dialogs = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {

        // Falas do tutorial
        dialogs.add("Olha só um forasteiro.");
        dialogs.add("Vejo que parece bem cansado, deve ter caminhado muito até aqui.");
        dialogs.add("Faz tempo que não vejo ninguém novo adentrar nessas terras distantes.");
        dialogs.add("Muito bem, jovem mago, seu esforço será recompensado com o seguinte conhecimento:");
        dialogs.add("[LAÇOS DE REPETIÇÃO].");
        dialogs.add("Na programação eles permitem que você repita um trecho de código várias vezes.");
        dialogs.add("Esssa estrutura de código está disponível em várias linguagens de programação.");
        dialogs.add("Em Python, o mais comum é o laço 'for'.");
        dialogs.add("Exemplo:  for i in range(5):  print(i)");
        dialogs.add("Outro laço bastante comum é o [WHILE], ele permite que algo se repita indefinidamente até");
        dialogs.add("que uma condição seja atendida.");
        dialogs.add("Use esses laços para criar feitiços poderosos no combate.");
        dialogs.add("Vamos la para fora duelar, você verá na pratica o que estou falando.");

        showText(dialogs.get(0));
    }

    @FXML
    private void onNext() {
        index++;

        if (index < dialogs.size()) {
            showText(dialogs.get(index));
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Combate/Demonstracao.fxml"));
                Parent root = loader.load();

                Stage stage = (Stage) dialogLabel.getScene().getWindow();
                stage.setScene(new Scene(root));
                stage.show();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void showText(String text) {
        dialogLabel.setText("");
        Timeline timeline = new Timeline();

        for (int i = 0; i < text.length(); i++) {
            int finalI = i;
            timeline.getKeyFrames().add(
                    new KeyFrame(Duration.millis(35 * i),
                            e -> dialogLabel.setText(text.substring(0, finalI + 1)))
            );
        }

        timeline.play();
    }
}

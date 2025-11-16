package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class DialogController {

    @FXML
    private Label dialogLabel;

    private List<String> dialogs = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {

        dialogs.add("O Oeste nunca esteve tão ameaçado...");
        dialogs.add("Forças antigas despertaram no coração do deserto.");
        dialogs.add("Você, jovem mago, é a última esperança.");
        dialogs.add("Sua jornada começa agora...");

        showText(dialogs.get(0));
    }

    @FXML
    private void onNext() {
        index++;
        if (index < dialogs.size()) {
            showText(dialogs.get(index));
        } else {
            System.out.println("Fim do diálogo. Trocar para próxima tela aqui.");
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


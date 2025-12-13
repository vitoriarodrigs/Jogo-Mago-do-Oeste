package Controllers;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DialogController {

    @FXML
    private Label dialogLabel;

    private List<String> dialogs = new ArrayList<>();
    private int index = 0;

    @FXML
    public void initialize() {

        dialogs.add("Em um mundo com magos e magia, um jovem mago vaga sem rumo em busca de");
        dialogs.add("conhecimento.");
        dialogs.add("Ao vagar por essas terras distantes ele avista uma biblioteca.");
        dialogs.add("Adentrando a biblioteca ele encontra uma figura peculiar.");
        dialogs.add("Quem sabe o que o espera.");

        showText(dialogs.get(0));
    }

    @FXML
    private void onNext() {
        index++;

        if (index < dialogs.size()) {
            showText(dialogs.get(index));
        } else {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/DialogoLoops.fxml"));
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



package Classes;

import Classes.Feitico.NomeMagia;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.util.Duration;

public class Animator {

    public Animator(){

    }
    public void slideToDown(Node node, Pane pane) {

        // --- Movimento ---
        TranslateTransition translate = new TranslateTransition(Duration.millis(400), node);
        translate.setByY(20 - node.getLayoutY());

        // --- Desaparecer ---
        FadeTransition fade = new FadeTransition(Duration.millis(400), node);
        fade.setFromValue(1.0);  // opacidade total
        fade.setToValue(0.0);    // desaparece completamente

        // --- Rodar ao mesmo tempo ---
        ParallelTransition animation = new ParallelTransition(translate, fade);

        // Se quiser remover o node da tela depois que sumir:
        animation.setOnFinished(e ->
                pane.getChildren().clear());

        animation.play();
    }
    public void appear(Node node, boolean desaparecer) {

        // estado inicial
        node.setOpacity(0);
        node.setScaleX(1.5);
        node.setScaleY(1.5);

        // Fade IN
        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        // Scale para voltar ao tamanho normal
        ScaleTransition scale = new ScaleTransition(Duration.millis(300), node);
        scale.setFromX(1.5);
        scale.setFromY(1.5);
        scale.setToX(1.0);
        scale.setToY(1.0);

        // Animações simultâneas do início
        ParallelTransition appear = new ParallelTransition(fadeIn, scale);

        // Caso NÃO precise desaparecer, toca só o appear
        if (!desaparecer) {
            appear.play();
            return;
        }

        // Fade OUT opcional
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        // Aparece → desaparece
        SequentialTransition full = new SequentialTransition(appear, fadeOut);
        full.play();
    }
    public String enemyElementColor(NomeMagia nome){
       if(nome == NomeMagia.GELO){
           return "#6CEFF5";
       }else if(nome == NomeMagia.FOGO){
           return "#FF5733";
       }else{
           return "#FFFFFF";
       }
    }
    public void enemyAttackSlideLeft(Node node, Pane pane, Pane damage, NomeMagia elemento,Runnable onFinish){

        Label label = new Label();
        label.setFont(new Font("Arial Black", 40));
        label.setTextFill(Color.WHITE);

        node.setOpacity(0);
        pane.getChildren().add(node);
        damage.getChildren().add(label);

        TranslateTransition translate = new TranslateTransition(Duration.millis(1000), node);
        translate.setByX(node.getLayoutX() - 30);

        FadeTransition fadeIn = new FadeTransition(Duration.millis(300), node);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);

        ParallelTransition animation = new ParallelTransition(translate, fadeIn);

        SequentialTransition full = new SequentialTransition(animation, fadeOut);
        full.play();
        full.setOnFinished(e->{
            appear(label,true);
           // damage.getChildren().add(label);
            pane.getChildren().clear();
            onFinish.run();
        });

    }
}

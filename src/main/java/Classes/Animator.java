package Classes;

import Classes.Feitico.NomeMagia;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Inimigo.InimigoAgua;
import javafx.animation.*;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.util.Duration;

import java.util.Random;

public class Animator {

    public Animator(){

    }

    public void start(int timer, Label label, ImageView img, Runnable onFinish) {
        SequentialTransition sequencial = new SequentialTransition();

        for (int i = timer; i >= 0; i--) {
            int finalI = i;

            PauseTransition pause = new PauseTransition(Duration.seconds(1));
            pause.setOnFinished(e -> {
               if(finalI != 0){
                   label.setText(String.valueOf(finalI));
                   appear(label, true);
               }
            });

            sequencial.getChildren().add(pause);
        }

        // Define o que acontece ao terminar TUDO
        sequencial.setOnFinished(e -> {
            appear(img, true);

            PauseTransition pausaFinal = new PauseTransition(Duration.seconds(1));
            pausaFinal.setOnFinished(ev -> onFinish.run());
            pausaFinal.play();
        });

        sequencial.play();
    }
    public void startComMensagem(Text text, Runnable onFinish) {

        PauseTransition pause = new PauseTransition(Duration.millis(5000));
        pause.setOnFinished(e->{
            text.setText("");
            onFinish.run();
        });
        pause.play();
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
           return "#2DA8BA";
       }else if(nome == NomeMagia.FIRE){
           return "#9cf2c2";
       }else if(nome == NomeMagia.THUNDER){
           return "#a548d8";
       }else if(nome == NomeMagia.WATER){
           return "#0097be";
       }else{
           return "#FFFFFF";
       }
    }
    public void enemyElementAppear(ImageView node){
        // estado inicial

        node.setScaleX(1.5);
        node.setScaleY(1.5);
        PauseTransition pause = new PauseTransition(Duration.millis(100));


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

        // Fade OUT opcional
        FadeTransition fadeOut = new FadeTransition(Duration.millis(300), node);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);


        // Aparece → desaparece
        SequentialTransition full = new SequentialTransition(appear,pause,fadeOut);
        full.play();

    }
    public void enemyAttackSlideLeft(Node node, Pane pane, Pane damage, NomeMagia elemento,Label label, Runnable onFinish){

        label.setTextFill(Color.web(enemyElementColor(elemento)));

        node.setOpacity(0);
        pane.getChildren().add(node);
        damage.getChildren().add(label);
        node.setLayoutY(+20);
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
            slideToDown(label,damage);
            pane.getChildren().clear();
            onFinish.run();
        });

    }
    public void enemyAttackSlideToDiagonalLeft(Node node, Pane pane, Pane damage, NomeMagia elemento,Label label, Runnable onFinish){

        label.setTextFill(Color.web(enemyElementColor(elemento)));

        node.setOpacity(0);

        pane.getChildren().add(node);
        damage.getChildren().add(label);

        TranslateTransition translate = new TranslateTransition(Duration.millis(1000), node);
        translate.setByX(node.getLayoutX() -20);
        translate.setByY(node.getLayoutY() + 60);
       // esquerda


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
            slideToDown(label,damage);
            pane.getChildren().clear();
            onFinish.run();
        });

    }
    public void enemyAttackHits(Inimigo inimigo, Node node, Pane pane, Pane damage, NomeMagia elemento, Label label, int quant, int poder, Runnable onFinish){

        label.setTextFill(Color.web(enemyElementColor(elemento)));

        if(poder <10){
            label.setText("0"+String.valueOf(poder));
        }else{
            label.setText(String.valueOf(poder));
        }
        label.setOpacity(1);
        node.setOpacity(0);
        pane.getChildren().add(node);
        damage.getChildren().add(label);
        SequentialTransition full = new SequentialTransition();

        for(int i = 0; i < quant; i++){
            int danoAtual = poder * (i + 1);
            ImageView golpe = new ImageView(((ImageView) node).getImage());
            golpe.setFitWidth(((ImageView) node).getFitWidth());
            golpe.setFitHeight(((ImageView) node).getFitHeight());
            golpe.setOpacity(0);
            pane.getChildren().add(golpe);

            int dir = i % 4; // alterna entre 0,1,2,3

            switch (dir) {
                case 0: golpe.setLayoutY(-20);
                        golpe.setLayoutX(60);
                        break;
                case 1: golpe.setLayoutX(160);
                        golpe.setLayoutY(20);
                        break;
                case 2: golpe.setLayoutY(90);
                        golpe.setLayoutX(50);
                        break;
                case 3: golpe.setLayoutX(-40);
                       golpe.setLayoutY(20);
                        break;
            }
            // estado inicial
            node.setScaleX(1.5);
            node.setScaleY(1.5);

            // Fade IN
            FadeTransition fadeIn = new FadeTransition(Duration.millis(300), golpe);
            fadeIn.setFromValue(0.0);
            fadeIn.setToValue(1.0);

            // Scale para voltar ao tamanho normal
            ScaleTransition scale = new ScaleTransition(Duration.millis(300), golpe);
            scale.setFromX(1.5);
            scale.setFromY(1.5);
            scale.setToX(1.0);
            scale.setToY(1.0);

            // Animações simultâneas do início
            ParallelTransition appear = new ParallelTransition(fadeIn, scale);
            appear.setOnFinished(e->{
                if(danoAtual< 10 ){
                    label.setText("0"+String.valueOf(danoAtual));
                }else{
                    label.setText(String.valueOf(danoAtual));
                }
            });
            PauseTransition pause = new PauseTransition(Duration.millis(500));
            pause.setOnFinished(e->{
                onFinish.run();
                golpe.setOpacity(0);
            });
            full.getChildren().addAll(appear,pause);
        }

        full.play();
        full.setOnFinished(e->{
            slideToDown(label,damage);
            pane.getChildren().clear();
            inimigo.setAtacando(false);
        });

    }
    public void enemyAttackFadeIn(Node node, Pane pane, Pane damage,Label label, Runnable onFinish){


        node.setOpacity(0);

        pane.getChildren().add(node);

        TranslateTransition translate = new TranslateTransition(Duration.millis(3000), node);
        translate.setByX(node.getLayoutX() -15);
        // esquerda


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
            slideToDown(label,damage);
            pane.getChildren().clear();
            onFinish.run();
        });

    }
    public void enemyAttackDrawCard(int numeroRand, Pane pane,Text text, Runnable onFinish){
        Image image1 = new Image("images/Efeitos/Cartas/cartaVerso.png");
        ImageView carta = new ImageView(image1);
        carta.setFitHeight(150);
        carta.setFitWidth(120);

        String sorteado = "";

        switch (numeroRand){
            case 1:
                sorteado = "images/Efeitos/Cartas/cartaBuffHp.png";
                text.setText("Hp restaurado");
                break;
            case 2:
                sorteado = "images/Efeitos/Cartas/cartaDebuffHp.png";
                text.setText("Troca de hp");
                break;
            case 3:
                sorteado = "images/Efeitos/Cartas/cartaBuffLoja.png";
                text.setText("Magia adicionada a loja");
                break;
            case 4:
                sorteado = "images/Efeitos/Cartas/cartaDebuffLoja.png";
                text.setText("A loja foi roubada");
                break;
            case 5:
                sorteado = "images/Efeitos/Cartas/cartaBuffMana.png";
                text.setText("Mana restaurada");
                break;
            case 6:
                sorteado = "images/Efeitos/Cartas/cartaDebuffMana.png";
                text.setText("Mana drenada");
                break;
        }

        Image image2 = new Image(sorteado);

        pane.getChildren().add(carta);
        carta.setLayoutX(60);
        carta.setLayoutY(50);

        RotateTransition rotate = new RotateTransition();
        rotate.setNode(carta);
        rotate.setDuration(Duration.millis(1000));
        rotate.setCycleCount(3);
        rotate.setInterpolator(Interpolator.LINEAR);
        rotate.setByAngle(360);
        rotate.setAxis(Rotate.Y_AXIS);
        rotate.setOnFinished(e->{
            carta.setImage(image2);
            text.setOpacity(1);
        });
        PauseTransition pause = new PauseTransition(Duration.millis(1500));

        SequentialTransition full = new SequentialTransition(rotate, pause);

        full.setOnFinished(e->{
            text.setOpacity(0);
            pane.getChildren().clear();
            onFinish.run();
        });

        full.play();

    }
}

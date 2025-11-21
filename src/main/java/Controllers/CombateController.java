package Controllers;

import Classes.Animator;
import Classes.Feitico.*;
import Classes.Jogo;
import Classes.Personagem.Buff;
import Classes.Personagem.Inimigo.Inimigo;
import Classes.Personagem.Inimigo.ModoAtaque;
import Classes.Personagem.Inimigo.TipoAtaque;
import Classes.Personagem.Personagem;
import Classes.Personagem.Player;
import Classes.Personagem.TipoBuff;
import javafx.animation.*;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Random;

public class CombateController {
    private Player jogador = Jogo.getInstancia().getJogador();
    private Inimigo inimigo = Jogo.getInstancia().getInimigoAtual(); //arrumar depois
    private ArrayList<TrechoDeCodigo>trechos = new ArrayList<>();
    private Animator animator = new Animator();
    private int buyTimer = 3;
    private boolean pause = true;
    private int timerInicial = 3;



    @FXML
    private HBox caixaDeMagias;

    @FXML
    private Pane dotesPositionPane;

    @FXML
    private Rectangle enemyHp;

    @FXML
    private Rectangle enemyMana;

    @FXML
    private Rectangle enemyMaxHp;

    @FXML
    private Rectangle enemyMaxMana;

    @FXML
    private ImageView enemySprite;

    @FXML
    private Rectangle heroHp;

    @FXML
    private Rectangle heroMana;

    @FXML
    private Rectangle heroMaxHp;

    @FXML
    private Rectangle heroMaxMana;

    @FXML
    private ImageView jogadorIMG;

    @FXML
    private ImageView lacoAtual;

    @FXML
    private Label lacoAtualText;
    @FXML
    private Label lacoAtualText2;

    @FXML
    private Pane magiasDisponiveisPane;

    @FXML
    private Label heroManaQuant;

    @FXML
    private Label enemyManaQuant;

    @FXML
    private Label timerLabel;

    @FXML
    private Button ataqueButton;

    @FXML
    private ImageView ataqueButtonImg;

    @FXML
    private Pane enemyEfeictsBox;

    @FXML
    private Pane enemyDamageBox;


    @FXML
    private Pane heroDamageBox;

    @FXML
    private Pane heroEfeictsBox;

    @FXML
    private Text avisosText;

    @FXML
    private ImageView heroMagiaImg;

    @FXML
    private ImageView enemyMagiaImg;

    @FXML
    private VBox enemyDebuffBox;

    @FXML
    private Label textoCentro;

    @FXML
    private ImageView imagemCentro;

    @FXML
    private ImageView fundoEscuro;

    @FXML
    public void initialize() {
        // Atualiza as barras na inicialização
        atualizarBarraHp(jogador,heroMaxHp,heroHp, false);
        atualizarBarraMana(jogador,heroMaxMana,heroMana,heroManaQuant, false);
        atualizarBarraMana(inimigo,enemyMaxMana,enemyMana,enemyManaQuant,true);
        loadMagias();
        atualizarMagiasDisponiveis();
        avisosText.setText("compre um laço de repetição.");
        iniciar();

        // Timeline para restaurar 1 ponto de mana a cada 1 segundo
        Timeline manaRegen = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            verificarTerminar();
            if(pause == false){
                jogador.restaurarMana(1);
                atualizarBarraMana(jogador,heroMaxMana,heroMana,heroManaQuant,false);

                inimigo.restaurarMana(1);
                atualizarBarraMana(inimigo, enemyMaxMana, enemyMana, enemyManaQuant, true);
                atualizarDebuffInimigo();
            }
        }));
        manaRegen.setCycleCount(Timeline.INDEFINITE); // roda para sempre
        manaRegen.play();

        Timeline enemyColldown = new Timeline(new KeyFrame(Duration.seconds(inimigo.getColldownDeAtaque()), event -> {
            if(pause == false) {
                ataqueInimigo();
            }
        }));
        enemyColldown.setCycleCount(Timeline.INDEFINITE); // roda para sempre
        enemyColldown.play();

        Timeline timerToBuy = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            //10 por que precisa ter um ultimo lugar pra mostrar o contador e cada item são 2 itens.
            if(pause == false){
                if(trechos.size() < 6){
                    if(buyTimer <= 5 && buyTimer != 0){
                        buyTimer -= 1;
                    }else{
                        createTrecho();
                        atualizarMagiasDisponiveis();
                        buyTimer = 3;
                    }
                }else{
                    buyTimer = 3;
                    trechos.remove(0);
                    atualizarMagiasDisponiveis();
                }
                timerLabel.setText(String.valueOf(buyTimer));
            }
        }));
        timerToBuy.setCycleCount(Timeline.INDEFINITE); // roda para sempre
        timerToBuy.play();

        Image heroImg = new Image(getClass().getResource("/images/Personagens/personagemPrincipal.png").toExternalForm());
        jogadorIMG.setImage(heroImg);
        Image enemyImg = new Image(getClass().getResource(inimigo.getSprite()).toExternalForm());
        enemySprite.setImage(enemyImg);
        Image enemySpell = new Image(inimigo.getLancarMagiaSprite());
        enemyMagiaImg.setImage(enemySpell);
    }

    public void iniciar(){

        fundoEscuro.setOpacity(1);
        animator.start(timerInicial,textoCentro,imagemCentro,()->removerFundo());
    }
    public void removerFundo(){
        fundoEscuro.setOpacity(0);
        textoCentro.setOpacity(0);
        imagemCentro.setOpacity(0);
        pause = false;
    }
    public void verificarTerminar(){
        if(pause == true){
            return;
        }

        if(jogador.getHpAtual() <= 0){
            pause = true;
            fundoEscuro.setOpacity(1);
            Image img = new Image("/images/Hud/derrota.png");
            imagemCentro.setImage(img);
            animator.appear(imagemCentro,false);
        }else if(inimigo.getHpAtual() <=0){
            pause = true;
            fundoEscuro.setOpacity(1);
            Image img = new Image("/images/Hud/vitoria.png");
            imagemCentro.setImage(img);
            animator.appear(imagemCentro,false);
        }
    }

    public void loadMagias(){
        LacoFor laco1 = new LacoFor(3,4);
        LacoWhile laco2 = new LacoWhile(10,4);
        Magia magia = new Magia(TipoMagia.ATAQUE,NomeMagia.FOGO,2,6);
        Magia magia2 = new Magia(TipoMagia.ATAQUE,NomeMagia.WATER,2,6);
        Magia magia3 = new Magia(TipoMagia.ATAQUE,NomeMagia.THUNDER,2,6);


        trechos.add(laco1);
        trechos.add(laco2);
        trechos.add(magia);
        trechos.add(magia2);
        trechos.add(magia3);

    }
    public void createTrecho(){

        Random random = new Random();
        int sorteado = random.nextInt(3) + 1;

        int quantMagias = 0;
        int quantLacos = 0;
        for(TrechoDeCodigo trecho: trechos){
          if(trecho instanceof Magia){
              quantMagias ++;
          }else{
              quantLacos ++;
          }
        }
        if(quantMagias >= 3 && quantLacos == 0){
            createLaco();
            return;
        }
        if(quantLacos >= 2 && quantMagias == 0){
            createMagia();
            return;
        }

        switch (sorteado){
            case 1 : createLaco();
                     return;
            case 2,3 : createMagia();
                     return;
        }
        atualizarMagiasDisponiveis();

    }
    public void createLaco(){

        Random random = new Random();
        int sorteado = random.nextInt(2) + 1;
        int duracaoFor = random.nextInt(4) +2;
        int duracaoWhile = random.nextInt(5) +8;
        int custo = random.nextInt(7)+2;
        switch (sorteado){
            case 1 : LacoFor laco = new LacoFor(duracaoFor,custo);
                trechos.add(laco);
                return;
            case 2 : LacoWhile laco2 = new LacoWhile(duracaoWhile,custo);
                trechos.add(laco2);
                return;
        }
    }
    public void createMagia(){

        Random random = new Random();
        int sorteado = random.nextInt(3) + 1;

        switch (sorteado){
            case 1 : Magia fogo = new Magia(TipoMagia.ATAQUE,NomeMagia.FOGO,4,6);
                trechos.add(fogo);
                return;
            case 2 : Magia raio = new Magia(TipoMagia.ATAQUE,NomeMagia.THUNDER,3,4);
                trechos.add(raio);
                return;
            case 3 : Magia agua = new Magia(TipoMagia.ATAQUE,NomeMagia.WATER,2,3);
                trechos.add(agua);
                return;
        }
    }


    public void atualizarMagiasDisponiveis (){

        if(magiasDisponiveisPane.getChildren().size() > 0){
            magiasDisponiveisPane.getChildren().clear();
        }

        for (int i = 0; i < trechos.size(); i++) {
            TrechoDeCodigo trecho = trechos.get(i);

            final int index = i;

            Label label = new Label();
            label.setFont(new Font("Arial", 25)); // aumenta o tamanho da fonte
            label.setTextFill(Color.WHITE); // cor do texto
             // deixa em negrito
            label.setWrapText(true);
            if(trecho.getCusto() < 10){
                label.setText("0"+String.valueOf(trecho.getCusto()));
            }else{
                label.setText(String.valueOf(trecho.getCusto()));
            }

            String endereco = "";

            // Define o texto da Label conforme o tipo
            if (trecho instanceof LacoFor laco) {
               endereco = "/images/Magias/for.png";
            }else if( trecho instanceof LacoWhile){
                endereco = "/images/Magias/while.png";
            }else if (trecho instanceof Magia magia) {
                if(magia.getNome() == NomeMagia.FOGO){
                    endereco = "/images/Magias/fire.png";
                }else if(magia.getNome() == NomeMagia.THUNDER){
                    endereco = "/images/Magias/thunder.png";
                }else if(magia.getNome() == NomeMagia.WATER){
                    endereco = "/images/Magias/water.png";
                }
            } else {
                endereco = "desconhecido";
            }
            Image imagem = new Image(endereco);

            ImageView img = new ImageView(imagem);

            if(trechos.size() ==6 || trechos.size() ==5){
                if(i == 0){
                    label.setOpacity(0.5);
                    img.setOpacity(0.5);
                }
                if(i == 1){
                    label.setOpacity(0.8);
                    img.setOpacity(0.8);
                }
            }



            img.setOnMouseClicked(event -> {
                comprarFeitico(trecho,index,img,label);
            });
            label.setOnMouseClicked(event -> {
                comprarFeitico(trecho,index,img,label);
            });

            // Procura um círculo com o mesmo ID do índice
            for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                if (child instanceof javafx.scene.shape.Circle circle) {
                    if (circle.getId() != null && circle.getId().equals(String.valueOf(i))) {

                        label.setLayoutX(circle.getLayoutX() + 50);
                        label.setLayoutY(circle.getLayoutY() - 20);

                        img.setLayoutX(circle.getLayoutX() - 25);
                        img.setLayoutY(circle.getLayoutY()- 30);
                    }
                }
            }
            magiasDisponiveisPane.getChildren().add(img);
            magiasDisponiveisPane.getChildren().add(label);

            if(trechos.size() < 6 && i+1 == trechos.size()){
                Image imgTimer = new Image("/images/Hud/timerBox.png");

                ImageView imgVTimer = new ImageView(imgTimer);

                for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                    if (child instanceof javafx.scene.shape.Circle circle) {
                        if (circle.getId() != null && circle.getId().equals(String.valueOf(i+1))) {

                            timerLabel.setLayoutX(circle.getLayoutX() + 30);
                            timerLabel.setLayoutY(circle.getLayoutY() - 25);

                            imgVTimer.setLayoutX(circle.getLayoutX() - 25);
                            imgVTimer.setLayoutY(circle.getLayoutY()- 30);
                        }
                    }
                }
                magiasDisponiveisPane.getChildren().add(imgVTimer);
            }

        }
        if(trechos.size() == 0){
            Image imgTimer = new Image("/images/Hud/timerBox.png");

            ImageView imgVTimer = new ImageView(imgTimer);

            for (javafx.scene.Node child : dotesPositionPane.getChildren()) {
                if (child instanceof javafx.scene.shape.Circle circle) {
                    if (circle.getId() != null && circle.getId().equals(String.valueOf(0))) {

                        timerLabel.setLayoutX(circle.getLayoutX() + 30);
                        timerLabel.setLayoutY(circle.getLayoutY() - 25);

                        imgVTimer.setLayoutX(circle.getLayoutX() - 25);
                        imgVTimer.setLayoutY(circle.getLayoutY()- 30);
                    }
                }
            }
            magiasDisponiveisPane.getChildren().add(imgVTimer);
        }
    }
    public void comprarFeitico(TrechoDeCodigo trecho, int index, ImageView img,Label label) {
        if(pause){
            return;
        }
        if(jogador.getManaAtual() < trecho.getCusto()){
            avisosText.setText("Sem mana suficiente para comprar a magia.");
            return;
        }
        if(trecho instanceof Magia){
            if(jogador.getLaco() != null){

                if(caixaDeMagias.getChildren().size() == 3){
                    avisosText.setText("Quantidade máxima de magias atingida.");
                    return;
                }

            }else{
                avisosText.setText("Compre primeiro um laço de repetição.");
                return;
            }
        }
        if(trecho instanceof LacoDeRepeticao){
            if(jogador.getLaco() != null){
                avisosText.setText("Você já possui um laço de repetição.");
                return;
            }
        }

        trechos.remove(index);
        magiasDisponiveisPane.getChildren().remove(img);
        magiasDisponiveisPane.getChildren().remove(label);
        jogador.comprarFeitico(trecho);
        avisosText.setText("");
        atualizarMagiasDisponiveis();
        atualizarBarraMana(jogador,heroMaxMana,heroMana,heroManaQuant,false);
        atualizarMagiaJogador();
    }

    public void atualizarMagiaJogador(){
        LacoDeRepeticao laco = jogador.getLaco();

        if(caixaDeMagias.getChildren().size()>0){
            caixaDeMagias.getChildren().clear();
        }
        if(laco == null){
            return;
        }

        lacoAtual.setVisible(true);
        lacoAtualText.setVisible(true);
        lacoAtualText2.setVisible(true);

        if(laco instanceof LacoFor){
            Image img = new Image("/images/Hud/lacoFor.png");
            lacoAtual.setImage(img);
            lacoAtualText.setText("for i in range("+laco.getDuracao()+")");
            lacoAtualText2.setText("");

        }else if(laco instanceof LacoWhile){
            Image img = new Image("/images/Hud/lacoWhile.png");
            lacoAtual.setImage(img);
            lacoAtualText.setText("while i < "+laco.getDuracao());
            lacoAtualText2.setText("i+=1");
        }

        if(laco.getMagias().size() > 0){

            ataqueButton.setDisable(false);
            ataqueButtonImg.setVisible(true);

            for(Magia magia : (laco).getMagias()){
                String endereco = "";

                if(magia.getNome() == NomeMagia.FOGO){
                    endereco = "/images/Magias/NoCost/fireNoCost.png";
                }else if(magia.getNome() == NomeMagia.THUNDER){
                    endereco = "/images/Magias/NoCost/thunderNoCost.png";
                }else if(magia.getNome() == NomeMagia.WATER){
                    endereco = "/images/Magias/NoCost/waterNoCost.png";
                }
                Image magiaImg = new Image(endereco);
                ImageView magiaIV = new ImageView(magiaImg);
                caixaDeMagias.getChildren().add(magiaIV);
            }
        }
    }

    private void atualizarBarraHp(Personagem personagem, Rectangle maxHp, Rectangle hp, boolean inimigo) {
        double proporcao = (double) personagem.getHpAtual() / personagem.getHpMaximo();
        double novaLargura = maxHp.getWidth() * proporcao;

        hp.setWidth(novaLargura);

        if (inimigo) {
            double direita = maxHp.getX() + maxHp.getWidth();
            hp.setX(direita - novaLargura);
        } else {
            hp.setX(maxHp.getX());
        }
    }
    private void atualizarBarraMana(Personagem personagem, Rectangle maxMana, Rectangle mana, Label manaQuant, boolean inimigo) {
        if(personagem.getManaAtual() <0){
            return;
        }
        double proporcao = (double) personagem.getManaAtual() / personagem.getManaMaxima();
        double novaLargura = maxMana.getWidth() * proporcao;

        mana.setWidth(novaLargura);

        if (inimigo) {
            double direita = maxMana.getX() + maxMana.getWidth();
            mana.setX(direita - novaLargura);
        } else {
            mana.setX(maxMana.getX());
        }

        int qnt = personagem.getManaAtual();
        manaQuant.setText(qnt < 10 ? "0" + qnt : String.valueOf(qnt));
    }
    private void atualizarDebuffInimigo(){

        if(enemyDebuffBox.getChildren().size()>0){
            enemyDebuffBox.getChildren().clear();
        }

        if(inimigo.getBuffs().size() == 0 ){
            return;
        }
        for(Buff buff : inimigo.getBuffs()){
            String endereco = "";
            String poder = "";
            String cor = "";
            if(buff.getTipo() == TipoBuff.FIRE_DEBUFF){
                inimigo.tomarDano(buff.getPoder());
                endereco = "/images/Efeitos/Debuff/debuffFire.png";
                poder = String.valueOf("+"+buff.getPoder());
                cor = "#BF3737";
                atualizarBarraHp(inimigo, enemyMaxHp, enemyHp, true);
            }else if(buff.getTipo() == TipoBuff.WATER_DEBUFF){
                endereco = "/images/Efeitos/Debuff/debuffWater.png";
                poder = String.valueOf("+"+buff.getPoder());
                cor = "#268FA6";
            }else if(buff.getTipo() == TipoBuff.THUNDER_DEBUFF){
                endereco = "/images/Efeitos/Debuff/debuffThunder.png";
                poder = String.valueOf("-"+buff.getPoder());
                cor = "#4E3D87";


            }
            Image img = new Image(endereco);
            ImageView imgV = new ImageView(img);

            Text label = new Text();
            label.setFont(new Font("Arial Black", 25));
            label.setFill(Color.WHITE);
            label.setStroke(Color.web(cor));
            label.setStrokeWidth(1.5);
            label.setText(poder);
            label.setLayoutX(25);
            label.setLayoutY(45);
            Pane pane = new Pane(imgV,label);

            enemyDebuffBox.getChildren().add(pane);
            animator.appear(pane,false);
        }
        inimigo.atualizarTempoBuffs();
    }

    private void ataqueInimigo(){
        inimigo.definirAtaque();

        String urlAtaque;

        if(inimigo.getAtaqueEscolhido() == TipoAtaque.FORTE){
            urlAtaque = inimigo.getMagiaForteSprite();
        }else{
            urlAtaque = inimigo.getMagiaFracaSprite();
        }

        Image img = new Image(urlAtaque);
        ImageView imgView = new ImageView(img);

        Label label = new Label();
        label.setFont(new Font("Arial Black", 40));
        if(inimigo.getDanoDoAtaque() < 10){
            label.setText("0"+String.valueOf(inimigo.getDanoDoAtaque()));
        }else{
            label.setText(String.valueOf(inimigo.getDanoDoAtaque()));
        }
        label.setOpacity(0);

        if(inimigo.podeAtacar(inimigo.getPrecoDoAtaque())){
            inimigo.gastarManaAtual(inimigo.getPrecoDoAtaque());


            animator.enemyElementAppear(enemyMagiaImg);

                if(inimigo.getModoDeAtaque() == ModoAtaque.HORIZONTAL_ESQUERDA){
                    animator.enemyAttackSlideLeft
                            (imgView,heroEfeictsBox, heroDamageBox, inimigo.getElemento(),label,() -> aplicarDanoInimigo());
                }else if(inimigo.getModoDeAtaque() == ModoAtaque.DIAGONAL_ESQUERDA){
                    animator.enemyAttackSlideToDiagonalLeft
                            (imgView,heroEfeictsBox, heroDamageBox, inimigo.getElemento(),label,() -> aplicarDanoInimigo());
                }

        }
    }
    private void aplicarDanoInimigo() {
        inimigo.atacar(jogador);
        atualizarBarraHp(jogador, heroMaxHp, heroHp, false);
    }

    @FXML
    void lancarFeitico(ActionEvent event) {
        LacoDeRepeticao laco = jogador.getLaco();
        enemyEfeictsBox.getChildren().clear();
        SequentialTransition sequencial = new SequentialTransition();
        double posX = 50;
        double posY = 50;
        int totalDamage = 0;

        Label label = new Label();
        label.setFont(new Font("Arial Black", 40));
        label.setTextFill(Color.WHITE);
        animator.appear(label,false);
        enemyDamageBox.getChildren().add(label);

        heroMagiaImg.setVisible(true);
        animator.appear(heroMagiaImg,true);
        PauseTransition lancarPause = new PauseTransition(Duration.millis(400));
        lancarPause.setOnFinished(e->{
            heroMagiaImg.setVisible(false);
        });
        sequencial.getChildren().add(lancarPause);

        if (laco instanceof LacoFor) {
            for (int i = 0; i < laco.getDuracao(); i++) {
                for (Magia magia : ((LacoFor) laco).getMagias()) {
                    double pposX = posX;
                    double pposY =posY;
                    totalDamage += magia.getPoder();
                    int curentDamage = totalDamage;

                    if (magia.getTipo() == TipoMagia.ATAQUE) {
                        PauseTransition ataquePause = new PauseTransition(Duration.millis(100));
                        ataquePause.setOnFinished(e -> {
                            criarEfeito(magia, "FOR", pposX, pposY, label);

                            jogador.atacar(inimigo, magia.getPoder());

                            if(curentDamage < 10){
                                label.setText("0"+curentDamage);
                            }else{
                                label.setText(String.valueOf(curentDamage));
                            }

                            atualizarBarraHp(inimigo, enemyMaxHp, enemyHp, true);
                        });
                        sequencial.getChildren().add(ataquePause);
                    }
                    posX +=50;
                    posY +=50;
                }
                PauseTransition ataquePause = new PauseTransition(Duration.millis(300));
                ataquePause.setOnFinished(e -> {
                    enemyEfeictsBox.getChildren().clear();
                });
                sequencial.getChildren().add(ataquePause);
                posX = 50;
                posY = 50;
            }
            sequencial.play();
            sequencial.setOnFinished(e->{
                enemyEfeictsBox.getChildren().clear();
                animator.slideToDown(label,enemyDamageBox);
               avisosText.setText("Compre um laço de repetição.");
            });
        }else if( laco instanceof LacoWhile){
            int fireQuant = 0;
            int waterQuant = 0;
            int thunderQuant = 0;
            for (Magia magia : laco.getMagias()) {
                if(magia.getNome() == NomeMagia.FOGO){
                    fireQuant += 2;
                }else if(magia.getNome() == NomeMagia.WATER){
                    waterQuant++;
                }else if(magia.getNome() == NomeMagia.THUNDER){
                    thunderQuant++;
                }
            }
            String mensagem = "";
            if(fireQuant > 0){
               Buff buff1 = new Buff(TipoBuff.FIRE_DEBUFF,laco.getDuracao(),fireQuant);
               mensagem = "Debuff [Fire] dá dano por segundo no oponente. ";
               inimigo.addBuff(buff1);
            }
            if(waterQuant > 0){
                Buff buff2 = new Buff(TipoBuff.WATER_DEBUFF,laco.getDuracao(),waterQuant);
                inimigo.addBuff(buff2);
                mensagem += "Debuff [Water] aumenta o tempo de espera do oponente. ";
            }
            if(thunderQuant > 0){
                Buff buff3 = new Buff(TipoBuff.THUNDER_DEBUFF,laco.getDuracao(),thunderQuant);
                inimigo.addBuff(buff3);
                mensagem += "Debuff [Thunder] diminui o dano do oponente.";
            }
            avisosText.setText(mensagem);
        }
        jogador.removerLaco();
        ataqueButton.setDisable(true);
        ataqueButtonImg.setVisible(false);
        lacoAtual.setVisible(false);
        lacoAtualText.setVisible(false);
        lacoAtualText2.setVisible(false);
        atualizarMagiaJogador();
    }
    public void criarEfeito(Magia magia, String laco, double posX, double posY, Label label){
        if(magia.getNome() == NomeMagia.FOGO){
            label.setTextFill(Color.web("#FF5733"));
            if(laco.equals("FOR")){
                Image img = new Image(getClass().getResource("/images/Efeitos/magiaFogoFor.png").toExternalForm());
               ImageView imgV = new ImageView(img);

               enemyEfeictsBox.getChildren().add(imgV);
                imgV.setLayoutX(imgV.getLayoutX() + posX);
                imgV.setLayoutY(imgV.getLayoutY() + posY);
            }
        }else if(magia.getNome() == NomeMagia.THUNDER) {
            label.setTextFill(Color.web("#a548d8"));
            if (laco.equals("FOR")) {
                Image img = new Image(getClass().getResource("/images/Efeitos/magiaThunderFor.png").toExternalForm());
                ImageView imgV = new ImageView(img);

                enemyEfeictsBox.getChildren().add(imgV);
                imgV.setLayoutX(imgV.getLayoutX() + posX);
                imgV.setLayoutY(imgV.getLayoutY() + posY);
            }
        }else if(magia.getNome() == NomeMagia.WATER) {
            label.setTextFill(Color.web("#1188ea"));
            if (laco.equals("FOR")) {
                Image img = new Image(getClass().getResource("/images/Efeitos/magiaWaterFor.png").toExternalForm());
                ImageView imgV = new ImageView(img);

                enemyEfeictsBox.getChildren().add(imgV);
                imgV.setLayoutX(imgV.getLayoutX() + posX);
                imgV.setLayoutY(imgV.getLayoutY() + posY);
            }
        }
    }

}

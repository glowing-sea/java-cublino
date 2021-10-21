package comp1140.ass2.gui;

import comp1140.ass2.Cublino;
import comp1140.ass2.core.Dice;
import comp1140.ass2.core.State;
import comp1140.ass2.core.Step;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.util.ArrayList;

// (By Rajin)
// Main class that executes the game logic through GUIs made with JavaFX
public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int TILE_SIZE = 52;
    private static final int GAMEPANE_SIZE = 450;
    private static final String URI_BASE = "assets/";

    private State prevGameState = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
    private State gameState = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

    private final Group root = new Group();
    private final static Pane gamePane = new Pane();
    private final static Pane orientationPanel = new Pane();
    private final static Pane gameSettingsOrientationPanel = new Pane();
    private final static Group gamePieces = new Group();
    private final static Group legalStepsGroup = new Group();
    private final Group controls = new Group();
    private final Group header = new Group();

    private final ArrayList<Step> availableLegalSteps = new ArrayList<>();
    private final ArrayList<Dice> dicePieces = new ArrayList<>();
    private final StringBuilder onGoingMove = new StringBuilder();
    private Dice onGoingDice;
    private Dice currentlySelectedDice;

    // AI Choice Mapping
    // 0 is Human v Human
    // 1 is Human v AI1
    // 2 is Human v AI2
    // 3 is AI1 v AI2
    // 4 is AI2 v AI2
    // 5 is AI1 v Human
    // 6 is AI2 v Human
    // 7 is AI1 v AI1
    // 8 is AI2 v AI2
    private int AIchoice = 0;


    private final OrientationTiles orientationTiles = new OrientationTiles();
    private final Label playerTurnLabel = new Label("Player Turn: "+ (gameState.getPlayerTurn() ? "White" : "Black"));
    private final Label validityLabel = new Label("Valid Move: N/A");
    ChoiceBox<String> variantChoice = new ChoiceBox<>();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cublino");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        scene.setFill(Color.web("#E9C8A5"));

        makeHeader();
        makeControls();
        makeBoard();
        updateDices();
        makeOrientationPanel();
        makeGameSettingsPanel();

        // Instruction Button
        Button helpButton = new Button("Show Instructions");
        helpButton.setStyle("""
                -fx-font: 13px Tahoma;
                -fx-background-radius: 0.5em;
                """);
        helpButton.setLayoutX(655);
        helpButton.setLayoutY(40);
        helpButton.setOnMouseClicked(mouseEvent -> showInstructions());


        root.getChildren().addAll(gamePane, controls, header, orientationPanel, gameSettingsOrientationPanel, helpButton);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // (By Rajin)
    // Generates an instruction page depending on the current game variant
    private void showInstructions() {
        final Stage gameStatusStage = new Stage();
        gameStatusStage.initModality(Modality.APPLICATION_MODAL);
        gameStatusStage.setTitle("Game Instructions");
        gameStatusStage.setResizable(false);

        VBox dialogVbox = new VBox();
        dialogVbox.setAlignment(Pos.CENTER);
        dialogVbox.setPadding(new Insets(30));
        dialogVbox.setSpacing(10);
        dialogVbox.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));

        ImageView purRulesImage = new ImageView(new Image(URI_BASE+"pur_rules.png"));
        purRulesImage.setFitHeight(450);
        purRulesImage.setFitWidth(450);
        ImageView contraRulesImage = new ImageView(new Image(URI_BASE+"contra_rules.png"));
        contraRulesImage.setFitHeight(450);
        contraRulesImage.setFitWidth(450);
        ImageView additionalInfoImage = new ImageView(new Image(URI_BASE+"additional_notes.png"));
        additionalInfoImage.setFitWidth(400);
        additionalInfoImage.setFitHeight(150);

        Button closeButton = new Button("Close");
        closeButton.setOnMouseClicked(mouseEvent -> gameStatusStage.close());

        dialogVbox.getChildren().addAll((gameState.isPur() ? purRulesImage : contraRulesImage), additionalInfoImage, closeButton);

        Scene dialogScene = new Scene(dialogVbox, 700, 700);
        gameStatusStage.setScene(dialogScene);
        gameStatusStage.show();
    }

    // (By Rajin)
    // Creates a game board with the tile GUI
    public void makeBoard() {
        gamePane.getChildren().clear();
        gamePane.setMinSize(GAMEPANE_SIZE,GAMEPANE_SIZE);
        gamePane.setLayoutX(100);
        gamePane.setLayoutY(150);
        gamePane.setBackground(new Background(new BackgroundImage(new Image(URI_BASE+"board.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        // Make a 7x7 tile board
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                gamePane.getChildren().add(new TileGUI(i,j));
            }
        }
    }

    // (By Rajin)
    // Populates a game board with dice GUIs at the default locations
    public void updateDices() {
        gamePieces.getChildren().clear();
        gamePane.getChildren().remove(gamePieces);
        orientationTiles.clearOrientation();

        dicePieces.clear();

        // Apply AI Move (for games where AI vs Human)
        if (AIchoice == 1 && !gameState.getPlayerTurn()) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 1).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 1).toString()));
        }

        if (AIchoice == 2 && !gameState.getPlayerTurn()) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 2).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 2).toString()));
        }

        if (AIchoice == 5 && gameState.getPlayerTurn()) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 1).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 1).toString()));
        }

        if (AIchoice == 6 && gameState.getPlayerTurn()) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 2).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 2).toString()));
        }

        // Apply AI Moves (for games where AI vs AI)
        if (AIchoice == 3) {
            if (gameState.getPlayerTurn()) {
                gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 1).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 1).toString()));
            } else {
                gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 2).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 2).toString()));
            }
        }

        if (AIchoice == 4) {
            if (gameState.getPlayerTurn()) {
                gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 2).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 2).toString()));
            } else {
                gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 1).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 1).toString()));
            }
        }

        if (AIchoice == 7) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 1).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 1).toString()));
        }

        if (AIchoice == 8) {
            gameState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), Cublino.bestMove(gameState, 2).toString()) : Cublino.applyMoveContra(gameState.toString(), Cublino.bestMove(gameState, 2).toString()));
        }

        // Re render dices
        for (Dice dice:gameState.getDices()) {
            gamePieces.getChildren().add(new PieceGUI(dice));
            dicePieces.add(dice);
        }

        gamePane.getChildren().add(gamePieces);

        // Check if game state is an end state and show pop up
        if (gameState.isGameOver() == 1 || gameState.isGameOver() == 2 || gameState.isGameOver() == 3) { // If the game is over
            final Stage gameStatusStage = new Stage();
            gameStatusStage.initModality(Modality.APPLICATION_MODAL);
            gameStatusStage.setTitle("Game Over");
            gameStatusStage.setResizable(false);

            VBox dialogVbox = new VBox();
            dialogVbox.setAlignment(Pos.CENTER);
            dialogVbox.setPadding(new Insets(30));
            dialogVbox.setSpacing(20);

            Label gameStatusLabel = new Label(gameState.isGameOver() == 1 ? "Player 1 Wins" : (gameState.isGameOver() == 2 ? "Player 2 Wins" : "It's a Draw") );
            gameStatusLabel.setFont(new Font(15));
            gameStatusLabel.relocate(100, 100);
            gameStatusLabel.setStyle("""
                -fx-font: 30px Tahoma;
                    -fx-stroke: black;
                    -fx-stroke-width: 3;
                """);

            Button restartGameButton = new Button("Restart Game");
            restartGameButton.setStyle("""
                -fx-font: 13px Tahoma;
                -fx-background-radius: 0.5em;
                """);

            restartGameButton.setOnMouseClicked(mouseEvent -> {
                variantChoice.setValue(gameState.isPur() ? "Pur" : "Contra");
                gameState = new State(gameState.isPur() ? "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7" : "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

                updateDices();
                gameStatusStage.close();
            });

            dialogVbox.getChildren().addAll(gameStatusLabel, restartGameButton);

            Scene dialogScene = new Scene(dialogVbox, 300, 200);
            gameStatusStage.setScene(dialogScene);
            gameStatusStage.show();
        }

        updateLabelUI();
    }

    // (By Rajin)
    // Updates the label UI
    public void updateLabelUI() {
        playerTurnLabel.setText("Player Turn: "+ (gameState.getPlayerTurn() ? "White" : "Black"));
    }

    private void makeOrientationPanel() {
        orientationPanel.setBackground(new Background(new BackgroundImage(new Image(URI_BASE+"orientation.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        orientationPanel.setPrefSize(200, 200);
        orientationPanel.setLayoutX(575);
        orientationPanel.setLayoutY(400);

        // Add default placeholders
        orientationTiles.show();
    }

    // (By Rajin)
    // Generates the game settings panel
    private void makeGameSettingsPanel() {
        gameSettingsOrientationPanel.setBackground(new Background(new BackgroundImage(new Image(URI_BASE+"settings.png"), BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.CENTER, BackgroundSize.DEFAULT)));
        gameSettingsOrientationPanel.setPrefSize(200, 230);
        gameSettingsOrientationPanel.setLayoutX(575);
        gameSettingsOrientationPanel.setLayoutY(150);

        Label gameSettingsLabel = new Label("Game Settings");
        gameSettingsLabel.setStyle("""
                -fx-font: 20px Tahoma;
                    -fx-stroke: black;
                    -fx-stroke-width: 1;
                """);


        VBox player1ChoicesVbox = new VBox();
        Label player1Label = new Label("Player 1 (White) : ");

        ChoiceBox<String> player1SettingChoice = new ChoiceBox<>();
        player1SettingChoice.setStyle("""
                -fx-font: 12px Tahoma;
                -fx-background-radius: 0.5em;
                """);
        player1SettingChoice.getItems().addAll("Human", "AI 1 (Greedy)", "AI 2 (Minimax w/ AB)");
        player1SettingChoice.setValue("Human");
        player1ChoicesVbox.getChildren().addAll(player1Label, player1SettingChoice);

        VBox player2ChoicesVbox = new VBox();
        Label player2Label = new Label("Player 2 (Black) : ");

        ChoiceBox<String> player2SettingChoice = new ChoiceBox<>();
        player2SettingChoice.setStyle("""
                -fx-font: 12px Tahoma;
                -fx-background-radius: 0.5em;
                """);
        player2SettingChoice.getItems().addAll("Human", "AI 1 (Greedy)", "AI 2 (Minimax w/ AB)");
        player2SettingChoice.setValue("Human");
        player2ChoicesVbox.getChildren().addAll(player2Label, player2SettingChoice);

        Button restartGameButton = new Button("Restart Game");
        restartGameButton.setStyle("""
                -fx-font: 13px Tahoma;
                -fx-background-radius: 0.5em;
                """);

        // Restart the game with new settings
        restartGameButton.setOnMouseClicked(mouseEvent -> {
            legalStepsGroup.getChildren().clear();
            gamePane.getChildren().remove(legalStepsGroup);
            gamePane.getChildren().add(legalStepsGroup);

            gameState = new State(gameState.isPur() ? "PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7" : "CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

            if (player1SettingChoice.getValue().equals("Human") && player2SettingChoice.getValue().equals("Human")) {
                AIchoice = 0;
            } else if (player1SettingChoice.getValue().equals("Human") && player2SettingChoice.getValue().equals("AI 1 (Greedy)")) {
                AIchoice = 1;
            } else if (player1SettingChoice.getValue().equals("Human") && player2SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)")) {
                AIchoice = 2;
            } else if (player1SettingChoice.getValue().equals("AI 1 (Greedy)") && player2SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)")) {
                AIchoice = 3;
            } else if (player1SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)") && player2SettingChoice.getValue().equals("AI 1 (Greedy)")) {
                AIchoice = 4;
            } else if (player1SettingChoice.getValue().equals("AI 1 (Greedy)") && player2SettingChoice.getValue().equals("Human")) {
                AIchoice = 5;
            } else if (player1SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)") && player2SettingChoice.getValue().equals("Human")) {
                AIchoice = 6;
            } else if (player1SettingChoice.getValue().equals("AI 1 (Greedy)") && player2SettingChoice.getValue().equals("AI 1 (Greedy)")) {
                AIchoice = 7;
            } else if (player1SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)") && player2SettingChoice.getValue().equals("AI 2 (Minimax w/ AB)")) {
                AIchoice = 8;
            }

            updateDices();
        });

        VBox settingsComponents = new VBox();
        settingsComponents.setLayoutX(20);
        settingsComponents.setLayoutY(30);
        settingsComponents.setSpacing(10);
        settingsComponents.getChildren().addAll(gameSettingsLabel, player1ChoicesVbox, player2ChoicesVbox, restartGameButton);

        gameSettingsOrientationPanel.getChildren().add(settingsComponents);
    }

    // (By Rajin)
    // Generate control components
    private void makeControls() {
        Button endTurnButton = new Button("End Turn");
        endTurnButton.setStyle("""
                -fx-font: 13px Tahoma;
                -fx-background-radius: 0.5em;
                """);
        endTurnButton.setOnAction(actionEvent -> {

            if (onGoingMove.length() > 0 && onGoingDice != null) {
                if (!Cublino.isValidMovePur(prevGameState.toString(), onGoingMove.toString())) {
                    gameState = prevGameState;
                    gameState.setTurn(!gameState.getPlayerTurn());
                    validityLabel.setText("Valid Move: Invalid");
                } else {
                    validityLabel.setText("Valid Move: Valid");
                }
            }

            onGoingMove.replace(0, onGoingMove.length(), "");
            onGoingDice = null;


            if (AIchoice == 3 || AIchoice == 4 || AIchoice == 7 || AIchoice == 8) {
                if (gameState.isGameOver() != 1 || gameState.isGameOver() != 2 || gameState.isGameOver() != 3) {
                    updateDices();
                }
            } else {
                gameState.setTurn(!gameState.getPlayerTurn());
                updateDices();
            }

            legalStepsGroup.getChildren().clear();

        });

        VBox vb = new VBox();

        vb.getChildren().addAll(validityLabel, endTurnButton);
        vb.setSpacing(5);
        vb.setLayoutX(100);
        vb.setLayoutY(BOARD_HEIGHT - 80);

        controls.getChildren().addAll(vb);
    }

    // (By Rajin)
    // Render the header
    public void makeHeader() {
        Label title = new Label("Cublino");
        title.setStyle("""
                -fx-font: 30px Tahoma;
                    -fx-stroke: black;
                    -fx-stroke-width: 3;
                """);

        variantChoice.setStyle("""
                -fx-font: 13px Tahoma;
                -fx-background-radius: 0.5em;
                """);
        variantChoice.getItems().add("Pur");
        variantChoice.getItems().add("Contra");
        variantChoice.setValue("Pur");

        variantChoice.getSelectionModel().selectedItemProperty().addListener((observableValue, o, t1) -> {
            if (t1.equals("Contra")) {
                gameState = new State("CWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7"); // resets to a default Pur game
                legalStepsGroup.getChildren().clear();
                gamePane.getChildren().remove(legalStepsGroup);
                gamePane.getChildren().add(legalStepsGroup);
                updateDices();
            } else if (t1.equals("Pur")) {
                gameState = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7"); // resets to a default Contra game
                legalStepsGroup.getChildren().clear();
                gamePane.getChildren().remove(legalStepsGroup);
                gamePane.getChildren().add(legalStepsGroup);
                updateDices();
            }
        });

        playerTurnLabel.setStyle("""
                -fx-font: 15px Tahoma;
                    -fx-stroke: black;
                    -fx-stroke-width: 5;
                """);


        VBox headerVBox = new VBox();
        headerVBox.getChildren().addAll(title, variantChoice, playerTurnLabel);
        headerVBox.setLayoutX(100);
        headerVBox.setLayoutY(30);
        headerVBox.setSpacing(5);

        header.getChildren().add(headerVBox);
    }

    // (By Rajin)
    // Generate indicators for the possible legal moves
    public void generateLegalIndicators(Dice dice) {
        // Get legal steps based on whether the game mode is pur or contra
        ArrayList<Step> legalSteps = gameState.isPur() ? gameState.legalStepsPur(dice) : gameState.legalStepsContra(dice);

        legalStepsGroup.getChildren().clear();
        availableLegalSteps.clear();

        // Render indicator at the location where the move ends
        if (onGoingMove.length() > 0 && onGoingDice != null) { // if there is an ongoing move
            if (dice.compareTo(onGoingDice) == 0) {
                legalSteps.removeIf(Step::isTip);
                legalSteps.removeIf(step -> !step.getStartPosition().equals(onGoingDice.getPosition()));
                legalSteps.removeIf(step -> onGoingMove.substring(onGoingMove.length()-2, onGoingMove.length()).contains(step.getEndPosition().toString()));

                for (Node node:gamePane.getChildren()) {
                    for (Step legalStep:legalSteps) {
                        if (node instanceof TileGUI) {
                            TileGUI tile = (TileGUI) node;
                            if (tile.xIndex == legalStep.getEndPosition().getX() && tile.yIndex == legalStep.getEndPosition().getY()) {
                                LegalIndicatorGUI legalIndicator = new LegalIndicatorGUI(tile.xIndex, tile.yIndex, legalStep);
                                legalStepsGroup.getChildren().add(legalIndicator);
                                availableLegalSteps.add(legalStep);
                            }
                        }
                    }
                }
            }
        } else {
            for (Node node:gamePane.getChildren()) {
                for (Step legalStep:legalSteps) {
                    if (node instanceof TileGUI) {
                        TileGUI tile = (TileGUI) node;
                        if (tile.xIndex == legalStep.getEndPosition().getX() && tile.yIndex == legalStep.getEndPosition().getY()) {
                            LegalIndicatorGUI legalIndicator = new LegalIndicatorGUI(tile.xIndex, tile.yIndex, legalStep);
                            legalStepsGroup.getChildren().add(legalIndicator);
                            availableLegalSteps.add(legalStep);
                        }
                    }
                }
            }
        }

        gamePane.getChildren().remove(legalStepsGroup);
        gamePane.getChildren().add(legalStepsGroup);
    }


    // (By Rajin)
    // Represents a piece an an ImageView
    class PieceGUI extends ImageView {
        final double xCoord, yCoord;
        TileGUI parentTile;

        PieceGUI(Dice dice) {
            for (Node node:gamePane.getChildren()) {
                if (node instanceof TileGUI) {
                    TileGUI tile = (TileGUI) node;
                    if (tile.xIndex == dice.getPosition().getX() && tile.yIndex == dice.getPosition().getY()) {
                        parentTile = tile;
                    }
                }
            }

            String diceAssetURI = URI_BASE+(dice.isPlayer1() ? "w" : "b")+"_dice_"+dice.getTopNumber()+".png";
            this.setImage(new Image(diceAssetURI));
            this.xCoord = parentTile.xCoord;
            this.yCoord = parentTile.yCoord;
            this.relocate(xCoord, yCoord );
            this.setFitWidth(TILE_SIZE + 1);
            this.setFitHeight(TILE_SIZE + 1);

            setOnMouseClicked(mouseEvent -> {
                if (gameState.getPlayerTurn() == dice.isPlayer1() && AIchoice == 0 || AIchoice == 1 && gameState.getPlayerTurn() && dice.isPlayer1() || AIchoice == 2 && gameState.getPlayerTurn() && dice.isPlayer1() || AIchoice == 5 && !gameState.getPlayerTurn() && !dice.isPlayer1() || AIchoice == 6 && !gameState.getPlayerTurn() && !dice.isPlayer1()) {
                    // Remove 'darken' effects from the other PieceGUIs
                    for (Node node:gamePieces.getChildren()) {
                        if (node instanceof PieceGUI && !(((PieceGUI) node).parentTile == this.parentTile)) {
                            ColorAdjust colorAdjust = new ColorAdjust();
                            colorAdjust.setBrightness(0);
                            node.setEffect(colorAdjust);
                        }
                    }

                    // Set the 'darken' effect on the current PieceGUI
                    ColorAdjust colorAdjust = new ColorAdjust();
                    colorAdjust.setBrightness(-0.25);
                    this.setEffect(colorAdjust);

                    generateLegalIndicators(dice);
                }

                currentlySelectedDice = null;
                // Select the current dice
                currentlySelectedDice = dice;

                // Update the orientation pane
                orientationTiles.updateOrientation(currentlySelectedDice);
            });
        }
    }

    // (By Rajin)
    // Represents the legal move indicator GUI
    class LegalIndicatorGUI extends TileGUI {
        LegalIndicatorGUI(int xIndex, int yIndex, Step legalStep) {
            super(xIndex, yIndex);
            setImage(new Image(URI_BASE+"legal.png"));

            // Updates orientation panel with the potential dice orientation
            setOnMouseEntered(mouseEvent -> {
                State potentialState = new State(gameState.isPur() ? Cublino.applyMovePur(gameState.toString(), legalStep.toString()) : Cublino.applyMoveContra(gameState.toString(), legalStep.toString()));
                Dice potentialDice = potentialState.getDiceAt(legalStep.getEndPosition());

                orientationTiles.updateOrientation(potentialDice);
            });

            setOnMouseExited(mouseEvent -> orientationTiles.updateOrientation(currentlySelectedDice));
        }
    }

    // (By Rajin)
    // Represents a tile in the game board which acts as a parent location for the PieceGUI to snap to
    class TileGUI extends ImageView {
        final double xCoord, yCoord;
        final int xIndex, yIndex;

        TileGUI(int xIndex, int yIndex) {
            this.setImage(new Image(URI_BASE+"tile.png"));
            this.xIndex = xIndex;
            this.yIndex = yIndex;
            this.xCoord = 19+60*(xIndex-1);
            this.yCoord = GAMEPANE_SIZE - (11+60*(yIndex));
            this.relocate(xCoord, yCoord);
            this.setFitWidth(TILE_SIZE);
            this.setFitHeight(TILE_SIZE);

            // Try to apply move if the current tile pressed is a legal indicator
            setOnMouseClicked(mouseEvent -> {
                for (Step step:availableLegalSteps) {
                    if (step.getEndPosition().getX() == xIndex && step.getEndPosition().getY() == yIndex) {

                        legalStepsGroup.getChildren().clear();
                        gamePane.getChildren().remove(legalStepsGroup);
                        gamePane.getChildren().add(legalStepsGroup);

                        if (onGoingMove.length() == 0 && onGoingDice == null) {
                            onGoingMove.append(step.getStartPosition());
                            prevGameState = gameState;
                        }

                        if (gameState.isPur()) {
                            gameState = new State(Cublino.applyMovePur(gameState.toString(), step.toString()));
                        } else {
                            gameState = new State(Cublino.applyMoveContra(gameState.toString(), step.toString()));
                        }

                        validityLabel.setText("Valid Move: Valid");

                        State potentialNextState = new State(gameState.toString());
                        potentialNextState.setTurn(!potentialNextState.getPlayerTurn());


                        if (gameState.getDiceAt(step.getEndPosition()) != null) {
                            ArrayList<Step> potentialJumpStates = new ArrayList<>(potentialNextState.legalStepsPur(gameState.getDiceAt(step.getEndPosition())));
                            potentialJumpStates.removeIf(Step::isTip);

                            if (gameState.isPur() && potentialJumpStates.size() != 0 ) {
                                for (Step jump: potentialJumpStates) {
                                    if (jump.getStartPosition().getX() == xIndex && jump.getStartPosition().getY() == yIndex) {
                                        gameState = potentialNextState;
                                        // Update game board state
                                        onGoingMove.append(jump.getStartPosition().toString());
                                        updateDices();
                                        for (Dice dice : dicePieces) {
                                            if (dice.getPosition().getX() == xIndex && dice.getPosition().getY() == yIndex) {
                                                onGoingDice = dice;
                                                generateLegalIndicators(dice);
                                                break;
                                            }
                                        }
                                    }
                                }
                            } else { // There are no other possible steps that could occur (i.e. ends player turn)
                                onGoingMove.replace(0, onGoingMove.length(), "");
                                onGoingDice = null;
                            }
                        }
                        // Update game board state
                        updateDices();
                        break;
                    }
                }
            });
        }
    }

    // (By Rajin)
    // Represents the orientation dice GUIs on the orientation panel
    class OrientationTiles  {
        ArrayList<ImageView> orientationTileImages = new ArrayList<>();

        OrientationTiles() {
            ImageView topTile = new ImageView(new Image(URI_BASE + "tile.png"));
            topTile.relocate(75, 75);
            orientationTileImages.add(topTile);

            ImageView forwardTile = new ImageView(new Image(URI_BASE + "tile.png"));
            forwardTile.relocate(75, 15);
            orientationTileImages.add(forwardTile);

            ImageView rightTile = new ImageView(new Image(URI_BASE + "tile.png"));
            rightTile.relocate(135, 75);
            orientationTileImages.add(rightTile);

            ImageView behindTile = new ImageView(new Image(URI_BASE + "tile.png"));
            behindTile.relocate(75, 135);
            orientationTileImages.add(behindTile);

            ImageView leftTile = new ImageView(new Image(URI_BASE + "tile.png"));
            leftTile.relocate(15, 75);
            orientationTileImages.add(leftTile);
        }

        // Displays the dice faces on the orientation panel
        public void show() {
            orientationPanel.getChildren().clear();
            for (ImageView tile:orientationTiles.orientationTileImages) {
                orientationPanel.getChildren().add(tile);
            }
        }

        // Updates the dice faces on the orientation panel
        public void updateOrientation(Dice dice) {
            if (dice != null) {
                String currentDiceColour = dice.isPlayer1() ? "w_" : "b_";
                int[] currentDiceFaces = dice.getFaces();

                // Update the images based on currently selected dice
                orientationTileImages.get(0).setImage(new Image(URI_BASE+currentDiceColour+"dice_"+currentDiceFaces[0]+".png")); // top face
                orientationTileImages.get(1).setImage(new Image(URI_BASE+currentDiceColour+"dice_"+currentDiceFaces[1]+".png")); // forward face
                orientationTileImages.get(2).setImage(new Image(URI_BASE+currentDiceColour+"dice_"+currentDiceFaces[2]+".png")); // right face
                orientationTileImages.get(3).setImage(new Image(URI_BASE+currentDiceColour+"dice_"+currentDiceFaces[3]+".png")); // bottom face
                orientationTileImages.get(4).setImage(new Image(URI_BASE+currentDiceColour+"dice_"+currentDiceFaces[4]+".png")); // left face

                show();
            } else {
                orientationTileImages.get(0).setImage(new Image(URI_BASE+"danger.png")); // top face
                orientationTileImages.get(1).setImage(new Image(URI_BASE+"danger.png")); // forward face
                orientationTileImages.get(2).setImage(new Image(URI_BASE+"danger.png")); // right face
                orientationTileImages.get(3).setImage(new Image(URI_BASE+"danger.png")); // bottom face
                orientationTileImages.get(4).setImage(new Image(URI_BASE+"danger.png")); // left face

                show();
            }
        }

        // Clears the dice faces from the orientation panel
        public void clearOrientation() {
            orientationTileImages.get(0).setImage(new Image(URI_BASE+"tile.png")); // top face
            orientationTileImages.get(1).setImage(new Image(URI_BASE+"tile.png")); // forward face
            orientationTileImages.get(2).setImage(new Image(URI_BASE+"tile.png")); // right face
            orientationTileImages.get(3).setImage(new Image(URI_BASE+"tile.png")); // bottom face
            orientationTileImages.get(4).setImage(new Image(URI_BASE+"tile.png")); // left face

            show();
        }
    }

}

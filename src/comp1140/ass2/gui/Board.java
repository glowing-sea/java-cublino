package comp1140.ass2.gui;

import comp1140.ass2.Cublino;
import comp1140.ass2.core.Dice;
import comp1140.ass2.core.State;
import comp1140.ass2.core.Step;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.stream.Collectors;

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
    private final static Group gamePieces = new Group();
    private final static Group legalStepsGroup = new Group();
    private final Group controls = new Group();

    private ArrayList<Step> availableLegalSteps = new ArrayList<>();
    private ArrayList<Dice> dicePieces = new ArrayList<>();
    private StringBuilder onGoingMove = new StringBuilder("");
    private Dice onGoingDice;

    private Label playerTurnLabel = new Label("Player Turn: "+ (gameState.getPlayerTurn() ? "White" : "Black"));
    private Label validityLabel = new Label("Valid Move: N/A");

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cublino");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        makeControls();
        makeBoard();
        updateDices();

        root.getChildren().addAll(gamePane, controls);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // (By Rajin)
    // creates a game board with the tile GUI
    public void makeBoard() {
        gamePane.getChildren().clear();
        gamePane.setMinSize(GAMEPANE_SIZE,GAMEPANE_SIZE);
        gamePane.setLayoutX(100);
        gamePane.setLayoutY(100);
        gamePane.setBackground(new Background(new BackgroundImage(new Image(URI_BASE+"board.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        // make a 7x7 tile board
        for (int i = 1; i < 8; i++) {
            for (int j = 1; j < 8; j++) {
                gamePane.getChildren().add(new TileGUI(i,j));
            }
        }
    }

    // (By Rajin)
    // populates a game board with dice GUIs at the default locations
    public void updateDices() {
        gamePieces.getChildren().clear();
        gamePane.getChildren().remove(gamePieces);

        dicePieces.clear();

        for (Dice dice:gameState.getDices()) {
            gamePieces.getChildren().add(new PieceGUI(dice));
            dicePieces.add(dice);
        }

        gamePane.getChildren().add(gamePieces);

        updateLabelUI();
    }

    public void updateLabelUI() {
        playerTurnLabel.setText("Player Turn: "+ (gameState.getPlayerTurn() ? "White" : "Black"));
    }

    // (By Rajin)
    private void makeControls() {
        Button end_turn = new Button("End Turn");
        end_turn.setStyle("-fx-background-radius: 1em; ");
        end_turn.setOnAction(actionEvent -> {

            // TODO: FIX this section

            if (onGoingMove.length() > 0 && onGoingDice != null) {
                if (!Cublino.isValidMovePur(prevGameState.toString(), onGoingMove.toString())) {
                    gameState = prevGameState;
                    gameState.setPlayer1Turn(!gameState.getPlayerTurn());
                    System.out.println("returned to previous state: " + gameState);
                    validityLabel.setText("Valid Move: Invalid");
                } else {
                    validityLabel.setText("Valid Move: Valid");
                }
            }

            System.out.println("AAA");
            onGoingMove.replace(0, onGoingMove.length(), "");
            onGoingDice = null;


            gameState.setPlayer1Turn(!gameState.getPlayerTurn());
            updateDices();

            legalStepsGroup.getChildren().clear();

            System.out.println("Game State: "+gameState);
        });

        VBox vb = new VBox();

        vb.getChildren().addAll(playerTurnLabel, validityLabel, end_turn);
        vb.setSpacing(10);
        vb.setLayoutX(100);
        vb.setLayoutY(BOARD_HEIGHT - 120);

        controls.getChildren().addAll(vb);
    }

    public void generateLegalIndicators(Dice dice) {
        // get legal steps based on whether the game mode is pur or contra
        ArrayList<Step> legalSteps = gameState.isPur() ? gameState.getLegalStepPur(dice) : gameState.getLegalStepContra(dice);

        legalStepsGroup.getChildren().clear();
        availableLegalSteps.clear();

        System.out.println(legalSteps);
        System.out.println(onGoingMove.length() > 0 && onGoingDice != null);

        if (onGoingMove.length() > 0 && onGoingDice != null) {
            if (dice.compareTo(onGoingDice) == 0) {
                legalSteps.removeIf(step -> step.isTip());
                legalSteps.removeIf(step -> !step.getStartPosition().equals(onGoingDice.getPosition()));
                legalSteps.removeIf(step -> onGoingMove.substring(onGoingMove.length()-2, onGoingMove.length()).contains(step.getEndPosition().toString()));

                for (Node node:gamePane.getChildren()) {
                    for (Step legalStep:legalSteps) {
                        if (node instanceof TileGUI) {
                            TileGUI tile = (TileGUI) node;
                            if (tile.xIndex == legalStep.getEndPosition().getX() && tile.yIndex == legalStep.getEndPosition().getY()) {
                                LegalIndicatorGUI legalIndicator = new LegalIndicatorGUI(tile.xIndex, tile.yIndex);
                                legalStepsGroup.getChildren().add(legalIndicator);
                                availableLegalSteps.add(legalStep);
                                System.out.println(availableLegalSteps);
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
                            LegalIndicatorGUI legalIndicator = new LegalIndicatorGUI(tile.xIndex, tile.yIndex);
                            legalStepsGroup.getChildren().add(legalIndicator);
                            availableLegalSteps.add(legalStep);
                            System.out.println(availableLegalSteps);
                        }
                    }
                }
            }
        }


        gamePane.getChildren().remove(legalStepsGroup);
        gamePane.getChildren().add(legalStepsGroup);
    }


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

            // display all the legal moves
            setOnMouseClicked(mouseEvent -> {
                // remove effects from the other PieceGUIs
                for (Node node:gamePieces.getChildren()) {
                    if (node instanceof PieceGUI && !(((PieceGUI) node).parentTile == this.parentTile)) {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        colorAdjust.setBrightness(0);
                        ((PieceGUI) node).setEffect(colorAdjust);
                    }
                }

                ColorAdjust colorAdjust = new ColorAdjust();
                colorAdjust.setBrightness(-0.25);
                this.setEffect(colorAdjust);

                generateLegalIndicators(dice);
                System.out.println("Game State: "+gameState);
            });
        }
    }

    class LegalIndicatorGUI extends TileGUI {
        LegalIndicatorGUI(int xIndex, int yIndex) {
            super(xIndex, yIndex);
            setImage(new Image(URI_BASE+"legal.png"));
        }
    }

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

            // try to apply move
            setOnMouseClicked(mouseEvent -> {
                // if the tile pressed is a legal move indicator
                for (Step step:availableLegalSteps) {
                    if (step.getEndPosition().getX() == xIndex && step.getEndPosition().getY() == yIndex) {
                        System.out.println("Pressed");

                        legalStepsGroup.getChildren().clear();
                        gamePane.getChildren().remove(legalStepsGroup);
                        gamePane.getChildren().add(legalStepsGroup);

                        // TODO: implement a way to find out if subsequent legal steps are available and show them (a way to complete the step)

                        if (onGoingMove.length() == 0 && onGoingDice == null) {
                            onGoingMove.append(step.getStartPosition());
                            prevGameState = gameState;
                        }

                        if (gameState.isPur()) {
                            gameState = new State(Cublino.applyMovePur(gameState.toString(), step.toString()));
                        } else if (!gameState.isPur()) {
                            gameState = new State(Cublino.applyMoveContra(gameState.toString(), step.toString()));
                        }

                        validityLabel.setText("Valid Move: Valid");



                        State potentialNextState = new State(gameState.toString());
                        potentialNextState.setPlayer1Turn(!potentialNextState.getPlayerTurn());



                        System.out.println("Potential Jumps: " + potentialNextState.generateAllJumpPur());


                        ArrayList<Step> potentialJumpStates = new ArrayList<>(potentialNextState.getLegalStepPur(gameState.getDiceAt(step.getEndPosition())));
                        potentialJumpStates.removeIf(p -> p.isTip());

                        // there are more steps that can happen in this current move
                        if (gameState.isPur() && potentialJumpStates.size() != 0 ) {
                            for (Step jump: potentialJumpStates) {
                                if (jump.getStartPosition().getX() == xIndex && jump.getStartPosition().getY() == yIndex) {
                                    gameState = potentialNextState;
                                    // update game board state
                                    onGoingMove.append(jump.getStartPosition().toString());
                                    updateDices();
                                    System.out.println("on going");
                                    for (Dice dice:dicePieces) {
                                        if (dice.getPosition().getX() == xIndex && dice.getPosition().getY() == yIndex) {
                                            onGoingDice = dice;
                                            generateLegalIndicators(dice);
                                            break;
                                        }
                                    }
                                }
                            }
                        // there are no other possible steps that could occur (i.e. ends player turn)
                        } else {
                            System.out.println("AAA");
                            onGoingMove.replace(0, onGoingMove.length(), "");
                            onGoingDice = null;
                        }

                        // update game board state
                        updateDices();
                        break;
                    }
                }
                System.out.println("OnGoing Move : "+onGoingMove);
            });
        }
    }
}

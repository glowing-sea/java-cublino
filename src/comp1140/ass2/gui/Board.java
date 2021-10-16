package comp1140.ass2.gui;

import comp1140.ass2.core.Dice;
import comp1140.ass2.core.State;
import comp1140.ass2.core.Step;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int TILE_SIZE = 52;
    private static final int GAMEPANE_SIZE = 450;
    private static final String URI_BASE = "assets/";

    private State gameState = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");
    private final Group root = new Group();
    private final static Pane gamePane = new Pane();
    private final static Group gamePieces = new Group();
    private final static Group legalStepsGroup = new Group();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cublino");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        makeBoard();
        initialiseDices();

        root.getChildren().addAll(gamePane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    // (By Rajin)
    // creates a game board with the tile GUI
    public void makeBoard() {
        gamePane.getChildren().clear();
        gamePane.setMinSize(GAMEPANE_SIZE,GAMEPANE_SIZE);
        gamePane.setLayoutX(245);
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
    public void initialiseDices() {
        gamePieces.getChildren().clear();
        gameState = new State("PWa1Wb1Wc1Wd1We1Wf1Wg1va7vb7vc7vd7ve7vf7vg7");

        for (Dice dice:gameState.getDices()) {
            gamePieces.getChildren().add(new PieceGUI(dice));
        }

        gamePane.getChildren().add(gamePieces);
    }

    class PieceGUI extends ImageView {
        final double xCoord, yCoord;
        TileGUI parentTile;
        ArrayList<Step> legalSteps;

        PieceGUI(Dice dice) {
            legalSteps = gameState.getLegalMove(dice);

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
                legalStepsGroup.getChildren().clear();

                for (Node node:gamePane.getChildren()) {
                    for (Step legalStep:legalSteps) {
                        if (node instanceof TileGUI) {
                            TileGUI tile = (TileGUI) node;
                            if (tile.xIndex == legalStep.getEndPosition().getX() && tile.yIndex == legalStep.getEndPosition().getY()) {
                                ImageView legalIndicator = new ImageView(new Image(URI_BASE+"legal.png"));
                                legalIndicator.relocate(tile.xCoord, tile.yCoord);
                                legalStepsGroup.getChildren().add(legalIndicator);
                            }
                        }
                    }
                }

                gamePane.getChildren().remove(legalStepsGroup);
                gamePane.getChildren().add(legalStepsGroup);
            });
        }
    }

    class TileGUI extends ImageView {
        final double xCoord, yCoord;
        final int xIndex, yIndex;

        TileGUI(int xIndex, int yIndex) {
            this.setImage(new Image(URI_BASE+"tile.png"));
            this.xIndex = xIndex;
            this.yIndex = yIndex;
            this.xCoord = GAMEPANE_SIZE - (11+60*(xIndex));
            this.yCoord = GAMEPANE_SIZE - (11+60*(yIndex));
            this.relocate(xCoord, yCoord);
            this.setFitWidth(TILE_SIZE);
            this.setFitHeight(TILE_SIZE);
        }
    }
}

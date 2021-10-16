package comp1140.ass2.gui;

import comp1140.ass2.core.Dice;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;
    private static final int TILE_SIZE = 52;
    private static final int GAMEPANE_SIZE = 450;
    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final static Pane gamePane = new Pane();
    private final Group gamePieces = new Group();

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cublino");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);
        makeBoard();

        root.getChildren().addAll(gamePane);
        primaryStage.setScene(scene);
        primaryStage.show();
    }


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

    class PieceGUI extends ImageView {
        final double xCoord, yCoord;
        TileGUI parentTile;

        PieceGUI(Dice dice) {
            String diceAssetURI = URI_BASE+(dice.isPlayer1() ? "w" : "b")+"_dice_"+dice.getTopNumber()+".png";
            this.setImage(new Image(diceAssetURI));
            this.xCoord = parentTile.xCoord;
            this.yCoord = parentTile.yCoord;
            this.relocate(xCoord, yCoord);
            this.setFitWidth(TILE_SIZE - 2);
            this.setFitHeight(TILE_SIZE - 2);
        }
    }

    class TileGUI extends ImageView {
        final double xCoord, yCoord;

        TileGUI(int xIndex, int yIndex) {
            this.setImage(new Image(URI_BASE+"tile.png"));
            this.xCoord = GAMEPANE_SIZE - (11+60*(xIndex));
            this.yCoord = GAMEPANE_SIZE - (11+60*(yIndex));
            this.relocate(xCoord, yCoord);
            this.setFitWidth(TILE_SIZE);
            this.setFitHeight(TILE_SIZE);
        }
    }
}

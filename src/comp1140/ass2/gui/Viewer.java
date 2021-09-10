package comp1140.ass2.gui;

import comp1140.ass2.Cublino;
import comp1140.ass2.core.Piece;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.util.ArrayList;
import java.util.HashMap;


/**
 * A very simple viewer for piece placements in the Cublino game.
 * <p>
 * NOTE: This class is separate from your main game class.  This
 * class does not play a game, it just illustrates various piece
 * placements.
 */
public class Viewer extends Application {

    /* board layout */
    private static final int VIEWER_WIDTH = 933;
    private static final int VIEWER_HEIGHT = 700;

    private static final String URI_BASE = "assets/";

    private final Group root = new Group();
    private final Group controls = new Group();
    private final Pane gamePane = new Pane();
    private TextField textField;

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    void makePlacement(String placement) {

        String placementEncoding = placement.trim();
        if (Cublino.isStateValid(placementEncoding)) {
            String diceEncodings = placementEncoding.substring(1,placementEncoding.length()); // all the dice encodings are assumed to be valid
            ArrayList<Piece> dices = new ArrayList<>();

            // add all the dice pieces
            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
                dices.add(new Piece(diceEncodings.substring(i,i+3)));
            }

            // generate sprites at the right locations
            for (Piece dice : dices) {
                String colour = dice.isPlayer1() ? "w" : "b";
                String topFacing = String.valueOf(dice.getTopNumber());
                String diceAssetURI = URI_BASE+colour+"_dice_"+topFacing+".png"; // dice image path based on the dice's properties

                ImageView diceImage = new ImageView();
                diceImage.setImage(new Image(diceAssetURI));
                diceImage.relocate(19+60*(dice.getPosition().getY()-1),19+60*(dice.getPosition().getX()-1)); // pixel translation

                gamePane.getChildren().add(diceImage); // add the leaf nodes to the parent node
                System.out.println("added");
            }

        } else if (!placement.equals("")) { // displays a warning dialog if placement string is invalid
            Alert a1 = new Alert(Alert.AlertType.WARNING,
                    "Please Enter A Valid Placement String.", ButtonType.OK);
            a1.setResizable(false);
            a1.show();
        }
    }

    /* Renders the base game board */
    private void renderBoard() {
        gamePane.setMinSize(450,450);
        gamePane.setLayoutX(240);
        gamePane.setLayoutY(100);
        gamePane.setBackground(new Background(new BackgroundImage(new Image(URI_BASE+"board.png"),
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));
    }

    /**
     * Create a basic text field for input and a refresh button.
     */
    private void makeControls() {
        Label label1 = new Label("Placement:");
        textField = new TextField();
        textField.setPrefWidth(300);
        Button refresh = new Button("Refresh");
        refresh.setOnAction(actionEvent -> {
                // erase all the current dices on the board before rendering the dices
                if (gamePane.getChildren().size() > 0) {
                    gamePane.getChildren().clear();
                    System.out.println("removed dices");
                }

                // render the dice placements on the board
                makePlacement(textField.getText());
                textField.clear();
        });
        HBox hb = new HBox();

        hb.getChildren().addAll(label1, textField, refresh);
        hb.setSpacing(10);
        hb.setLayoutX(230);
        hb.setLayoutY(VIEWER_HEIGHT - 50);

        controls.getChildren().addAll(hb);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Cublino Viewer");
        primaryStage.setResizable(false);
        Scene scene = new Scene(root, VIEWER_WIDTH, VIEWER_HEIGHT);

        makeControls();
        renderBoard();

        root.getChildren().addAll(gamePane, controls);

        primaryStage.setScene(scene);
        primaryStage.show();

        // Refresh Key's Alternative Option: Press Enter
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // erase all the current dices on the board before rendering the dices
                if (gamePane.getChildren().size() > 0) {
                    gamePane.getChildren().clear();
                    System.out.println("removed dices");
                }

                // render the dice placements on the board
                makePlacement(textField.getText());
                textField.clear();
            }
        });
    }

}

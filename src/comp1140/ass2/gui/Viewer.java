package comp1140.ass2.gui;

import comp1140.ass2.Cublino;
import comp1140.ass2.core.Dice;
import javafx.application.Application;
import javafx.geometry.Insets;
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
import javafx.util.Duration;

import java.util.ArrayList;


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
    private Label gameStringStatus = new Label(); // a label to display the validity of the placement string

    ArrayList<Dice> dices = new ArrayList<>(); // list of dices in the current pane

    /**
     * Draw a placement in the window, removing any previously drawn one
     *
     * @param placement A valid placement string
     */
    // (By Rajin)
    void makePlacement(String placement) {
        String placementEncoding = placement.trim();
        if (Cublino.isStateWellFormed(placementEncoding)) {
            String diceEncodings = placementEncoding.substring(1); // all the dice encodings are assumed to be valid
            dices.clear(); // clear the dices

            // add all the dice pieces
            for (int i = 0; i < diceEncodings.length()-2; i+=3) {
                dices.add(new Dice(diceEncodings.substring(i,i+3)));
            }

            // generate sprites at the right locations
            for (Dice dice : dices) {
                String colour = dice.isPlayer1() ? "w" : "b";
                String topFacing = String.valueOf(dice.getTopNumber());
                String diceAssetURI = URI_BASE+colour+"_dice_"+topFacing+".png"; // dice image path based on the dice's properties

                ImageView diceImage = new ImageView();
                diceImage.setImage(new Image(diceAssetURI));
                diceImage.relocate(gamePane.getWidth() - (11+60*(dice.getPosition().getX())) , gamePane.getHeight()-(11+60*(dice.getPosition().getY()))); // pixel translation

                Pane orientationPane = new Pane(); // orientation display pane
                int[] sides = dice.getFaces(); // sides of the currently selected dice

                Label orientationPaneTitle = new Label("Dice Orientation For: "+dice);
                orientationPaneTitle.setStyle("-fx-font-weight: bold; -fx-font-size: 12;");
                orientationPaneTitle.relocate(20 + orientationPane.getWidth()/2, 0);

                Label forward_Text = new Label(String.valueOf(sides[1]));
                forward_Text.setFont(new Font(13));
                forward_Text.relocate(80,40);

                Label top_Text = new Label(String.valueOf(dice.getTopNumber()));
                top_Text.setFont(new Font(13));
                top_Text.relocate(80,60);

                Label right_Text = new Label(String.valueOf(sides[2]));
                right_Text.setFont(new Font(13));
                right_Text.relocate(100,60);

                Label left_Text = new Label(String.valueOf(sides[4]));
                left_Text.setFont(new Font(13));
                left_Text.relocate(60,60);

                Label backward_Text = new Label(String.valueOf(sides[3]));
                backward_Text.setFont(new Font(13));
                backward_Text.relocate(80,80);

                orientationPane.setPadding(new Insets(10,10,10,10));

                orientationPane.getChildren().addAll(orientationPaneTitle, top_Text, forward_Text, backward_Text, left_Text, right_Text);

                // adding a tooltip to each imageview in order to view the orientation panel
                Tooltip orientationDisplay = new Tooltip();
                orientationDisplay.setGraphic(orientationPane);
                orientationDisplay.setShowDelay(new Duration(100));
                orientationDisplay.setHideDelay(new Duration(100));
                Tooltip.install(diceImage, orientationDisplay);

                gamePane.getChildren().add(diceImage); // add the leaf nodes to the parent node
            }

        } else if (!placement.equals("")) { // displays a warning dialog if placement string is not well formed
            Alert a1 = new Alert(Alert.AlertType.NONE,
                    "Please Enter A Well Formed String.", ButtonType.OK);
            a1.setResizable(false);
            a1.setTitle("ERROR: Well Formed String Missing");
            a1.show();
        }

        // Let user know whether the placement string is valid
        if (Cublino.isStateValid(placementEncoding)) {
            gameStringStatus.setText("Placement String Validity: VALID");
            gameStringStatus.setTextFill(Color.GREEN);
        } else if (placement.equals("")) {
            gameStringStatus.setText("Placement String Validity: NONE");
            gameStringStatus.setTextFill(Color.BLACK);
        } else {
            gameStringStatus.setText("Placement String Validity: INVALID");
            gameStringStatus.setTextFill(Color.RED);
        }
    }

    // (By Rajin)
    /* Renders the base game board */
    private void renderBoard() {
        gamePane.setMinSize(450,450);
        gamePane.setLayoutX(245);
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

        // (By Rajin)
        Button display = new Button("Display");
        display.setOnAction(actionEvent -> {
                // erase all the current dices on the board before rendering the dices
                if (gamePane.getChildren().size() > 0) {
                    gamePane.getChildren().clear();
                }

                // render the dice placements on the board
                makePlacement(textField.getText());
        });

        // (By Rajin)
        Button clear = new Button("Clear");
        // a clear button to clear the placement string
        clear.setOnAction(actionEvent -> {
            if (gamePane.getChildren().size() > 0) {
                gamePane.getChildren().clear();
            }

            gameStringStatus.setTextFill(Color.BLACK);
            gameStringStatus.setText("Placement String Validity: NONE");

            textField.clear();
        });

        HBox hb = new HBox();

        hb.getChildren().addAll(label1, textField, display, clear);
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

        gameStringStatus.setText("Placement String Validity: NONE");
        gameStringStatus.setLayoutX(10);
        gameStringStatus.setLayoutY(10);

        makeControls();
        renderBoard();

        root.getChildren().addAll(gameStringStatus, gamePane, controls);

        primaryStage.setScene(scene);
        primaryStage.show();

        // (By Rajin)
        // Refresh Key's Alternative Option: Press Enter
        scene.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.ENTER) {
                // erase all the current dices on the board before rendering the dices
                if (gamePane.getChildren().size() > 0) {
                    gamePane.getChildren().clear();
                }

                // render the dice placements on the board
                makePlacement(textField.getText());
            }
        });
    }

}

package comp1140.ass2.gui;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Board extends Application {

    private static final int BOARD_WIDTH = 933;
    private static final int BOARD_HEIGHT = 700;

    private final Group root = new Group();

    //  FIXME Task 9 (CR): Implement a basic playable Cublino game in JavaFX that only allows valid moves to be played

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Cublino");
        Scene scene = new Scene(root, BOARD_WIDTH, BOARD_HEIGHT);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}

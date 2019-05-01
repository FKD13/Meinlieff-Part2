package Meinlieff.GameFinished;

import Meinlieff.Companion;
import Meinlieff.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

//the companion class for when the game ends for some reason - done - cheater - quit - ...
public class GameFinishedCompanion implements Companion {

    @FXML
    public Label message;
    @FXML
    public Button quit;
    @FXML
    public Button play;

    private Main main;
    private String text;

    public GameFinishedCompanion(Main main, String text) {
        this.main = main;
        this.text = text;
    }

    public void initialize() {
        quit.setOnAction(e -> quit());
        play.setOnAction(e -> play());
        message.setText(text);
    }

    private void quit() {
        Platform.exit();
    }

    private void play() {
        //todo start new game
        System.out.println("this will start a new game");
    }
}

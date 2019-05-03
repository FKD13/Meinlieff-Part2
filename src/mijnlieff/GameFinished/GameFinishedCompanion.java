package mijnlieff.GameFinished;

import mijnlieff.Companion;
import mijnlieff.Mijnlieff;
import mijnlieff.ServerClient.Client;
import mijnlieff.ServerSelection.ServerSelectionCompanion;
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

    private Mijnlieff mijnlieff;
    private Client client;
    private String text;

    public GameFinishedCompanion(Mijnlieff mijnlieff, Client client, String text) {
        this.client = client;
        this.mijnlieff = mijnlieff;
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
        client.disconnect();
        mijnlieff.openWindow("/mijnlieff/ServerSelection/ServerSelection.fxml", new ServerSelectionCompanion(mijnlieff, client));
    }
}

package Meinlieff.ServerSelection;

import Meinlieff.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class ServerSelectionController {

    private Main main;

    @FXML
    public Button connect;
    @FXML
    public Button cancel;

    public ServerSelectionController(Main main) {
        this.main = main;
    }

    public void connect() {
        main.openMainMenu("", 0);
    }

    public void cancel() {
        Platform.exit();
    }
}

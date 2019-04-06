package Meinlieff.ServerSelection;

import Meinlieff.Main;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ServerSelectionController {

    private Main main;

    @FXML
    public Button connect;
    @FXML
    public Button cancel;
    @FXML
    public TextField host;
    @FXML
    public TextField port;
    @FXML
    public TextField username;

    public ServerSelectionController(Main main) {
        this.main = main;
    }

    public void connect() {
        main.openMainMenu(host.getText().trim(), Integer.parseInt(port.getText()), username.getText().trim());
    }

    public void cancel() {
        Platform.exit();
    }
}

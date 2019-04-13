package Meinlieff.ServerSelection;

import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ServerClient.Client;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ServerSelectionCompanion implements Companion {

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

    public ServerSelectionCompanion(Main main) {
        this.main = main;
    }

    public void connect() {
        Client client = new Client();
        if (client.connect(host.getText().trim(), Integer.parseInt(port.getText().trim())) &&
                client.login(username.getText().trim())) {

            main.openWindow("/Meinlieff/MainMenu/MainMenu.fxml",
                    new MainMenuCompanion(main, client));
        }
    }

    public void cancel() {
        Platform.exit();
    }
}

package Meinlieff.ServerSelection;

import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.ConnectTask;
import Meinlieff.ServerClient.ServerTasks.LoginTask;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class ServerSelectionCompanion implements Companion {

    private Main main;
    private Client client;
    private ConnectTask connect_task;
    private LoginTask login_task;

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
    @FXML
    public Label errorLabel;

    public ServerSelectionCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
    }

    public void initialize() {
        errorLabel.setVisible(false);
        errorLabel.getStyleClass().add("warning");

        //todo check for other option than textProperty()
        host.textProperty().addListener((e) -> errorLabel.setVisible(false));
        port.textProperty().addListener((e) -> errorLabel.setVisible(false));
        username.textProperty().addListener((e) -> errorLabel.setVisible(false));

        connect.setOnAction((e) -> connect());
        cancel.setOnAction((e) -> cancel());
    }

    private void errorMsg(String string) {
        errorLabel.setVisible(true);
        errorLabel.setText(string);
    }

    private void connect() {
        if (isValid(host.getText().trim())) {
            if (isValidPort(port.getText().trim())) {
                if (isValid(username.getText().trim())) {
                    if (connect_task == null) {
                        connect_task = client.getConnectTask();
                        connect_task.setAdress(host.getText().trim(), Integer.parseInt(port.getText().trim()));
                        connect_task.stateProperty().addListener(this::connected);
                        new Thread(connect_task).start();
                        connect.setDisable(true);
                    }
                } else {
                    errorMsg("Invalid username");
                }
            } else {
                errorMsg("Invalid port");
            }
        } else {
            errorMsg("Invalid host");
        }
    }

    private void connected(Observable o) {
        if (connect_task.getState() == Worker.State.SUCCEEDED) {
            if (connect_task.getValue()) {
                login_task = client.getLoginTask();
                login_task.setUsername(username.getText().trim());
                login_task.stateProperty().addListener(this::login);
                new Thread(login_task).start();
            } else {
                errorMsg("Could not connect to server");
                connect.setDisable(false);
            }
            connect_task = null;
        }
    }

    private void login(Observable o) {
        if (login_task.getState() == Worker.State.SUCCEEDED) {
            if (login_task.getValue()) {
                main.openWindow("/Meinlieff/MainMenu/MainMenu.fxml", new MainMenuCompanion(main, client));
            } else {
                errorMsg("Could not login whit this username");
                login_task = null;
                connect.setDisable(false);
            }
        }
    }

    private boolean isValid(String string) {
        return ! string.contains(" ") && string.length() > 0;
    }

    private boolean isValidPort(String port) {
        return (port).matches("[0-9]+");
    }

    public void cancel() {
        Platform.exit();
    }
}

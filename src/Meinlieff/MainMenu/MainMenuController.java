package Meinlieff.MainMenu;

import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.Player;
import Meinlieff.ServerClient.ServerTasks.QuequeTask;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;

import java.io.IOException;
import java.util.ArrayList;

public class MainMenuController implements InvalidationListener {

    private Main main;
    private String host;
    private int port;
    private QuequeTask task;

    @FXML
    public ListView<Player> listView;

    public MainMenuController(Main main, String host, int port, String username) {
        this.main = main;
        this.host = host;
        this.port = port;
        Client client = new Client();
        client.connect(host, port);
        try {
            System.out.println(client.login(username));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void invalidated(Observable observable) {
        if (task.getState() == Worker.State.SUCCEEDED) {
            ArrayList<Player> players = task.getValue();
            System.out.println(players);
            listView.setItems(FXCollections.observableList(players));
            listView.refresh();
        }
    }

    public void refresh() {
        task = new QuequeTask(host, port);
        task.stateProperty().addListener(this);
        new Thread(task).start();
    }
}

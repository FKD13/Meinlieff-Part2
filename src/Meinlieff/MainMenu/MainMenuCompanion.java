package Meinlieff.MainMenu;

import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.Player;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MainMenuCompanion implements Companion {

    private Main main;
    private Client client;
    private QueueTaskListener qtl;

    @FXML
    public ListView<Player> listView;
    @FXML
    public Button refresh;
    @FXML
    public Button challenge;
    @FXML
    public Button enqueue;

    public MainMenuCompanion(Main main,Client client) {
        this.main = main;
        this.client = client;
        qtl = new QueueTaskListener(this);
    }

    public void initialize() {
        refresh.setOnAction((e) -> refresh());
        challenge.setOnAction((e) -> challenge());
        enqueue.setOnAction((e) -> enqueue());
    }

    public void updateListView(ArrayList<Player> players) {
        listView.setItems(FXCollections.observableList(players));
        listView.refresh();
    }

    private void refresh() {
        Task<ArrayList<Player>> task = client.getQueue(qtl);
    }

    private void challenge() {
        System.out.println("this button will connect you");
        // TODO
    }

    private void enqueue() {
        System.out.println("this button will enqueue you");
        // TODO
    }
}

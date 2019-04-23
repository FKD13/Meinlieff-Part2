package Meinlieff.MainMenu;

import Meinlieff.BoardPicker.BoardPickerCompanion;
import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.Player;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import Meinlieff.ServerClient.ServerTasks.QueueTask;
import Meinlieff.WaitScreen.WaitGameStartScreen.WaitGameStartScreenCompanion;
import Meinlieff.WaitScreen.WaitQueueScreen.WaitQueueScreenCompanion;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MainMenuCompanion implements Companion {

    private Main main;
    private Client client;

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
    }

    public void initialize() {
        refresh.setOnAction((e) -> refresh_press());
        challenge.setOnAction((e) -> challenge_press());
        enqueue.setOnAction((e) -> enqueue());
        refresh_press();
    }

    public void updateListView(Observable o) {
        QueueTask task = (QueueTask) ((Property) o).getBean();
        if (task.getState().equals(Worker.State.SUCCEEDED)) {
            ArrayList<Player> players = task.getValue();
            listView.setItems(FXCollections.observableList(players));
            listView.refresh();
        }
    }

    private void refresh_press() {
        QueueTask task = client.getQueueTask();
        task.stateProperty().addListener(this::updateListView);
        new Thread(task).start();
    }

    private void challenge_press() {
        if (listView.getSelectionModel().getSelectedItem() != null) {
            AwaitResponseTask task = client.getAwaitResponseTask("C " + listView.getSelectionModel().getSelectedItem().toString());
            task.stateProperty().addListener(this::challenge);
            new Thread(task).start();
        } else {
            //todo "You should choose an opponent"
        }
    }

    private void challenge(Observable o) {
        AwaitResponseTask task = (AwaitResponseTask) ((Property) o).getBean();
        if (task.getState().equals(Worker.State.SUCCEEDED)) {
            String response = task.getValue();
            System.out.println(response);
            System.err.println(response);
            if (response.split(" ")[1].equals("T")) {
                main.openWindow("/Meinlieff/BoardPicker/BoardPicker.fxml", new BoardPickerCompanion(main, client));
            } else {
                main.openWindow("/Meinlieff/WaitScreen/WaitGameStartScreen/WaitGameStartScreen.fxml", new WaitGameStartScreenCompanion(main, client));
            }
        }
    }

    private void enqueue() {
        main.openWindow("/Meinlieff/WaitScreen/WaitQueueScreen/WaitQueueScreen.fxml", new WaitQueueScreenCompanion(main, client));
    }
}

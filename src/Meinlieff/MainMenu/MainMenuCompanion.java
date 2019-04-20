package Meinlieff.MainMenu;

import Meinlieff.BoardPicker.BoardPickerCompanion;
import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.ResponseWaiter;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.Player;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTaskListener;
import Meinlieff.ServerClient.ServerTasks.QueueTaskListener;
import Meinlieff.WaitScreen.WaitGameStartScreen.WaitGameStartScreenCompanion;
import Meinlieff.WaitScreen.WaitQueueScreen.WaitQueueScreenCompanion;
import javafx.collections.FXCollections;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MainMenuCompanion implements Companion, ResponseWaiter {

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
        refresh();
    }

    public void updateListView(ArrayList<Player> players) {
        listView.setItems(FXCollections.observableList(players));
        listView.refresh();
    }

    private void refresh() {
        Task<ArrayList<Player>> task = client.getQueue(qtl);
        new Thread(task).start();
    }

    private void challenge() {
        System.out.println("this button will connect you");
        // TODO
        if (listView.getSelectionModel().getSelectedItem() != null) {
            AwaitResponseTask task = client.getAwaitResponseTask("C " + listView.getSelectionModel().getSelectedItem().toString());
            AwaitResponseTaskListener awaitResponseTaskListener = new AwaitResponseTaskListener(this, task);
            new Thread(task).start();
        }
    }

    @Override
    public void onResponse(AwaitResponseTaskListener awaitResponseTaskListener) {
        String response = awaitResponseTaskListener.getTask().getValue();
        if (response.charAt(2) == 'F') {
            main.openWindow("/Meinlieff/BoardPicker/BoardPicker.fxml", new BoardPickerCompanion(main, client));
        } else {
            main.openWindow("/Meinlieff/WaitScreen/WaitGameStartScreen/WaitGameStartScreen.fxml", new WaitGameStartScreenCompanion(main, client));
        }
    }

    private void enqueue() {
        main.openWindow("/Meinlieff/WaitScreen/WaitQueueScreen/WaitQueueScreen.fxml", new WaitQueueScreenCompanion(main, client));
    }
}

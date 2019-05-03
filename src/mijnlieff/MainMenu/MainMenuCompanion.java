package mijnlieff.MainMenu;

import mijnlieff.BoardPicker.BoardPickerCompanion;
import mijnlieff.Companion;
import mijnlieff.Mijnlieff;
import mijnlieff.ServerClient.Client;
import mijnlieff.ServerClient.Player;
import mijnlieff.ServerClient.ServerTasks.AwaitResponseTask;
import mijnlieff.ServerClient.ServerTasks.QueueTask;
import mijnlieff.WaitScreen.WaitGameStartScreen.WaitGameStartScreenCompanion;
import mijnlieff.WaitScreen.WaitQueueScreen.WaitQueueScreenCompanion;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.collections.FXCollections;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;

import java.util.ArrayList;

public class MainMenuCompanion implements Companion {

    private Mijnlieff mijnlieff;
    private Client client;

    @FXML
    public ListView<Player> listView;
    @FXML
    public Button refresh;
    @FXML
    public Button challenge;
    @FXML
    public Button enqueue;
    @FXML
    public Label errorLabel;

    public MainMenuCompanion(Mijnlieff mijnlieff, Client client) {
        this.mijnlieff = mijnlieff;
        this.client = client;
    }

    public void initialize() {
        errorLabel.setVisible(false);

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
        errorLabel.setVisible(false);
        QueueTask task = client.getQueueTask();
        task.stateProperty().addListener(this::updateListView);
        new Thread(task).start();
    }

    private void challenge_press() {
        errorLabel.setVisible(false);
        if (listView.getSelectionModel().getSelectedItem() != null) {
            AwaitResponseTask task = client.getAwaitResponseTask("C " + listView.getSelectionModel().getSelectedItem().toString());
            task.stateProperty().addListener((e) -> challenge(e));
            new Thread(task).start();
        } else {
            errorLabel.setText("You should choose an opponent");
            errorLabel.setVisible(true);
        }
    }

    private void challenge(Observable o) {
        AwaitResponseTask task = (AwaitResponseTask) ((Property) o).getBean();
        if (task.getState().equals(Worker.State.SUCCEEDED)) {
            String response = task.getValue();
            if (response.split(" ")[1].equals("T")) {
                mijnlieff.openWindow("/mijnlieff/BoardPicker/BoardPicker.fxml", new BoardPickerCompanion(mijnlieff, client));
            } else {
                mijnlieff.openWindow("/mijnlieff/WaitScreen/WaitGameStartScreen/WaitGameStartScreen.fxml", new WaitGameStartScreenCompanion(mijnlieff, client));
            }
        }
    }

    private void enqueue() {
        mijnlieff.openWindow("/mijnlieff/WaitScreen/WaitQueueScreen/WaitQueueScreen.fxml", new WaitQueueScreenCompanion(mijnlieff, client));
    }
}

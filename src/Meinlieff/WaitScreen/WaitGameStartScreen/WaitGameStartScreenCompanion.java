package Meinlieff.WaitScreen.WaitGameStartScreen;

import Meinlieff.Companion;
import Meinlieff.GameBoard.GameBoardCompanion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.WaitTask;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Worker;

public class WaitGameStartScreenCompanion implements Companion {

    private Main main;
    private Client client;

    public WaitGameStartScreenCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
    }

    public void initialize() {
        WaitTask task = client.getWaitTask();
        task.stateProperty().addListener(this::start);
        new Thread(task).start();
    }

    private void start(Observable o) {
        WaitTask task = (WaitTask) ((Property) o).getBean();
        if (task.getState() == Worker.State.SUCCEEDED) {
            main.openWindow("/Meinlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(client, task.getValue().trim(), false));
        }
    }
}

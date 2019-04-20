package Meinlieff.WaitScreen.WaitQueueScreen;

import Meinlieff.BoardPicker.BoardPickerCompanion;
import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ResponseWaiter;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTaskListener;
import Meinlieff.WaitScreen.WaitGameStartScreen.WaitGameStartScreenCompanion;
import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaitQueueScreenCompanion implements Companion, ResponseWaiter {

    @FXML
    public Button cancel;

    private Main main;
    private Client client;
    private AwaitResponseTask task;

    public WaitQueueScreenCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
    }

    public void initialize() {
        cancel.setOnAction((e) -> cancel());
        task = client.getAwaitResponseTask("P");
        AwaitResponseTaskListener awaitResponseTaskListener = new AwaitResponseTaskListener(this, task);
        new Thread(task).start();
    }

    private void cancel() {
        if (task.getLine().equals("P")){
            task.setLine("R");
            task.writeLine();
        }
    }

    @Override
    public void onResponse(AwaitResponseTaskListener awaitResponseTaskListener) {
        if (task.getState().equals(Worker.State.SUCCEEDED)) {
            if (task.getLine().trim().equals("P")) {
                String response = awaitResponseTaskListener.getTask().getValue();
                if (response.charAt(2) == 'F') {
                    main.openWindow("/Meinlieff/BoardPicker/BoardPicker.fxml", new BoardPickerCompanion(main, client));
                } else {
                    main.openWindow("/Meinlieff/WaitScreen/WaitGameStartScreen/WaitGameStartScreen.fxml", new WaitGameStartScreenCompanion(main, client));
                }
            } else if (task.getLine().trim().equals("R")) {
                if (task.getValue().trim().equals("+")) {
                    main.openWindow("/Meinlieff/MainMenu/MainMenu.fxml", new MainMenuCompanion(main, client));
                } else {
                    System.err.println("[WaitQueueScreenCompanion] could not retract from list");
                }
            }
        }
    }
}

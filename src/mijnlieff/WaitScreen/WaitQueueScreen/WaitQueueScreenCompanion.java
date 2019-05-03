package mijnlieff.WaitScreen.WaitQueueScreen;

import mijnlieff.BoardPicker.BoardPickerCompanion;
import mijnlieff.Companion;
import mijnlieff.Mijnlieff;
import mijnlieff.MainMenu.MainMenuCompanion;
import mijnlieff.ServerClient.Client;
import mijnlieff.ServerClient.ServerTasks.AwaitResponseTask;
import mijnlieff.WaitScreen.WaitGameStartScreen.WaitGameStartScreenCompanion;
import javafx.beans.Observable;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaitQueueScreenCompanion implements Companion {

    @FXML
    public Button cancel;

    private Mijnlieff mijnlieff;
    private Client client;
    private AwaitResponseTask task;

    public WaitQueueScreenCompanion(Mijnlieff mijnlieff, Client client) {
        this.mijnlieff = mijnlieff;
        this.client = client;
    }

    public void initialize() {
        cancel.setOnAction((e) -> cancel());
        task = client.getAwaitResponseTask("P");
        task.stateProperty().addListener(this::server_Responded);
        new Thread(task).start();
    }

    private void cancel() {
        if (task.getLine().equals("P")){
            task.setLine("R");
            task.writeLine();
        }
    }

    private void server_Responded(Observable o) {
        if (task.getState().equals(Worker.State.SUCCEEDED)) {
            if (task.getLine().trim().equals("P")) {
                String response = task.getValue();
                if (response.length() >= 3) {
                    if (response.charAt(2) == 'T') {
                        mijnlieff.openWindow("/mijnlieff/BoardPicker/BoardPicker.fxml", new BoardPickerCompanion(mijnlieff, client));
                    } else {
                        mijnlieff.openWindow("/mijnlieff/WaitScreen/WaitGameStartScreen/WaitGameStartScreen.fxml", new WaitGameStartScreenCompanion(mijnlieff, client));
                    }
                }
            } else if (task.getLine().trim().equals("R")) {
                if (task.getValue().trim().equals("+")) {
                    mijnlieff.openWindow("/mijnlieff/MainMenu/MainMenu.fxml", new MainMenuCompanion(mijnlieff, client));
                } else {
                    System.err.println("[WaitQueueScreenCompanion] could not retract from list");
                }
            }
        }
    }
}

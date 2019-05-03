package mijnlieff.WaitScreen.WaitGameStartScreen;

import mijnlieff.BoardPicker.GameBoardCompanionInitializer;
import mijnlieff.Companion;
import mijnlieff.GameBoard.GameBoardCompanion;
import mijnlieff.GameBoard.Point;
import mijnlieff.GameFinished.GameFinishedCompanion;
import mijnlieff.Mijnlieff;
import mijnlieff.ServerClient.Client;
import mijnlieff.ServerClient.ServerTasks.WaitTask;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Worker;

import java.util.ArrayList;

public class WaitGameStartScreenCompanion extends GameBoardCompanionInitializer implements Companion {

    private Mijnlieff mijnlieff;
    private Client client;

    public WaitGameStartScreenCompanion(Mijnlieff mijnlieff, Client client) {
        this.mijnlieff = mijnlieff;
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
            System.out.println(task.getValue().trim());
            if (task.getValue().trim().matches("X( [0-9]){8}")) {
                ArrayList<Point> points = parsePoints(task.getValue().trim());
                if (securityCheck(points)) {
                    mijnlieff.openWindow("/mijnlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(mijnlieff, client, parsePoints(task.getValue().trim()), false, task.getValue().trim()));
                } else {
                    mijnlieff.openWindow("/mijnlieff/GameFinished/GameFinished.fxml", new GameFinishedCompanion(mijnlieff, client, "Your opponent has send an invalid board"));
                }
            } else {
                mijnlieff.openWindow("/mijnlieff/GameFinished/GameFinished.fxml", new GameFinishedCompanion(mijnlieff, client, "Your opponent left"));
            }
        }
    }



}

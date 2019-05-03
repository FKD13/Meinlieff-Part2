package Meinlieff.WaitScreen.WaitGameStartScreen;

import Meinlieff.BoardPicker.GameBoardCompanionInitializer;
import Meinlieff.Companion;
import Meinlieff.GameBoard.GameBoardCompanion;
import Meinlieff.GameBoard.Point;
import Meinlieff.GameFinished.GameFinishedCompanion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.WaitTask;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Worker;

import java.util.ArrayList;

public class WaitGameStartScreenCompanion extends GameBoardCompanionInitializer implements Companion {

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
            System.out.println(task.getValue().trim());
            ArrayList<Point> points = parsePoints(task.getValue().trim());
            if (securityCheck(points)) {
                main.openWindow("/Meinlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(main, client, parsePoints(task.getValue().trim()), false, task.getValue().trim()));
            } else {
                main.openWindow("/Meinlieff/GameFinished/GameFinished.fxml", new GameFinishedCompanion(main, client, "Your opponent has send an invalid board"));
            }
        }
    }



}

package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ServerClient.Player;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ArrayList;

public class QueueTaskListener implements InvalidationListener {

    private MainMenuCompanion mainMenuCompanion;
    private Task<ArrayList<Player>> task;

    public QueueTaskListener(MainMenuCompanion mainMenuCompanion) {
        this.mainMenuCompanion = mainMenuCompanion;
    }

    public void setTask(Task<ArrayList<Player>> task) {
        this.task = task;
    }

    @Override
    public void invalidated(Observable observable) {
        if (task.getState() == Worker.State.SUCCEEDED) {
            mainMenuCompanion.updateListView(task.getValue());
        }
    }
}

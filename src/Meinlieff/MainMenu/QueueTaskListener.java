package Meinlieff.MainMenu;

import Meinlieff.ServerClient.Player;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;

import java.util.ArrayList;

public class QueueTaskListener implements InvalidationListener {

    private MainMenuCompanion mainMenuCompanion;

    public QueueTaskListener(MainMenuCompanion mainMenuCompanion) {
        this.mainMenuCompanion = mainMenuCompanion;
    }

    @Override
    public void invalidated(Observable observable) {
        if (((Property) observable).getBean() != null) {
            Task<ArrayList<Player>> task = (Task<ArrayList<Player>>) ((Property) observable).getBean();
            if (task.getState() == Worker.State.SUCCEEDED) {
                mainMenuCompanion.updateListView(task.getValue());
            }
        }
    }
}

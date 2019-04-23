package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.ResponseWaiter;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Worker;

public class AwaitResponseTaskListener implements InvalidationListener {

    private ResponseWaiter responseWaiter;
    private AwaitResponseTask task;

    public AwaitResponseTaskListener(ResponseWaiter responseWaiter, AwaitResponseTask task) {
        this.responseWaiter = responseWaiter;
        this.task = task;
        task.stateProperty().addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        if (task.getState() == Worker.State.SUCCEEDED) {
            responseWaiter.onResponse(this);
        }
    }

    public AwaitResponseTask getTask() {
        return task;
    }
}

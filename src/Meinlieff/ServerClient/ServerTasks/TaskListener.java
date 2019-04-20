package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.Companion;
import javafx.beans.InvalidationListener;
import javafx.concurrent.Task;

public abstract class TaskListener implements InvalidationListener {

    protected Companion companion;
    protected Task task;

    protected TaskListener(Companion companion) {
        this.companion = companion;
        this.task = null;
    }
}

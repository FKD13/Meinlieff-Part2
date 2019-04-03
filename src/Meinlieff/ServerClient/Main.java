package Meinlieff.ServerClient;

import com.sun.javafx.tk.Toolkit;
import javafx.application.Application;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.concurrent.Task;
import javafx.stage.Stage;

import java.util.ArrayList;

public class Main extends Application implements InvalidationListener {

    private Task<ArrayList<Player>> task;
    @Override
    public void start(Stage stage) throws Exception {
        task = new Client("localhost", 4444).getQueque();
        new Thread(task).start();
        task.stateProperty().addListener(this);
    }

    @Override
    public void invalidated(Observable observable) {
        System.out.println(task.getValue());
    }

    public static void main(String[] args) {
        launch(args);
    }
}

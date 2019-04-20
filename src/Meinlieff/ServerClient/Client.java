package Meinlieff.ServerClient;

import Meinlieff.Companion;
import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import Meinlieff.ServerClient.ServerTasks.QueueTask;
import Meinlieff.ServerClient.ServerTasks.QueueTaskListener;
import javafx.beans.InvalidationListener;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Client {

    private String name = "Client";

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public boolean connect(String host, int port) {
        try {
            socket = new Socket(host, port);
            try {
                out = new PrintWriter(socket.getOutputStream(), true);
                try {
                    in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                } catch (IOException e) {
                    printError(e);
                    out.close();
                    try {
                        socket.close();
                    } catch (IOException ex) {
                        printError(ex);
                    }
                    return false;
                }
            } catch (IOException e) {
                printError(e);
                try {
                    socket.close();
                } catch (IOException ex) {
                    printError(ex);
                }
                return false;
            }
        } catch (IOException e) {
            printError(e);
            return false;
        }
        System.out.println("connected!");
        return true;
    }

    public boolean login(String username) {
        String answer = "";
        try {
            out.println("I " + username);
            answer = in.readLine();
        } catch (IOException e) {
            printError(e);
        }
        System.out.println("login!");
        return answer.startsWith("+");
    }

    public Task<ArrayList<Player>> getQueue(QueueTaskListener queueTaskListener) {
        Task<ArrayList<Player>> task = new QueueTask(out, in);
        task.stateProperty().addListener(queueTaskListener);
        queueTaskListener.setTask(task);
        return task;
    }

    public AwaitResponseTask getAwaitResponseTask(String line) {
        AwaitResponseTask task = new AwaitResponseTask(out, in, line);
        return task;
    }

    private void printError(Exception e) {
        System.err.println("[" + name + "] " + e.getCause());
    }
}
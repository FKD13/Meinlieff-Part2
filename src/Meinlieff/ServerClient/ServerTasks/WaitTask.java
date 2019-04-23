package Meinlieff.ServerClient.ServerTasks;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class WaitTask extends Task<String> {

    private PrintWriter out;
    private BufferedReader in;

    public WaitTask(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    @Override
    protected String call() {
        String line = "";
        try {
            line = in.readLine();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return line;
    }
}

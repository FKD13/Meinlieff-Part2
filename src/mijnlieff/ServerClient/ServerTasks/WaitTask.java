package mijnlieff.ServerClient.ServerTasks;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;

public class WaitTask extends Task<String> {

    private BufferedReader in;

    public WaitTask(BufferedReader in) {
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

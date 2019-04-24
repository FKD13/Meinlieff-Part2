package Meinlieff.ServerClient.ServerTasks;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class AwaitResponseTask extends Task<String> {

    private PrintWriter out;
    private BufferedReader in;
    private String line;

    public AwaitResponseTask(PrintWriter out, BufferedReader in, String line) {
        this.out = out;
        this.in = in;
        this.line = line;
    }

    public String getLine() {
        return line;
    }

    public void setLine(String line) {
        this.line = line;
    }

    public void writeLine() {
        out.println(line);
    }

    @Override
    protected String call() {
        String response = "";
        out.println(line);
        try {
            response = in.readLine();
        } catch (IOException e) {
            System.err.println("[AwaitResponseTask] an IOException while reading from BufferedReader");
        }
        System.err.println(response);
        return response;
    }
}

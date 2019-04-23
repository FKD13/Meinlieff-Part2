package Meinlieff.ServerClient.ServerTasks;

import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class LoginTask extends Task<Boolean> {

    private PrintWriter out;
    private BufferedReader in;
    private String username;

    public LoginTask(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    protected Boolean call() throws Exception {
        String answer = "";
        try {
            out.println("I " + username);
            answer = in.readLine();
        } catch (IOException e) {
            System.err.println("[LoginTask] " + e.toString());
        }
        System.out.println("login!");
        return answer.startsWith("+");
    }
}

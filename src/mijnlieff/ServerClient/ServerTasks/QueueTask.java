package mijnlieff.ServerClient.ServerTasks;

import mijnlieff.ServerClient.Player;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;

public class QueueTask extends Task<ArrayList<Player>> {

    private PrintWriter out;
    private BufferedReader in;

    public QueueTask(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    @Override
    protected ArrayList<Player> call() {
        ArrayList<Player> players = new ArrayList<>();
        out.println("W");
        try {
            String line = in.readLine().trim();
            while (!line.equals("+")) {
                players.add(parsePlayer(line));
                line = in.readLine();
            }
        } catch (IOException e) {
            System.err.println("[QueueTask] " + e.getCause());
        }
        return players;
    }

    private Player parsePlayer(String line) {
        return new Player(line.replace("+ ", ""));
    }
}

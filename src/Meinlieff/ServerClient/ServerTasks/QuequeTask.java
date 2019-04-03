package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.ServerClient.Player;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;

public class QuequeTask extends Task<ArrayList<Player>> {

    private PrintWriter out;
    private BufferedReader in;

    public QuequeTask(PrintWriter out, BufferedReader in) {
        this.out = out;
        this.in = in;
    }

    @Override
    protected ArrayList<Player> call() throws Exception {
        ArrayList<Player> players = new ArrayList<>();
        out.println("W");
        String line = in.readLine().trim();
        while (!line.equals("+")) {
            players.add(parsePlayer(line));
            line = in.readLine().trim();
        }
        return players;
    }

    private Player parsePlayer(String line) {
        return new Player(line.split(" ")[1]);
    }
}

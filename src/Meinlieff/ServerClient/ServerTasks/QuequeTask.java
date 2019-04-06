package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.ServerClient.Player;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class QuequeTask extends Task<ArrayList<Player>> {

    private String host;
    private int port;
    private String name = "QuequeTask";

    public QuequeTask(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected ArrayList<Player> call() {
        ArrayList<Player> players = new ArrayList<>();
        try (Socket socket = new Socket(host, port);
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream())))
        {
            out.println("I QuequeClient");
            if (in.readLine().trim().equals("-")) {
                System.err.println("[" + name + "] can't login");
                return null;
            }
            out.println("W");
            String line = in.readLine().trim();
            while (!line.equals("+")) {
                players.add(parsePlayer(line));
                line = in.readLine().trim();
            }
            out.println("Q\n");
        } catch (UnknownHostException e) {
            System.err.println("[" + name + "] Host not found: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[" + name + "] IOException: "  + e.getMessage());
        }
        return players;
    }

    private Player parsePlayer(String line) {
        return new Player(line.split(" ")[1]);
    }
}

package Meinlieff.ServerClient;

import Meinlieff.ServerClient.ServerTasks.QuequeTask;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void connect(String host, int port) {
        // if (socket.isConnected()) {
        //     disconnect();
        // }
        System.out.println("connect");
        try {
            socket = new Socket(host, port);
            out = new PrintWriter(socket.getOutputStream(), true);
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (UnknownHostException e) {
            System.err.println("[Client] " + e.getMessage());
        } catch (IOException e) {
            System.err.println("[Client] " + e.getMessage());
            disconnect();
        }
    }

    public boolean login(String name) throws Exception {
        out.println("I " + name);
        return in.readLine().trim().equals("+");
    }

    public void disconnect() {
        try {
            socket.close();
        } catch (IOException e) {
            System.err.println("[Client] Error closing socket");
        }
        out.close();
        try {
            in.close();
        } catch (IOException e) {
            System.err.println("[Client] Error closing BufferedReader in");
        }
    }

    // public Task<ArrayList<Player>> getQueque() {
    //     return new QuequeTask(out, in);
    // }
}
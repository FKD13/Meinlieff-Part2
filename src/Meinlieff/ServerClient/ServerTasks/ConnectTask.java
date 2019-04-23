package Meinlieff.ServerClient.ServerTasks;

import Meinlieff.ServerClient.Client;
import javafx.concurrent.Task;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class ConnectTask extends Task<Boolean> {

    private Client client;
    private String host;
    private int port;

    public ConnectTask(Client client) {
        this.client = client;
    }

    public void setAdress(String host, int port) {
        this.host = host;
        this.port = port;
    }

    @Override
    protected Boolean call() throws Exception {
        PrintWriter out = null;
        BufferedReader in = null;
        Socket socket = null;
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
        client.setFields(socket, out, in);
        System.out.println("connected!");
        return true;
    }

    private void printError(IOException e) {
        System.err.println("[ConnectTask] " + e.toString());
    }
}

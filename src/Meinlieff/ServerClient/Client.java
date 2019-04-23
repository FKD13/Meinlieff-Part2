package Meinlieff.ServerClient;

import Meinlieff.ServerClient.ServerTasks.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private String name = "Client";

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public ConnectTask getConnectTask() {
        return new ConnectTask(this);
    }

    public LoginTask getLoginTask() {
        return new LoginTask(out, in);
    }

    public WaitTask getWaitTask() {return new WaitTask(out, in); }

    public void setFields(Socket socket, PrintWriter out, BufferedReader in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    /*
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
     */

    public void disconnect() {
        if (socket != null && socket.isConnected()) {
            out.close();
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("[Client] an error closing the socket, already Closed?");
            }
            try {
                in.close();
            } catch (IOException e) {
                System.err.println("[Client] an error closing in chanel");
            }
            System.err.println("[Client] closing connection");
        }
    }

    public QueueTask getQueueTask() {
        return new QueueTask(out, in);
    }

    public AwaitResponseTask getAwaitResponseTask(String line) {
        AwaitResponseTask task = new AwaitResponseTask(out, in, line);
        return task;
    }

    private void printError(Exception e) {
        System.err.println("[" + name + "] " + e.getCause());
    }
}
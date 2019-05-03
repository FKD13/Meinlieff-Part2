package mijnlieff.ServerClient;

import mijnlieff.ServerClient.ServerTasks.*;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;

public class Client {

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public void sendLine(String line) {
        out.println(line);
    }

    public ConnectTask getConnectTask() {
        return new ConnectTask(this);
    }

    public LoginTask getLoginTask() {
        return new LoginTask(out, in);
    }

    public WaitTask getWaitTask() {return new WaitTask(in); }

    public void setFields(Socket socket, PrintWriter out, BufferedReader in) {
        this.socket = socket;
        this.out = out;
        this.in = in;
    }

    public void disconnect() {
        if (socket != null && socket.isConnected()) {
            out.println("Q");
            out.close();
            try {
                in.close();
            } catch (IOException e) {
                System.err.println("[Client] an error closing in chanel");
            }
            try {
                socket.close();
            } catch (IOException e) {
                System.err.println("[Client] an error closing the socket, already Closed?");
            }
        }
    }

    public QueueTask getQueueTask() {
        return new QueueTask(out, in);
    }

    public AwaitResponseTask getAwaitResponseTask(String line) {
        return new AwaitResponseTask(out, in, line);
    }
}
package mijnlieff.Part1;

import mijnlieff.GameBoard.Move;
import mijnlieff.GameBoard.Piece;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.HashMap;

public class PartOneClient {

    private HashMap<Character, Piece> map = new HashMap<>();
    private String host;
    private int port;
    private boolean connected;

    private Socket socket;
    private PrintWriter out;
    private BufferedReader in;

    public PartOneClient(String host, int port) {
        map.put('+', Piece.TOWER);
        map.put('X', Piece.RUNNER);
        map.put('o', Piece.PULLER);
        map.put('@', Piece.PUSHER);
        this.host = host;
        this.port = port;
        this.connected = false;
    }

    public boolean connect() {
        try {
            socket = new Socket(host, port);
        } catch (IOException e) {
            System.err.println("[Client] could not connect to " + host + ":" + port);
            return false;
        }
        try {
            out = new PrintWriter(socket.getOutputStream(), true);
        } catch (IOException e) {
            System.err.println("[Client] error opening outputStream, closing socket...");
            try {
                socket.close();
            } catch (IOException io) {
                System.err.println("[Client] error closing the socket");

            }
            return false;
        }
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        } catch (IOException e) {
            System.err.println("[Client] error opening inputStream, closing outputStream and socket...");
            out.close();
            try {
                socket.close();
            } catch (IOException io) {
                System.err.println("[Client] error closing the socket");
            }
            return false;
        }
        connected = true;
        return true;
    }

    public String sendRequest(String request) {
        out.println(request);
        String response = null;
        try {
            response = in.readLine().trim();
        } catch (IOException e) {
            disconnect();
        }
        return response;
    }

    public void disconnect() {
        if (connected) {
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
            connected = false;
        }

    }

    public boolean isConnected() {
        return connected;
    }

    public Move parseMove(String line) {
        String[] data = line.split(" ");
        Piece piece = map.get(data[4]);
        return new Move().setData(Integer.parseInt(data[2]), Integer.parseInt(data[3]), piece, data[1].equals("T"));
    }
}

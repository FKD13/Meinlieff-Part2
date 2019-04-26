package Meinlieff.GameBoard;

import java.util.HashMap;

public class Move {

    private HashMap<String, Piece> map = new HashMap<>();
    private int x;
    private int y;
    private boolean isFinal;
    private Piece piece;

    public Move(String line) {
        map.put("+", Piece.TOWER);
        map.put("X", Piece.RUNNER);
        map.put("o", Piece.PULLER);
        map.put("@", Piece.PUSHER);
        String[] move = line.split(" ");
        this.x = Integer.parseInt(move[2]);
        this.y = Integer.parseInt(move[3]);
        this.piece = map.get(move[4]);
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isFinal() {
        return isFinal;
    }

    public Piece getPiece() {
        return piece;
    }
}

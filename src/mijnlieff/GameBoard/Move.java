package mijnlieff.GameBoard;

import java.util.HashMap;

public class Move {

    private HashMap<String, Piece> String_Piece = new HashMap<>();
    private HashMap<Piece, String> Piece_String = new HashMap<>();

    private int x;
    private int y;
    private boolean isFinal;
    private Piece piece;

    public Move() {
        String_Piece.put("+", Piece.TOWER);
        String_Piece.put("X", Piece.RUNNER);
        String_Piece.put("o", Piece.PULLER);
        String_Piece.put("@", Piece.PUSHER);

        Piece_String.put(Piece.TOWER, "+");
        Piece_String.put(Piece.RUNNER, "X");
        Piece_String.put(Piece.PULLER, "o");
        Piece_String.put(Piece.PUSHER, "@");
        x = -1;
        y = -1;
        isFinal = false;
        piece = Piece.NULL;
    }

    public Move setData(String line) {
        String[] move = line.split(" ");
        if (move.length == 5){
            this.x = Integer.parseInt(move[2]);
            this.y = Integer.parseInt(move[3]);
            this.piece = String_Piece.get(move[4]);
            isFinal = move[1].equals("T");
        }
        return this;
    }

    public Move setData(int x, int y, Piece piece, boolean isFinal) {
        this.x = x;
        this.y = y;
        this.piece = piece;
        this.isFinal = isFinal;
        return this;
    }

    public String toString() {
        if (piece != Piece.EMPTY) {
            return "X " + (isFinal ? "T" : "F") + " " + x + " " + y + " " + Piece_String.get(piece);
        } else {
            return "X";
        }
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

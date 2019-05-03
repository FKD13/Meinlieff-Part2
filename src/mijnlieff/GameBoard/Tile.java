package mijnlieff.GameBoard;

public class Tile {

    private Piece piece;
    private boolean color;

    public Tile(Piece piece) {
        this.piece = piece;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setColor(boolean b) {
        this.color = b;
    }

    public boolean getColor() {
        return color;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }
}

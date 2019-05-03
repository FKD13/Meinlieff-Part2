package mijnlieff.Part1.Updated;

import mijnlieff.GameBoard.Piece;
import mijnlieff.Model;
import javafx.application.Platform;

public class PartOneModel extends Model {

    private boolean color = true;

    public void setTile(int x, int y, Piece p) {
        int i = 0;
        while (i < 8 && getSideTile(i, color).getPiece() != p) {
            i++;
        }
        if (i == 8) {
            System.err.println("got invalid move from server");
            Platform.exit();
        } else {
            getSideTile(i, color).setPiece(Piece.NULL);
            tiles[x][y].setPiece(p);
            tiles[x][y].setColor(color);
            color = !color;
            fireInvalidationEvent();
        }
    }

    public void unsetTile(int x, int y) {
        Piece p = getTile(x, y).getPiece();
        color = ! color;
        int i = 0;
        while (i < 8 && getSideTile(i, color).getPiece() != Piece.NULL) {
            i++;
        }
        if (i == 8) {
            // should not happen
            System.err.println("I can't do this");
        } else {
            getSideTile(i, color).setPiece(p);
            tiles[x][y].setPiece(Piece.EMPTY);
            tiles[x][y].setColor(color);
            fireInvalidationEvent();
        }
    }
}

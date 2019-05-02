package Meinlieff.Part1.Updated;

import Meinlieff.GameBoard.Piece;
import Meinlieff.GameBoard.Tile;
import javafx.application.Platform;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class PartOneModel implements Observable {

    private ArrayList<InvalidationListener> listeners = new ArrayList<>();
    private Tile[][] tiles;
    private Tile[] blackside;
    private Tile[] whiteside;
    private boolean color = true;

    public void setSide(boolean b, Tile[] t) {
        if (b) {
            whiteside = t;
        } else {
            blackside = t;
        }
        fireInvalidationEvent();
    }

    public Tile getSide(boolean b, int nr) {
        if (b) {
            return whiteside[nr];
        } else {
            return blackside[nr];
        }
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
        fireInvalidationEvent();
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTile(int x, int y, Piece p) {
        int i = 0;
        while (i < 8 && getSide(color, i).getPiece() != p) {
            i++;
        }
        if (i == 8) {
            System.err.println("got invalid move from server");
            Platform.exit();
        } else {
            getSide(color, i).setPiece(Piece.NULL);
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
        while (i < 8 && getSide(color, i).getPiece() != Piece.NULL) {
            i++;
        }
        if (i == 8) {
            // should not happen
            System.err.println("I can't do this");
        } else {
            getSide(color, i).setPiece(p);
            tiles[x][y].setPiece(Piece.EMPTY);
            tiles[x][y].setColor(color);
            fireInvalidationEvent();
        }
    }

    @Override
    public void addListener(InvalidationListener invalidationListener) {
        listeners.add(invalidationListener);
    }

    @Override
    public void removeListener(InvalidationListener invalidationListener) {
        listeners.remove(invalidationListener);
    }

    private void fireInvalidationEvent() {
        for (InvalidationListener listener: listeners) {
            listener.invalidated(this);
        }
    }
}

package Meinlieff.Part1.Updated;

import Meinlieff.GameBoard.Piece;
import Meinlieff.GameBoard.Tile;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class PartOneModel implements Observable {

    private ArrayList<InvalidationListener> listeners = new ArrayList<>();
    private Tile[][] tiles;
    private boolean color = true;

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
        fireInvalidationEvent();
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTile(int x, int y, Piece p) {
        tiles[x][y].setPiece(p);
        tiles[x][y].setColor(color);
        color = ! color;
        fireInvalidationEvent();
    }

    public void unsetTile(int x, int y) {
        color = ! color;
        tiles[x][y].setPiece(Piece.EMPTY);
        tiles[x][y].setColor(color);
        fireInvalidationEvent();
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

package mijnlieff;

import mijnlieff.GameBoard.Tile;
import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public abstract class Model implements Observable {

    protected Tile[][] tiles;
    protected Tile[] white_side;
    protected Tile[] black_side;
    private ArrayList<InvalidationListener> listeners = new ArrayList<>();

    public Tile getSideTile(int nr, boolean color) {
        if (color) {
            return white_side[nr];
        } else {
            return black_side[nr];
        }
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public void setTiles(Tile[][] tiles, Tile[] white_side, Tile[] black_side) {
        this.tiles = tiles;
        this.white_side = white_side;
        this.black_side = black_side;
        fireInvalidationEvent();
    }

    protected void fireInvalidationEvent() {
        for (InvalidationListener listener : listeners) {
            listener.invalidated(this);
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
}

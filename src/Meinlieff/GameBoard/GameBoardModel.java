package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class GameBoardModel implements Observable {

    private Tile[][] tiles;
    private ArrayList<InvalidationListener> listeners;

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
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

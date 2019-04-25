package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class GameBoardModel implements Observable {

    private Tile[][] tiles;
    private ArrayList<InvalidationListener> listeners;
    private Tile selectedTile;

    public GameBoardModel() {
        listeners = new ArrayList<>();
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void setTiles(Tile[][] tiles) {
        this.tiles = tiles;
        fireInvalidationEvent();
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    private void fireInvalidationEvent() {
        for (InvalidationListener listener: listeners) {
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

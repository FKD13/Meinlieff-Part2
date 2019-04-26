package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class GameBoardModel implements Observable {

    private Tile[][] tiles;
    private Tile[] white_side;
    private Tile[] black_side;

    private ArrayList<InvalidationListener> listeners;
    private Tile selectedTile;
    private boolean playerColor;

    public GameBoardModel(boolean playerColor) {
        this.playerColor = playerColor;
        listeners = new ArrayList<>();
    }

    public boolean getPlayerColor() {
        return playerColor;
    }

    public Tile getSelectedTile() {
        return selectedTile;
    }

    public void setSelectedTile(Tile selectedTile) {
        this.selectedTile = selectedTile;
    }

    public void setTiles(Tile[][] tiles, Tile[] white_side, Tile[] black_side) {
        this.tiles = tiles;
        this.white_side = white_side;
        this.black_side = black_side;
        fireInvalidationEvent();
    }

    public Tile getTile(int x, int y) {
        return tiles[x][y];
    }

    public Tile getSideTile(int nr, boolean color) {
        if (color) {
            return white_side[nr];
        } else {
            return black_side[nr];
        }
    }

    public void setTile(int x, int y, Tile tile) {
        tiles[x][y].setPiece(tile.getPiece());
        tiles[x][y].setColor(tile.getColor());
        tile.setPiece(Piece.NULL);
        fireInvalidationEvent();
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

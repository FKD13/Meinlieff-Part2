package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import java.util.ArrayList;

public class GameBoardModel implements Observable {

    private Tile[][] tiles;
    private Tile[] white_side;
    private Tile[] black_side;

    private ArrayList<InvalidationListener> listeners;
    private Move previousMove = new Move().setData(0, 0, Piece.EMPTY, false);
    private Tile selectedTile;
    private boolean playerColor;
    private boolean canMove;
    private boolean gameEnd;

    public GameBoardModel(boolean playerColor) {
        this.gameEnd = false;
        this.playerColor = playerColor;
        listeners = new ArrayList<>();
    }

    // some simple getter end setters
    public boolean isGameEnd() {
        return gameEnd;
    }

    public void setGameEnd(boolean gameEnd) {
        this.gameEnd = gameEnd;
    }

    public boolean CanMove() {
        return canMove;
    }

    public void setCanMove(boolean canMove) {
        this.canMove = canMove;
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

    public Move getPreviousMove() {
        return previousMove;
    }

    public void setPreviousMove(Move previousMove) {
        this.previousMove = previousMove;
    }

    // set the board and sidepanes
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

    // set a tile on the board
    public boolean setTile(int x, int y, Tile tile) {
        if (validatePosition(x, y)) {
            previousMove = new Move().setData(x, y, tile.getPiece(), false);
            tiles[x][y].setPiece(tile.getPiece());
            tiles[x][y].setColor(tile.getColor());
            tile.setPiece(Piece.NULL);
            fireInvalidationEvent();
            return true;
        } else {
            return false;
        }
    }

    public boolean isFinalMove() {
        if (playerColor) {
            return isFinal(white_side);
        } else {
            return isFinal(black_side);
        }
    }

    private boolean isFinal(Tile[] tiles) {
        int count = 0;
        for (Tile t : tiles) {
            if (t.getPiece() != Piece.NULL) {
                count += 1;
            }
        }
        return count == 0;
    }

    public boolean validatePosition(int x, int y) {
        return tiles[x][y].getPiece() == Piece.EMPTY && previousMove.getPiece().isValidPosition(previousMove.getX(), previousMove.getY(), x, y);
    }

    private void fireInvalidationEvent() {
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

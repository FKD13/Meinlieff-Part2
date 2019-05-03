package Meinlieff.Part1.Updated;

import Meinlieff.Companion;
import Meinlieff.GameBoard.Move;
import Meinlieff.GameBoard.Piece;
import Meinlieff.GameBoard.Tile;
import Meinlieff.Part1.PartOneClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PartOneCompanion implements Companion {

    private PartOneClient client;
    private PartOneModel model;
    private ArrayList<Move> moves;
    private int index = 0;

    @FXML
    public Button undoAll;
    @FXML
    public Button undoOne;
    @FXML
    public Button doOne;
    @FXML
    public Button doAll;
    @FXML
    public GridPane gridPane;
    @FXML
    public VBox whiteSide;
    @FXML
    public VBox blackSide;

    public PartOneCompanion(String host, int port) {
        moves = new ArrayList<>();
        model = new PartOneModel();
        client = new PartOneClient(host, port);
        if (client.connect()) {
            fetchAllMoves();
        }
    }

    private void fetchAllMoves() {
        moves = new ArrayList<>();
        Move move = new Move().setData(client.sendRequest("X"));
        if (move != null) {
            moves.add(move);
            while (!move.isFinal()) {
                move = new Move().setData(client.sendRequest("X"));
                if (move != null) {
                    moves.add(move);
                }
            }
        }
    }

    public void initialize() {
        doAll.setOnAction(e -> doAllMoves());
        doOne.setOnAction(e -> doOneMove());
        undoAll.setOnAction(e -> undoAllMoves());
        undoOne.setOnAction(e -> undoOneMove());
        Tile[][] tiles = new Tile[4][4];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                BoardTileImageView boardTileImageView = new BoardTileImageView(200, 200, i, j);
                gridPane.add(boardTileImageView, j, i);
                Tile tile = new Tile(Piece.EMPTY);
                tiles[i][j] = tile;
                model.addListener(boardTileImageView);
            }
        }
        model.setTiles(tiles);
        model.setSide(true, fill_sidePane(true, model));
        model.setSide(false, fill_sidePane(false, model));
        disableButtons();
    }

    private Tile[] fill_sidePane(boolean color, PartOneModel model) {
        Tile[] tiles = new Tile[8];
        tiles[0] = new Tile(Piece.PULLER);
        tiles[1] = new Tile(Piece.PULLER);
        tiles[2] = new Tile(Piece.PUSHER);
        tiles[3] = new Tile(Piece.PUSHER);
        tiles[4] = new Tile(Piece.TOWER);
        tiles[5] = new Tile(Piece.TOWER);
        tiles[6] = new Tile(Piece.RUNNER);
        tiles[7] = new Tile(Piece.RUNNER);
        for (int i = 0; i < 8; i++) {
            SideTileImageView view = new SideTileImageView(100, 100, color, i);
            model.addListener(view);
            if (color) {
                whiteSide.getChildren().add(view);
            } else {
                blackSide.getChildren().add(view);
            }
        }
        return tiles;
    }

    public void prepareScreenshot() {
        doAllMoves();
    }

    private void doAllMoves() {
        while (doOneMove()) {}
    }

    private boolean doOneMove() {
        Move move = moves.get(index);
        model.setTile(move.getY(), move.getX(), move.getPiece());
        index++;
        disableButtons();
        return moves.size() != index;
    }

    private boolean undoOneMove() {
        index--;
        Move move = moves.get(index);
        model.unsetTile(move.getY(), move.getX());
        disableButtons();
        return index != 0;
    }

    private void undoAllMoves() {
        while (undoOneMove()) {}
    }

    private void disableButtons() {
        doOne.setDisable(false);
        undoOne.setDisable(false);
        doAll.setDisable(false);
        undoAll.setDisable(false);

        if (index == 0) {
            undoOne.setDisable(true);
            undoAll.setDisable(true);
        }

        if (index == moves.size()) {
            doAll.setDisable(true);
            doOne.setDisable(true);
        }
    }
}

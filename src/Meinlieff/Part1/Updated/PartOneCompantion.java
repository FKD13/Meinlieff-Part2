package Meinlieff.Part1.Updated;

import Meinlieff.GameBoard.Move;
import Meinlieff.GameBoard.Piece;
import Meinlieff.GameBoard.Tile;
import Meinlieff.Part1.PartOneClient;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

//todo sidepanes, disable right buttons
public class PartOneCompantion {

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

    public PartOneCompantion(String host, int port) {
        model = new PartOneModel();
        client = new PartOneClient(host, port);
        client.connect();
        fetchAllMoves();
    }

    private void fetchAllMoves() {
        moves = new ArrayList<>();
        Move move = new Move().setData(client.sendRequest("X"));
        if (move != null) {
            moves.add(move);
            while (! move.isFinal()) {
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
    }

    private void doAllMoves() {
        while (doOneMove()) {}
    }

    private boolean doOneMove() {
        Move move = moves.get(index);
        model.setTile(move.getX(), move.getY(), move.getPiece());
        index++;
        return moves.size() != index;
    }

    private boolean undoOneMove() {
        index--;
        Move move = moves.get(index);
        model.unsetTile(move.getX(), move.getY());
        return index != 0;
    }

    private void undoAllMoves() {
        while (undoOneMove()) {}
    }
}

package Meinlieff.Part1.old;

import Meinlieff.GameBoard.GameBoardModel;
import Meinlieff.GameBoard.Move;
import Meinlieff.GameBoard.Piece;
import Meinlieff.GameBoard.Tile;
import Meinlieff.Part1.PartOneClient;
import Meinlieff.Part1.Updated.BoardTileImageView;
import Meinlieff.Part1.Updated.SideTileImageView;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class PartOneGameBoardCompanion {

    private GameBoardModel boardModel;
    private ArrayList<Move> steps = new ArrayList<>();
    private PartOneClient client;
    private int move = 0;

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


    public PartOneGameBoardCompanion(String host, int port) {
        client = new PartOneClient(host, port);
        boolean succes = client.connect();
        if (succes) {
            fetchStep();
        }
    }

    public void disconnectClient() {
        client.disconnect();
    }

    public void fetchStep() {
        if (client.isConnected()){

            String answer = client.sendRequest("X");
            if (answer != null) {
                steps.add(client.parseMove(answer));
            }
        }
    }

    public void initialize() {
        // create the BoardModel
        Tile[][] tiles = new Tile[4][4];
        boardModel = new GameBoardModel(false);

        // add Tileimageviews to gridpane
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                Tile tile = new Tile(Piece.EMPTY);
                BoardTileImageView boardTileImageView = new BoardTileImageView(200, 200, i, j);
                gridPane.add(boardTileImageView, i, j);
                tiles[i][j] = tile;
                boardModel.addListener(boardTileImageView);
            }
        }
        // add TileImageViews to sideBoards
        Tile[] whiteSideTiles = initialze_sideBoard(true, whiteSide, boardModel);
        Tile[] blackSideTiles = initialze_sideBoard(false, blackSide, boardModel);

        // pass tiles to BoardModel
        boardModel.setSideTiles(whiteSideTiles, blackSideTiles);
        boardModel.setTiles(tiles);

        // disable the right buttons
        if (move == 0 ) {
            undoAll.setDisable(true);
            undoOne.setDisable(true);
        }
        if (move == steps.size()) {
            doOne.setDisable(true);
            doAll.setDisable(true);
        }
    }

    private Tile[] initialze_sideBoard(boolean color, VBox sideBoard, GameBoardModel boardModel) {
        Piece[] pieces = new Piece[]{
                Piece.PUSHER,
                Piece.PULLER,
                Piece.RUNNER,
                Piece.TOWER
        };
        Tile[] tiles = new Tile[8];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 2; j++) {
                Tile tile = new Tile(Piece.EMPTY);
                tile.setColor(color);
                tile.setPiece(pieces[i]);
                tiles[i * 2 + j] = tile;
                SideTileImageView sideTileImageView = new SideTileImageView(100,100, color, i * 2 + j);
                sideBoard.getChildren().add(sideTileImageView);
                boardModel.addListener(sideTileImageView);
            }
        }
        return tiles;
    }

    public void undoAllMoves() {
        while (move != 0) {
            undoOneMove();
        }
    }

    public void undoOneMove() {
        if (move > 0) {
            move--;
            boardModel.moveDone();
            boardModel.setBoardPiece(steps.get(move).getX(), steps.get(move).getY(), Piece.EMPTY);
            boardModel.setSidePiece(Piece.EMPTY, steps.get(move).getPiece());
            doOne.setDisable(false);
            doAll.setDisable(false);
        }
        if (move == 0 ) {
            undoAll.setDisable(true);
            undoOne.setDisable(true);
        }
    }

    public void doOneMove() {
        if (move < steps.size()) {
            boardModel.setSidePiece(steps.get(move).getPiece(), Piece.EMPTY);
            boardModel.setBoardPiece(steps.get(move).getX(), steps.get(move).getY(), steps.get(move).getPiece());
            move++;
            undoAll.setDisable(false);
            undoOne.setDisable(false);
            boardModel.moveDone();
            if (move == steps.size()) {
                fetchStep();
            }
        }
        if (move == steps.size()) {
            doOne.setDisable(true);
            doAll.setDisable(true);
        }

    }

    public void doAllMoves() {
        while (move < steps.size()) {
            doOneMove();
        }
    }
}
}

package Meinlieff.GameBoard;

import Meinlieff.Companion;
import Meinlieff.GameFinished.GameFinishedCompanion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

/*
todo:
    - detect the end of the game
    - calculate score
    - implement part 1 again :(
    - lots of testing :)
 */
public class GameBoardCompanion implements Companion {

    private Client client;
    private ArrayList<Point> boardconfiguration;
    private String board;
    private boolean color;
    private GameBoardModel boardModel;
    private Main main;

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane white_sidePane;
    @FXML
    public GridPane black_sidePane;

    public GameBoardCompanion(Main main, Client client, ArrayList<Point> boardconfiguration, boolean color, String board) {
        this.main = main;
        this.client = client;
        this.boardconfiguration = boardconfiguration;
        this.color = color;
        this.board = board;
    }

    public void initialize() {
        colorGameBoard();
        Point dimension = getDimension();
        initialize_gridPane(dimension);

        // calculate the size of the tiles
        double size = 800 / (dimension.getY() + 1);
        if (dimension.getX() > dimension.getY()) {
            size = 800 / (dimension.getX() + 1);
        }
        boardModel = new GameBoardModel(color);
        Tile[][] tiles = new Tile[dimension.getX() + 1][dimension.getY() + 1];
        for (int i = 0; i <= dimension.getX(); i++) {
            for (int j = 0; j <= dimension.getY(); j++) {
                tiles[i][j] = new Tile(Piece.NULL);
            }

        }
        for (Point p : boardconfiguration) {
            tiles[p.getX()][p.getY()] = new Tile(Piece.EMPTY);
        }
        for (int i = 0; i <= dimension.getX(); i++) {
            for (int j = 0; j <= dimension.getY(); j++) {
                TileImageView tileImageView = new TileImageView(null, i, j, boardModel, this);
                tileImageView.setFitHeight(size);
                tileImageView.setFitWidth(size);
                gridPane.add(tileImageView, j, i);
            }
        }
        boardModel.setTiles(tiles, fill_sidePane(true, boardModel), fill_sidePane(false, boardModel));
        boardModel.setCanMove(true);
        if (color) {
            sendServerMove(board);
        }
    }

    private Tile[] fill_sidePane(boolean color, GameBoardModel model) {
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
            SideTileImageView view = new SideTileImageView(i, color, model);
            if (color) {
                white_sidePane.add(view, 0, i);
            } else {
                black_sidePane.add(view, 0, i);
            }
            tiles[i].setColor(color);
        }
        return tiles;
    }

    private void initialize_gridPane(Point dimension) {
        // initalize central gridpane

        for (int i = 0; i <= dimension.getX(); i++) {
            gridPane.addColumn(i);
        }
        for (int i = 0; i <= dimension.getY(); i++) {
            gridPane.addRow(i);
        }
        //adjust the size of the gridpane to the dimension of the field
        if (dimension.getX() > dimension.getY()) {
            gridPane.setPrefHeight(Math.ceil(800/(dimension.getX() + 1)) * (dimension.getY() + 1));
        } else {
            gridPane.setPrefHeight(Math.ceil(800/(dimension.getY() + 1)) * (dimension.getX() + 1));
        }
    }

    private void colorGameBoard() {
        //make clear which color the player is playing as
        String css_class = "green";
        if (color) {
            css_class = "orange";
        }
        gridPane.getStyleClass().add(css_class);
        white_sidePane.getStyleClass().add("orange");
        black_sidePane.getStyleClass().add("green");
    }

    private Point getDimension() {
        int x = 0;
        int y = 0;
        for (Point p : boardconfiguration) {
            if (p.getX() > x) {
                x = p.getX();
            }
            if (p.getY() > y) {
                y = p.getY();
            }
        }
        return new Point(x, y);
    }

    public void sendServerMove(String move) {
        AwaitResponseTask task = client.getAwaitResponseTask(move);
        task.stateProperty().addListener(this::gotMove);
        new Thread(task).start();
        boardModel.setCanMove(false);
    }

    private void gotMove(Observable o) {
        AwaitResponseTask task = (AwaitResponseTask) ((Property) o).getBean();
        if (task.getState() == Worker.State.SUCCEEDED) {
            String value = task.getValue().trim();
            System.out.println(value);
            if (! value.equals("Q")) {
                // handle incoming move
                if (value.matches("X [TF] [0-9] [0-9] [+@Xo]")) {
                    doMove(value);
                } else if (value.equals("X")) {
                    boardModel.setPreviousMove(new Move().setData(0,0,Piece.EMPTY, false));
                } else {
                    quit("Opponent has send an unexpected line");
                }
                // send 'X' if necessary
                int som = possibleMoves();
                System.out.println("possible positions: " + som);
                if (som > 0) {
                    // if the incoming move is the final move, you should
                    if (!boardModel.isFinalMove()) {
                        boardModel.setCanMove(true);
                    }
                } else {
                    // skip turn
                    boardModel.setPreviousMove(new Move().setData(0,0,Piece.EMPTY, false));
                    sendServerMove("X");
                }
            } else {
                quit("Your opponent left the game");
            }
        }
    }
    private void quit(String reason) {
        main.openWindow("/Meinlieff/GameFinished/GameFinished.fxml", new GameFinishedCompanion(main, reason));
    }

    private void doMove(String value) {
        Move move = new Move().setData(value);
        int i = 0;
        while (i < 8 && boardModel.getSideTile(i, !boardModel.getPlayerColor()).getPiece() != move.getPiece())
            i++;
        Tile tile = boardModel.getSideTile(i, !boardModel.getPlayerColor());
        if (! boardModel.setTile(move.getX(), move.getY(), tile)) {
            // cheater
            quit("Your opponent was cheating");
        }
    }

    private int possibleMoves() {
        int som = 0;
        for (Point p : boardconfiguration) {
            if (boardModel.validatePosition(p.getX(), p.getY())) {
                som += 1;
            }
        }
        return som;
    }
}

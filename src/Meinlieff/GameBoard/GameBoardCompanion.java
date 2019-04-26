package Meinlieff.GameBoard;

import Meinlieff.Companion;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerClient.ServerTasks.AwaitResponseTask;
import javafx.application.Platform;
import javafx.beans.Observable;
import javafx.beans.property.Property;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameBoardCompanion implements Companion {

    private Client client;
    private ArrayList<Point> boardconfiguration;
    private String board;
    private boolean color;
    private GameBoardModel boardModel;

    @FXML
    public GridPane gridPane;
    @FXML
    public GridPane white_sidePane;
    @FXML
    public GridPane black_sidePane;

    public GameBoardCompanion(Client client, String boardconfiguration, boolean color) {
        this.client = client;
        this.boardconfiguration = parsePoints(boardconfiguration);
        this.color = color;
        this.board = boardconfiguration;
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

    private ArrayList<Point> parsePoints(String line) {
        line = line.replace(" ", "").replace("X", "");
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            points.add(new Point(Integer.parseInt(line.charAt(i * 2) + ""), Integer.parseInt(line.charAt(i * 2 + 1) + "")));
            points.add(new Point(Integer.parseInt(line.charAt(i * 2) + "") + 1, Integer.parseInt(line.charAt(i * 2 + 1) + "")));
            points.add(new Point(Integer.parseInt(line.charAt(i * 2) + ""), Integer.parseInt(line.charAt(i * 2 + 1) + "") + 1));
            points.add(new Point(Integer.parseInt(line.charAt(i * 2) + "") + 1, Integer.parseInt(line.charAt(i * 2 + 1) + "") + 1));
        }
        return points;
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
            if (! task.getValue().trim().equals("Q")) {
                Move move = new Move().setData(task.getValue());
                System.out.println(move.getPiece());
                int i = 0;
                while (i < 8 && boardModel.getSideTile(i, !boardModel.getPlayerColor()).getPiece() != move.getPiece())
                    i++;
                System.out.println(i + " " + boardModel.getSideTile(i, !boardModel.getPlayerColor()).getPiece());
                Tile tile = boardModel.getSideTile(i, !boardModel.getPlayerColor());
                boardModel.setTile(move.getX(), move.getY(), tile);
                boardModel.setCanMove(true);
            } else {
                Platform.exit();
            }
        }
    }
}

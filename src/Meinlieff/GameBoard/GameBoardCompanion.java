package Meinlieff.GameBoard;

import Meinlieff.Companion;
import Meinlieff.ServerClient.Client;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;

import java.util.ArrayList;

public class GameBoardCompanion implements Companion {

    private Client client;
    private ArrayList<Point> boardconfiguration;
    private boolean color;

    @FXML
    public GridPane gridPane;

    public GameBoardCompanion(Client client, String boardconfiguration, boolean color) {
        this.client = client;
        this.boardconfiguration = parsePoints(boardconfiguration);
        this.color = color;
    }

    public void initialize() {
        Point dimension = getDimension();
        for (int i = 0; i <dimension.getX(); i++) {
            gridPane.addColumn(i);
        }
        for (int i = 0; i <dimension.getY(); i++) {
            gridPane.addRow(i);
        }
        gridPane.setGridLinesVisible(true);
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
}

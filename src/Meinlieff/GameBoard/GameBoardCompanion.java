package Meinlieff.GameBoard;

import Meinlieff.Companion;
import Meinlieff.ServerClient.Client;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
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
        // initalize central gridpane
        Point dimension = getDimension();
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
        // calculate the size of the tiles
        double size = 800 / (dimension.getY() + 1);
        if (dimension.getX() > dimension.getY()) {
            size = 800 / (dimension.getX() + 1);
        }

        for (int i = 0; i <= dimension.getX(); i++) {
            for (int j = 0; j <= dimension.getY(); j++) {
                Image image = new Image("/Image/empty.png");
                for (Point p : boardconfiguration) {
                    if (p.toString().equals(new Point(i, j).toString())) {
                        image = new Image("/Image/wit-loper.png");
                    }
                }
                ImageView imageView = new ImageView(image);
                imageView.setFitHeight(size);
                imageView.setFitWidth(size);
                gridPane.add(imageView, i, j);
            }
        }
        System.out.println(dimension);
        System.out.println(gridPane.getPrefHeight() + " " + gridPane.getPrefWidth());
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

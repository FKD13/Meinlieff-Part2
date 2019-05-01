package Meinlieff.BoardPicker;

import Meinlieff.GameBoard.Point;

import java.util.ArrayList;

public class GameBoardCompanionInitializer {

    protected ArrayList<Point> parsePoints(String line) {
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

    protected boolean securityCheck(ArrayList<Point> points) {
        int i = 0;
        boolean trig = true;
        while (trig && i < points.size()) {
            int j = i + 1;
            while (trig && j < points.size()) {
                if (points.get(i).toString().equals(points.get(j).toString())) {
                    trig = false;
                }
                j++;
            }
            i++;
        }
        return trig;
    }
}

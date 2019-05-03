package mijnlieff.Part1.Updated;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.ImageView;

public class SideTileImageView extends ImageView implements InvalidationListener {

    private boolean color;
    private int nr;

    public SideTileImageView(int width, int height, boolean color, int nr) {
        setFitHeight(height);
        setFitWidth(width);
        this.color = color;
        this.nr = nr;
    }

    @Override
    public void invalidated(Observable observable) {
        PartOneModel boardModel = (PartOneModel) observable;
        setImage(boardModel.getSideTile(nr, color).getPiece().getImage(color));
    }
}


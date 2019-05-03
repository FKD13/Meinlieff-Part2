package Meinlieff.Part1.Updated;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;

import javafx.scene.image.ImageView;

public class BoardTileImageView extends ImageView implements InvalidationListener {
    private int x;
    private int y;

    public BoardTileImageView(int width, int height, int x, int y) {
        setFitHeight(height);
        setFitWidth(width);
        this.x = x;
        this.y = y;
    }

    @Override
    public void invalidated(Observable observable) {
        PartOneModel model = (PartOneModel) observable;
        setImage(model.getTile(x, y).getPiece().getImage(model.getTile(x, y).getColor()));
    }
}

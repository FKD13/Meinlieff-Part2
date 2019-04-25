package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileImageView extends ImageView implements InvalidationListener {

    private int x;
    private int y;

    public TileImageView(Image image, int x, int y) {
        super(image);
        this.x = x;
        this.y = y;
    }

    @Override
    public void invalidated(Observable observable) {
        GameBoardModel model = (GameBoardModel) observable;
        model.getTile(x, y);
    }
}

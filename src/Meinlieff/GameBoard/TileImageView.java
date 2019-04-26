package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileImageView extends ImageView implements InvalidationListener {

    private int x;
    private int y;

    private GameBoardModel model;
    private boolean isGameView;

    public TileImageView(Image image, int x, int y, boolean isGameView, GameBoardModel model) {
        super(image);
        this.x = x;
        this.y = y;
        this.isGameView = isGameView;
        this.model = model;
        this.setOnMouseClicked((e) -> clicked());
    }

    private void clicked() {
        if (getImage() != null) {
            if (isGameView) {
                setImage(model.getSelectedTile().getPiece().getImage(true));
            } else {
                model.setSelectedTile(model.getTile(x, y));
            }
        }
    }

    @Override
    public void invalidated(Observable observable) {
        setImage(model.getTile(x, y).getPiece().getImage(true));
    }
}

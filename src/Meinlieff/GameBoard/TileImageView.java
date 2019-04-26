package Meinlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class TileImageView extends ImageView implements InvalidationListener {

    private int x;
    private int y;

    private GameBoardModel model;
    private GameBoardCompanion companion;
    private boolean isGameView;

    public TileImageView(Image image, int x, int y, GameBoardModel model, GameBoardCompanion companion) {
        super(image);
        this.x = x;
        this.y = y;
        this.model = model;
        this.companion = companion;
        model.addListener(this);
        this.setOnMouseClicked((e) -> clicked());
    }

    private void clicked() {
        if (model.getTile(x, y).getPiece() == Piece.EMPTY) {
            if (model.getSelectedTile() != null) {
                model.setTile(x, y, model.getSelectedTile());
                model.setSelectedTile(null);
                companion.sendserverMove("X F " + x + " " + y + " @");
            }
        }
    }

    @Override
    public void invalidated(Observable observable) {
        setImage(model.getTile(x, y).getPiece().getImage(model.getTile(x, y).getColor()));
    }
}

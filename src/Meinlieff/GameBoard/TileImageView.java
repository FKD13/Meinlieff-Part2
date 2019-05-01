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
        if (model.CanMove() && model.getTile(x, y).getPiece() == Piece.EMPTY) {
            if (model.getSelectedTile() != null) {
                Piece p = model.getSelectedTile().getPiece();
                if (model.setTile(x, y, model.getSelectedTile())) {
                    companion.sendServerMove(new Move().setData(x, y, p, model.isFinalMove()).toString());
                    model.setSelectedTile(null);
                }
            }
        }
    }

    @Override
    public void invalidated(Observable observable) {
        setImage(model.getTile(x, y).getPiece().getImage(model.getTile(x, y).getColor()));
    }
}

package mijnlieff.GameBoard;

import javafx.beans.InvalidationListener;
import javafx.beans.Observable;
import javafx.scene.image.ImageView;

public class SideTileImageView extends ImageView implements InvalidationListener {

    private int nr;
    private boolean color;
    private GameBoardModel model;

    public SideTileImageView(int nr, boolean color, GameBoardModel model) {
        this.nr = nr;
        this.color = color;
        this.model = model;
        model.addListener(this);

        setFitHeight(100);
        setFitWidth(100);
        setOnMouseClicked(e -> clicked());
    }

    private void clicked() {
        if (model.getPlayerColor() == color) {
            model.setSelectedTile(model.getSideTile(nr, color));
        }
    }

    @Override
    public void invalidated(Observable observable) {
        setImage(model.getSideTile(nr, color).getPiece().getImage(color));
    }
}

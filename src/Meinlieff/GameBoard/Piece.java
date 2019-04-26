package Meinlieff.GameBoard;

import javafx.scene.image.Image;

public enum Piece {

    PUSHER {
        @Override
        public Image getImage(boolean color) {
            if (color) {
                return new Image("/Image/wit-pusher.png");
            } else {
                return new Image("/Image/zwart-pusher.png");
            }
        }
    },
    PULLER {
        @Override
        public Image getImage(boolean color) {
            if (color) {
                return new Image("/Image/wit-puller.png");
            } else {
                return new Image("/Image/zwart-puller.png");
            }
        }
    },
    TOWER {
        @Override
        public Image getImage(boolean color) {
            if (color) {
                return new Image("/Image/wit-toren.png");
            } else {
                return new Image("/Image/zwart-toren.png");
            }
        }
    },
    RUNNER {
        @Override
        public Image getImage(boolean color) {
            if (color) {
                return new Image("/Image/wit-loper.png");
            } else {
                return new Image("/Image/zwart-loper.png");
            }
        }
    },
    EMPTY {
        @Override
        public Image getImage(boolean color) {
            return new Image("/Image/empty.png");
        }
    },
    NULL {
        @Override
        public Image getImage(boolean color) {
            return null;
        }
    };

    public abstract Image getImage(boolean color);
}

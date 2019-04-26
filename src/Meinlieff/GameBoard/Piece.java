package Meinlieff.GameBoard;

import javafx.scene.image.Image;

public enum Piece {

    PUSHER {
        @Override
        public Image getImage(boolean b) {
            return new Image("/Image/wit-pusher.png");
        }
    },
    PULLER {
        @Override
        public Image getImage(boolean b) {
            return new Image("/Image/wit-puller.png");
        }
    },
    TOWER {
        @Override
        public Image getImage(boolean b) {
            return new Image("/Image/wit-toren.png");
        }
    },
    RUNNER {
        @Override
        public Image getImage(boolean b) {
            return new Image("/Image/wit-loper.png");
        }
    },
    EMPTY {
        @Override
        public Image getImage(boolean b) {
            return new Image("/Image/empty.png");
        }
    },
    NULL {
        @Override
        public Image getImage(boolean b) {
            return null;
        }
    };

    public abstract Image getImage(boolean b);
}

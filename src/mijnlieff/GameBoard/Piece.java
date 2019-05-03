package mijnlieff.GameBoard;

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

        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            return tileX - 1 > x || tileX + 1 < x || tileY - 1 > y || tileY + 1 < y;
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

        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            return ! (tileX == x && tileY == y) && (tileX - 1 <= x && tileX + 1 >= x && tileY - 1 <= y && tileY + 1 >= y);
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

        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            return (tileX == x && tileY != y) || (tileX != x && tileY == y);
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

        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            int delta = tileY - y;
            return delta != 0 && (x - delta == tileX || x + delta == tileX);
        }
    },
    EMPTY {
        @Override
        public Image getImage(boolean color) {
            return new Image("/Image/empty.png");
        }

        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            return true;
        }
    },
    NULL {
        @Override
        public Image getImage(boolean color) {
            return null;
        }


        @Override
        public boolean isValidPosition(int tileX, int tileY, int x, int y) {
            return false;
        }
    };

    public abstract Image getImage(boolean color);
    public abstract boolean isValidPosition(int tileX, int tileY, int x, int y);
}

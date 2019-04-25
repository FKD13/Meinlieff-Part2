package Meinlieff.GameBoard;

public enum Piece {

    PUSHER {
        public String getPath(Boolean b) {
            if (b) {
                return "/Images/wit-pusher.png";
            } else {
                return "/Images/zwart-pusher.png";
            }
        }

        @Override
        public int getMinpos() {
            return 0;
        }
    },

    PULLER {
        public String getPath(Boolean b) {
            if (b) {
                return "/Images/wit-puller.png";
            } else {
                return "/Images/zwart-puller.png";
            }
        }

        @Override
        public int getMinpos() {
            return 2;
        }
    },

    LOPER {
        public String getPath(Boolean b) {
            if (b) {
                return "/Images/wit-loper.png";
            } else {
                return "/Images/zwart-loper.png";
            }
        }

        @Override
        public int getMinpos() {
            return 4;
        }
    },

    TOREN {
        public String getPath(Boolean b) {
            if (b) {
                return "/Images/wit-toren.png";
            } else {
                return "/Images/zwart-toren.png";
            }
        }

        @Override
        public int getMinpos() {
            return 6;
        }
    },

    EMPTY {
        public String getPath(Boolean b) {
            return "/Images/empty.png";
        }

        @Override
        public int getMinpos() {
            return 0;
        }
    };

    public abstract String getPath(Boolean b);
    public abstract int getMinpos();
}

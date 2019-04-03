package Meinlieff.ServerClient;

public class Player {

    private String naam;

    public Player(String naam) {
        this.naam = naam;
    }

    @Override
    public String toString() {
        return naam;
    }
}

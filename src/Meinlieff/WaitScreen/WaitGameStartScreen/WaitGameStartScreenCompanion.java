package Meinlieff.WaitScreen.WaitGameStartScreen;

import Meinlieff.Companion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;

public class WaitGameStartScreenCompanion implements Companion {

    private Main main;
    private Client client;

    public WaitGameStartScreenCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
    }
}

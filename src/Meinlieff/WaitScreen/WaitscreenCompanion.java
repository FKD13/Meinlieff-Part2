package Meinlieff.WaitScreen;

import Meinlieff.Companion;
import Meinlieff.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class WaitscreenCompanion implements Companion {

    @FXML
    public Button cancel;

    private Main main;

    public WaitscreenCompanion(Main main) {
        this.main = main;
    }

    public void initialize() {
        cancel.setOnAction((e) -> cancel());
    }

    private void cancel() {
        //
    }
}

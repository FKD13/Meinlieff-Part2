package Meinlieff.BoardPicker;

import Meinlieff.Companion;
import Meinlieff.Main;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class BoardPickerCompanion implements Companion {

    @FXML
    public GridPane gridpane;
    @FXML
    public Button start;
    @FXML
    public Button clear;

    private Main main;

    public BoardPickerCompanion(Main main) {
        this.main = main;
    }

    public void initialize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton button = new ToggleButton();
                button.setMinWidth(30);
                button.setMinHeight(30);
                button.setVisible(true);
                gridpane.add(button, i, j);
            }
        }
        start.setOnAction((e) -> start());
        clear.setOnAction((e) -> clear());
    }

    private void start() {
        //
    }

    private void clear() {
        for (Node n : gridpane.getChildren()) {
            ToggleButton button = (ToggleButton) n;
            button.setSelected(false);
        }
    }
}

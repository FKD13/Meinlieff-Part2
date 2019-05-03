package Meinlieff.BoardPicker;

import Meinlieff.Companion;
import Meinlieff.GameBoard.GameBoardCompanion;
import Meinlieff.Main;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerSelection.ServerSelectionCompanion;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.GridPane;

public class BoardPickerCompanion extends GameBoardCompanionInitializer implements Companion {

    @FXML
    public GridPane gridpane;
    @FXML
    public Button start;
    @FXML
    public Button clear;
    @FXML
    public Button cancel;
    @FXML
    public Label errorLabel;

    private Main main;
    private Client client;
    private ToggleButton[][] buttons;
    private String coorinates;

    public BoardPickerCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
        buttons = new ToggleButton[11][11];
        coorinates = "X 0 0 2 0 0 2 2 2";
    }

    public void initialize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton button = new ToggleButton();
                button.setOnAction(e -> errorLabel.setVisible(false));
                button.setMinWidth(30);
                button.setMinHeight(30);
                button.setVisible(true);
                buttons[i][j] = button;
                gridpane.add(button, i, j);
            }
        }
        start.setOnAction((e) -> start());
        clear.setOnAction((e) -> clear());
        cancel.setOnAction((e) -> cancel());
        errorLabel.getStyleClass().add("warning");
        errorLabel.setVisible(false);
    }

    private void start() {
        if (validate()) {
            main.openWindow("/Meinlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(main, client, parsePoints(coorinates), true, coorinates));
        } else {
            errorLabel.setVisible(true);
            errorLabel.setText("Invalid Field");
        }
    }

    private void clear() {
        errorLabel.setVisible(false);
        for (Node n : gridpane.getChildren()) {
            ToggleButton button = (ToggleButton) n;
            button.setSelected(false);
        }
    }

    private void cancel() {
        client.disconnect();
        main.openWindow("/Meinlieff/ServerSelection/ServerSelection.fxml", new ServerSelectionCompanion(main, client));
    }

    //todo when a field is invalid it should stop checking
    //todo when a field is moved one up the coordinates should shift back down
    private boolean validate() {
        coorinates = "X";
        boolean[][] field = new boolean[10][10];
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                field[i][j] = false;
            }
        }
        boolean valid = true;
        int done = 0;
        for (int y = 0; y < 10; y++) {
            for (int x = 0; x < 10; x++) {
                if (!field[x][y]) {
                    if (buttons[x][y].isSelected()) {
                        if (x < 9 && y < 9) {
                            coorinates += " " + x + " " + y;
                            field[x][y] = true;
                            field[x + 1][y] = true;
                            field[x][y + 1] = true;
                            field[x + 1][y + 1] = true;
                            done += 1;
                        } else {
                            valid = false;
                        }
                    }
                } else {
                    if (!buttons[x][y].isSelected()) {
                        valid = false;
                    } else {
                        done+= 1;
                    }
                }
            }
        }
        return valid && done == 16;
    }
}

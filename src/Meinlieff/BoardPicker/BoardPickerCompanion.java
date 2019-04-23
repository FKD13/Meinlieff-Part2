package Meinlieff.BoardPicker;

import Meinlieff.Companion;
import Meinlieff.GameBoard.GameBoardCompanion;
import Meinlieff.Main;
import Meinlieff.MainMenu.MainMenuCompanion;
import Meinlieff.ServerClient.Client;
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
    @FXML
    public Button cancel;

    private Main main;
    private Client client;
    private ToggleButton[][] buttons;
    private String coorinates;

    public BoardPickerCompanion(Main main, Client client) {
        this.main = main;
        this.client = client;
        buttons = new ToggleButton[10][10];
        coorinates = "X 0 0 2 0 0 2 2 2";
    }

    public void initialize() {
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                ToggleButton button = new ToggleButton();
                button.setMinWidth(30);
                button.setMinHeight(30);
                button.setVisible(true);
                buttons[i][j] = button;
                gridpane.add(button, i, j);
            }
        }
        start.setOnAction((e) -> start());
        clear.setOnAction((e) -> clear());
        //cancel.setOnAction((e) -> cancel());
        // to prevent serverComm. from breaking
        cancel.setDisable(true);
    }

    // todo replace serr with errorLabel
    private void start() {
        if (validate()) {
            main.openWindow("/Meinlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(client, coorinates, true));
        } else {
            System.err.println("invalid field");
        }
    }

    private void clear() {
        for (Node n : gridpane.getChildren()) {
            ToggleButton button = (ToggleButton) n;
            button.setSelected(false);
        }
    }

    // todo this breaks the server communication
    private void cancel() {
        main.openWindow("/Meinlieff/MainMenu/MainMenu.fxml", new MainMenuCompanion(main, client));
    }

    //todo when a field is invalid it should stop checking
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
        for (int x = 0; x < 10; x++) {
            for (int y = 0; y < 10; y++) {
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
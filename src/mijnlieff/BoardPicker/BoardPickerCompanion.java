package mijnlieff.BoardPicker;

import mijnlieff.Companion;
import mijnlieff.GameBoard.GameBoardCompanion;
import mijnlieff.Mijnlieff;
import mijnlieff.ServerClient.Client;
import mijnlieff.ServerSelection.ServerSelectionCompanion;
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

    private Mijnlieff mijnlieff;
    private Client client;
    private ToggleButton[][] buttons;
    private String coorinates;

    public BoardPickerCompanion(Mijnlieff mijnlieff, Client client) {
        this.mijnlieff = mijnlieff;
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
        // to prevent program from verifying again when nothing has changed
        if (!errorLabel.isVisible()) {
            if (validate()) {
                mijnlieff.openWindow("/mijnlieff/GameBoard/GameBoard.fxml", new GameBoardCompanion(mijnlieff, client, parsePoints(coorinates), true, coorinates));
            } else {
                if (!errorLabel.isVisible()) {
                    errorLabel.setVisible(true);
                    errorLabel.setText("Invalid Field");
                }
            }
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
        mijnlieff.openWindow("/mijnlieff/ServerSelection/ServerSelection.fxml", new ServerSelectionCompanion(mijnlieff, client));
    }

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
        boolean x_ok = false;
        boolean y_ok = false;
        int y = 0;
        while (y < 10 && valid) {
            int x = 0;
            while (x < 10 && valid) {
                if (!field[x][y]) {
                    if (buttons[x][y].isSelected()) {
                        if (x < 9 && y < 9) {
                            coorinates += " " + y + " " + x;
                            if (x == 0) {
                                x_ok = true;
                            } if (y == 0) {
                                y_ok = true;
                            }
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
                        errorLabel.setText("Invalid Field: Tiles should be grouped in squares of 4");
                        errorLabel.setVisible(true);
                    } else {
                        done+= 1;
                    }
                }
                x++;
            }
            y++;
        }
        if (!errorLabel.isVisible() && (! x_ok || ! y_ok)) {
            errorLabel.setText("Invalid Field: there should be a tile on the first row and column");
            errorLabel.setVisible(true);
        }
        return valid && done == 16 && x_ok && y_ok;
    }
}

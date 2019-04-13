package Meinlieff;

import Meinlieff.ServerSelection.ServerSelectionCompanion;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/ServerSelection/ServerSelection.fxml"));
        loader.setController(new ServerSelectionCompanion(this));
        Scene scene = new Scene(loader.load());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Meinlieff");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.primaryStage = primaryStage;
    }

    public void openWindow(String path_to_fxml, Companion cmp) {
        try {
            primaryStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path_to_fxml));
            loader.setController(cmp);
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /*
    public void openMainMenu(String host, int port, String username) {
        try {
            primaryStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu/MainMenu.fxml"));
            loader.setController(new MainMenuCompanion(this, host, port, username));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void openBoardPicker(String host, int port, String username) {
        try {
            primaryStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/BoardPicker/BoardPicker.fxml"));
            loader.setController(new BoardPickerCompanion(this));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    */
}

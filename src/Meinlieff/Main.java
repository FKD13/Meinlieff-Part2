package Meinlieff;

import Meinlieff.MainMenu.MainMenuController;
import Meinlieff.ServerSelection.ServerSelectionController;
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
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ServerSelection/ServerSelection.fxml"));
        loader.setController(new ServerSelectionController(this));
        Scene scene = new Scene(loader.load());

        primaryStage.setResizable(false);
        primaryStage.setTitle("Meinlieff");
        primaryStage.setScene(scene);
        primaryStage.show();

        this.primaryStage = primaryStage;
    }

    public void openMainMenu(String host, int port, String username) {
        try {
            primaryStage.hide();
            FXMLLoader loader = new FXMLLoader(getClass().getResource("MainMenu/MainMenu.fxml"));
            loader.setController(new MainMenuController(this, host, port, username));
            primaryStage.setScene(new Scene(loader.load()));
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

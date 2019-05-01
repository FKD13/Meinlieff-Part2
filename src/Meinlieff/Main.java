package Meinlieff;

import Meinlieff.Part1.Updated.PartOneCompantion;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerSelection.ServerSelectionCompanion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    private Stage primaryStage;
    private Client client;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception{
        client = new Client();
        if (getParameters().getRaw().size() == 0) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/ServerSelection/ServerSelection.fxml"));
            loader.setController(new ServerSelectionCompanion(this, client));
            Scene scene = new Scene(loader.load());
            primaryStage.setResizable(false);
            primaryStage.setTitle("Meinlieff");
            primaryStage.setScene(scene);
            primaryStage.show();

            this.primaryStage = primaryStage;
        } else if (getParameters().getRaw().size() == 2) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/Part1/PartOne.fxml"));
            // todo check if port is number
            loader.setController(new PartOneCompantion(getParameters().getRaw().get(0), Integer.parseInt(getParameters().getRaw().get(1))));
            Scene scene = new Scene(loader.load());
            primaryStage.setResizable(false);
            primaryStage.setTitle("Meinlieff");
            primaryStage.setScene(scene);
            primaryStage.show();
        } else if (getParameters().getRaw().size() == 3) {
            // screenshot part 1
        } else {
            System.err.println("Usage: command [<host> <port> [<path>]]");
            Platform.exit();
        }
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

    @Override
    public void stop() {
        client.disconnect();
    }
}

package Meinlieff;

import Meinlieff.Part1.Updated.PartOneCompantion;
import Meinlieff.ServerClient.Client;
import Meinlieff.ServerSelection.ServerSelectionCompanion;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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
        } else if (getParameters().getRaw().size() == 2 || getParameters().getRaw().size() == 3) {

            FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/Part1/PartOne.fxml"));
            // todo check if port is number
            PartOneCompantion partOneCompantion = new PartOneCompantion(getParameters().getRaw().get(0), Integer.parseInt(getParameters().getRaw().get(1)));
            loader.setController(partOneCompantion);
            Scene scene = new Scene(loader.load());
            primaryStage.setResizable(false);
            primaryStage.setTitle("Meinlieff");
            primaryStage.setScene(scene);

            if (getParameters().getRaw().size() == 3) {
                partOneCompantion.prepareScreenshot();
                WritableImage writableImage = scene.snapshot(null);
                BufferedImage fromFXImage = SwingFXUtils.fromFXImage(
                        writableImage, null);
                ImageIO.write(
                        fromFXImage,
                        "png",
                        new File(getParameters().getRaw().get(2).replaceFirst("^~", System.getProperty("user.home"))));
                Platform.exit();
            } else {
                primaryStage.show();
            }
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

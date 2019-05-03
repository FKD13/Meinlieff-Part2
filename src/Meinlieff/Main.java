package Meinlieff;

import Meinlieff.Part1.Updated.PartOneCompanion;
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
            this.primaryStage = primaryStage;
            primaryStage.setResizable(false);
            primaryStage.setTitle("Mijnlieff");
            openWindow("/Meinlieff/ServerSelection/ServerSelection.fxml", new ServerSelectionCompanion(this, client));
            // FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/ServerSelection/ServerSelection.fxml"));
            // loader.setController(new ServerSelectionCompanion(this, client));
            // Scene scene = new Scene(loader.load());
            //
            // primaryStage.setScene(scene);
            // primaryStage.show();


        } else if (getParameters().getRaw().size() == 2 || getParameters().getRaw().size() == 3) {
            if (!getParameters().getRaw().get(1).matches("[0-9]+")) {
                System.err.println("Usage: Port should be a number");
                Platform.exit();
            } else {
                // I cannot use openWindow here because it is possible it don't has to open
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/Meinlieff/Part1/PartOne.fxml"));
                PartOneCompanion partOneCompanion = new PartOneCompanion(getParameters().getRaw().get(0), Integer.parseInt(getParameters().getRaw().get(1)));
                loader.setController(partOneCompanion);
                Scene scene = new Scene(loader.load());
                primaryStage.setResizable(false);
                primaryStage.setTitle("Meinlieff");
                primaryStage.setScene(scene);

                if (getParameters().getRaw().size() == 3) {
                    partOneCompanion.prepareScreenshot();
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

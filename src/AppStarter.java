import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import presentationmodel.DataPot;
import presentationmodel.discoverer;
import presentationmodel.listener;
import view.RootPane;

import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.util.Enumeration;

/**
 * Created by jp on 11.03.2017.
 */
public class AppStarter extends Application
{
    private DataPot model;

    public void start(Stage primaryStage) throws Exception{
        model = new DataPot();


        primaryStage.setOnCloseRequest(new EventHandler<WindowEvent>() {
            @Override
            public void handle(WindowEvent e) {
                Platform.exit();
                System.exit(0);
            }
        });


        Parent rootPanel = new RootPane(model);

        Scene scene = new Scene(rootPanel);

        primaryStage.titleProperty().bind(model.applTitleProperty());

        primaryStage.setScene(scene);

        primaryStage.setHeight(500);
        primaryStage.setWidth(1000);
        primaryStage.setResizable(true);
        primaryStage.centerOnScreen();

        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);
    }



}

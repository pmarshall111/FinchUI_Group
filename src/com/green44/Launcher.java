package com.green44;

import com.green44.view.ViewManager;
import edu.cmu.ri.createlab.terk.robot.finch.Finch;
import javafx.application.Application;
import javafx.stage.Stage;

//Class to start the program off.
public class Launcher extends Application {
//    public static Finch initialisedFinch;

    @Override
    public void start(Stage primaryStage) throws Exception{
//        initialisedFinch = new Finch();
        ViewManager.init(primaryStage);
//        ViewManager.showMainMenu();
        ViewManager.showCmdLineTask();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

package com.green44.view;

//import com.green44.model.FinchLiveData;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

import java.io.IOException;

//Class serves up pages to the GUI. To change page we just call a method such as ViewManager.showMainMenu()
public class ViewManager {
    private static Stage mainStage;
    private final static int stageWidth = 950;
    private final static int stageHeight = 850;
    //required to have instance of viewManager within static class because getClass() method (used to load in fxml)
    //cannot be called from static methods. But we need the static methods to access this class from the controllers.
    private static ViewManager viewManager;

    public static void init(Stage stage) {
        mainStage = stage;
        mainStage.setTitle("Finch UI");
        mainStage.getIcons().add(new Image("https://lh3.googleusercontent.com/meJELhcTj6NeEK2o8IvU2_QOLI8YPwg2L1m3mYf5CmxvO4v2gL-mQIw52Ucw6ENoY1jYzgl5N6A=w128-h128-e365"));
        viewManager = new ViewManager();
    }

    public static void showMainMenu() {
        viewManager.showMainMenuInstance();
    }

    private void showMainMenuInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("mainMenu.fxml"));
            mainStage.setScene(new Scene(root, stageWidth, stageHeight));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //TODO: need to rename this method to your task
    public static void showStarterCodeTask() {
        viewManager.showStarterCodeTaskInstance();
    }

    //TODO: rename this method also
    private void showStarterCodeTaskInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("starterCode.fxml"));
            mainStage.setScene(new Scene(root, stageWidth, stageHeight));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void showSearchForLight() {
        viewManager.showSearchForLightInstance();
    }

    private void showSearchForLightInstance() {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("searchForLightTask.fxml"));
            mainStage.setScene(new Scene(root, stageWidth, stageHeight));
            mainStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
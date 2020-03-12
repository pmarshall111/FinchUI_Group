package com.green44.controller;

import com.green44.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;

public class MainMenu {

    @FXML
    private Button lightBtn;

    @FXML
    private Button shapeBtn;

    @FXML
    private Button naviBtn;

    @FXML
    private Button zigBtn;

    @FXML
    private Button objectBtn;

    @FXML
    private Button danceBtn;

    @FXML
    private void goToSearchForLight() {
        ViewManager.showSearchForLight();
    }

    @FXML
    private void goToStarterCode() {
        ViewManager.showStarterCodeTask();
    }
    
    @FXML
    private void goToCmdLineCode() {
        ViewManager.showCmdLineTask();
    }

}

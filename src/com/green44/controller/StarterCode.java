package com.green44.controller;

import com.green44.view.ViewManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;

//This is the class that is used to control user interaction with the fxml. so if a user clicks a button, we will
//respond by calling a method written in this class. Example given by the goToMainMenu method. The button in the fxml
//knows to call that method as it has an attribute in the fxml code called "onMouseClicked" which gives a reference to the
//goToMainMenu method.
//Note: anything that is annotated with a @FXML tag means that it can be seen by the fxml code, or lets the
//java class know that we're referencing an fxml component
public class StarterCode {
    @FXML
    private Button stopBtn;

    @FXML
    private Button startBtn;

    @FXML
    private TextField input1;

    @FXML
    private ChoiceBox choiceBox;

    @FXML
    private CheckBox checkBox;

    @FXML
    void goToMainMenu() {
        ViewManager.showMainMenu();
    }

    @FXML
    void startSearching() {
        stopBtn.setVisible(true);
        startBtn.setVisible(false);
        //in here we would start the task as well

        
    }

    @FXML
    void endSearch() {
        stopBtn.setVisible(false);
        startBtn.setVisible(true);
        //and here we would end the task
    }


}

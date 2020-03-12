package com.green44.controller;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintStream;

import com.green44.model.NoughtsAndCrosses;
import com.green44.view.ViewManager;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

public class ShowCommandLine {
	@FXML
	private Text cmdLineOutput;
	
	@FXML
	private ScrollPane cmdLineScrollPane;
	
	@FXML
	private VBox cmdLineContainer;

	@FXML
	private TextField cmdLineInput;
	
	@FXML
	private GridPane buttonContainer;
	
	@FXML
	private VBox mainContentContainer;
	
	private PrintStream consolePrintStream;
	private Thread programThread;

	@FXML
	void startSearching() {
		mainContentContainer.getChildren().remove(buttonContainer);
		cmdLineContainer.setVisible(true);
		cmdLineContainer.setManaged(true);

		programThread = new Thread(new Runnable() {
			@Override
			public void run() {
				redirectPrintsToGUI();
				NoughtsAndCrosses.play();
				restorePrintsToConsole();
			}
		});

		programThread.start();
	}
	
	@FXML
	void checkIfEnter(KeyEvent e) {
		if (e.getCode() == KeyCode.ENTER) {
			updateProgramText();
		}
	}
	
	@FXML
	void updateProgramText() {
		String input = cmdLineInput.getText();
		//need to interrupt other thread.
		NoughtsAndCrosses.updateUserInput(input);
		if (programThread != null) {
			programThread.interrupt();
		}
		//empty input
		cmdLineInput.setText("");
	}

	
    @FXML
    void goToMainMenu() {
    	restorePrintsToConsole();
        ViewManager.showMainMenu();
    }
    
	private void restorePrintsToConsole(){
		System.out.flush();
		System.setOut(consolePrintStream);				
	}
	
	private void redirectPrintsToGUI()  {
		// Saving the old System.out
		consolePrintStream = System.out;
		
		// Create a new output stream where we can edit behaviour once new entry detected.
		ByteArrayOutputStream baos = new ByteArrayOutputStream() {
	        @Override
	        public void write(int b) {
	            updateText(String.valueOf((char) b));
	        }

	        @Override
	        public void write(byte[] b, int off, int len) {
	            updateText(new String(b, off, len));
	        }

	        @Override
	        public void write(byte[] b) throws IOException {
	            write(b, 0, b.length);
	        }
		};
		PrintStream ps = new PrintStream(baos);
		System.setOut(ps);
	}
	
	private void updateText(final String newText) {
	    Platform.runLater(() -> {
	    	String currText = cmdLineOutput.getText();
	    	cmdLineOutput.setText(currText + newText);
	    	cmdLineScrollPane.setVvalue(1d);
	    });
	}
}

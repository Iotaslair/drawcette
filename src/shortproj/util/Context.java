package util;

import javafx.stage.Stage;

public class Context {

	public Stage stage;
	public double lastx;
	public double lasty;
	public boolean firstclick;

	public Context(Stage primaryStage) {
		lastx = 0;
		lasty = 0;
		
		stage = primaryStage;
		
		firstclick = true;
	}
}

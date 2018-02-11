package projects.shortproj.util;

import javafx.scene.Group;
import javafx.stage.Stage;

public class Context {

	public Stage stage;
	public double storedx;
	public double storedy;
	public boolean firstClick;
	public Group storedGroup;

	public Context(Stage primaryStage) {
		storedx = 0;
		storedy = 0;
		
		stage = primaryStage;
		
		firstClick = true;
		storedGroup = null;
	}
	
	public void resetLastClick() {
		storedx = 0;
		storedy = 0;
		firstClick = true;
		storedGroup = null;
	}
}

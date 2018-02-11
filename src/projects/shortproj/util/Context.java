package projects.shortproj.util;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;

public class Context {

	public Stage stage;
	public double storedx;
	public double storedy;
	public boolean firstClick;
	public int clickCount;
	public Group storedGroup;
	public Node storedNode;
	public Transform transform;

	public Context(Stage primaryStage) {
		storedx = 0;
		storedy = 0;
		
		stage = primaryStage;
		
		clickCount = 0;
		firstClick = true;
		storedGroup = null;
		storedNode = null;
		transform = null;
	}
	
	public void resetLastClick() {
		storedx = 0;
		storedy = 0;
		clickCount = 0;
		firstClick = true;
		storedGroup = null;
		storedNode = null;
		transform = null;
	}
}

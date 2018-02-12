package projects.shortproj.util;

import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Shape;

public class ElementGroup {
	private Group group;
	private String groupName;
	private Paint[] colors;
	
	public void setGroup(Group g) {
		group = g;
	}
	
	public ElementGroup(Group g, String s) {
		group = g;
		groupName = s;
	}
	
	public void setGroupName(String s) {
		groupName = s;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	// Overrides the toString method to return the name of the group
	public String toString() {
		return groupName;
	}

	public void highlight() {
		Color highlight = Color.rgb(255, 233, 0);
		
		colors = new Paint[group.getChildren().size()];
		int i = 0;
		for (Node node : group.getChildren()) {
			if (node instanceof Shape) {
				colors[i++] = ((Shape) node).getStroke();
				((Shape) node).setStroke(highlight);
			}
		}
	}
	
	public void unHighlight() {
		int i = 0;
		for (Node node : group.getChildren()) {
			if (node instanceof Shape) {
				((Shape) node).setStroke(colors[i++]);
			}
		}
	}
}

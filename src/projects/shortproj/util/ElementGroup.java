package projects.shortproj.util;

import javafx.scene.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;


public class ElementGroup {
	private Group group;
	private String groupName;
	private DropShadow highlight;
    private final BooleanProperty hidden = new SimpleBooleanProperty();
	
	public void setGroup(Group g) {
		group = g;
	}
	
	public ElementGroup(Group g, String s) {
		group = g;
		groupName = s;
		group.setAccessibleText(groupName);
		
		highlight = new DropShadow();
		Color color = Color.rgb(255, 0, 0);
		highlight.setColor(color);
		highlight.setOffsetX(0f);
		highlight.setOffsetY(0f);
		highlight.setHeight(50);
		
		this.hidden.set(true);
	}
	
	public void setGroupName(String s) {
		groupName = s;
	}
	
	public Group getGroup() {
		return group;
	}
	
	public void removeFromGroup(Object node) {
		if (node instanceof Node) {
			((Node) node).setEffect(null);
		}
		group.getChildren().remove(node);
	}
	
	public void addToGroup(Node node) {
		group.getChildren().add(node);
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	// Overrides the toString method to return the name of the group
	public String toString() {
		return groupName;
	}

	public void highlight() {
		for (Node node : group.getChildren()) {
			node.setEffect(highlight);
		}
	}
	
	public void unHighlight() {
		for (Node node : group.getChildren()) {
			node.setEffect(null);
		}
	}
	
	public BooleanProperty hiddenProperty() {
		return hidden;
	}
}

package projects.shortproj.util;

import javafx.scene.*;

public class ElementGroup {
	private Group group;
	private String groupName;
	
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
}

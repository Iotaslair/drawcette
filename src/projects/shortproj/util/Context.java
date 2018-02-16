package projects.shortproj.util;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.MenuBar;
import javafx.scene.control.ToggleGroup;
import javafx.scene.transform.Transform;
import javafx.stage.Stage;
import projects.shortproj.gui.ColorBar;
import projects.shortproj.gui.DrawingSurface;
import projects.shortproj.gui.SideBar;
import projects.shortproj.gui.SideBarRight;
import projects.shortproj.gui.TopMenu;

public class Context {
	public Stage stage;
	
	// Main elements
	public ColorBar colorPicker;
	public SideBar menuBox;
	public SideBarRight sidebarRight;
	public DrawingSurface surface;
	public MenuBar menuBar;
	public ToggleGroup toolGroup = new ToggleGroup();

	public double storedx;
	public double storedy;
	
	public double extrax;
	public double extray;
	public boolean firstClick;
	public int clickCount;
	public Group storedGroup;
	public Node storedNode;
	public Node storedNode1;
	public Node storedNode2;
	public Node storedNode3;
	public Transform transform;

	public Context(Stage primaryStage) {
		stage = primaryStage;
		
		menuBox = new SideBar(this);
        sidebarRight = new SideBarRight(this);
        surface = new DrawingSurface(this);
        menuBar = new TopMenu(this);
        colorPicker = new ColorBar(this);

        // Set element sizes
        menuBar.setPrefHeight(25.0);
        menuBox.setPrefWidth(100.0);
        sidebarRight.setPrefWidth(180.0);
        colorPicker.setPrefHeight(60.0);
        
		storedx = 0;
		storedy = 0;
		
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
		
		if (storedNode1 != null) surface.getChildren().remove(storedNode1);
		storedNode1 = null;
		if (storedNode2 != null) surface.getChildren().remove(storedNode2);
		storedNode2 = null;
		if (storedNode3 != null) surface.getChildren().remove(storedNode3);
		storedNode3 = null;
	}
	
	public void refreshZ() {
		this.menuBox.toFront();
		this.sidebarRight.toFront();
		this.menuBar.toFront();
		this.colorPicker.toFront();
	}
}

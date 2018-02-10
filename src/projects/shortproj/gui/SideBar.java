package projects.shortproj.gui;

import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import projects.shortproj.util.Context;

public final class SideBar extends VBox {
	public SideBar(Context context) {
        this.setPadding(new Insets(5, 25, 15, 15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #336699;");

        // Tool Group 1
        final ToggleGroup group1 = new ToggleGroup();

        Label toolsLabel1 = new Label("Brushes");
        toolsLabel1.setTextFill(Color.WHITE);
        
        ToggleButton tool1 = new ToggleButton("Brush 1");
        tool1.setToggleGroup(group1);
        tool1.setSelected(true);
        tool1.setPrefSize(60, 25);

        ToggleButton tool2 = new ToggleButton("Brush 2");
        tool2.setToggleGroup(group1);
        tool2.setPrefSize(60, 25);

        ToggleButton tool3 = new ToggleButton("Brush 3");
        tool3.setToggleGroup(group1);
        tool3.setPrefSize(60, 25);

        // Instrument Group 2        
        final ToggleGroup group2 = new ToggleGroup();

        Label toolsLabel2 = new Label("Shapes");
        toolsLabel2.setPadding(new Insets(10, 0, 0, 0));
        toolsLabel2.setTextFill(Color.WHITE);

        ToggleButton tool4 = new ToggleButton("Line");
        tool4.setToggleGroup(group2);
        tool4.setSelected(true);
        tool4.setPrefSize(60, 25);

        ToggleButton tool5 = new ToggleButton("Square");
        tool5.setToggleGroup(group2);
        tool5.setPrefSize(60, 25);

        ToggleButton tool6 = new ToggleButton("Circle");
        tool6.setToggleGroup(group2);
        tool6.setPrefSize(60, 25);

        
        // Add all elements to the toolbar
        this.getChildren().addAll(toolsLabel1, tool1, tool2, tool3, toolsLabel2, tool4, tool5, tool6);
        
	}
}

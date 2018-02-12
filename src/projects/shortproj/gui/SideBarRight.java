package projects.shortproj.gui;

import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import projects.shortproj.util.Context;

public final class SideBarRight extends VBox {
	Context context;
	
	public SideBarRight(Context context) {
		this.context = context;
		
        this.setPadding(new Insets(15, 15, 15, 15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #336699;");
        
        // List of groups
        ListView<String> list = new ListView<String>();
        list.setPrefSize(150, 200);
        
        ObservableList<String> items = FXCollections.observableArrayList (
            "Single", "Double", "Suite", "Family App");
        
        list.setItems(items);
        
        
        // Add list to sidebar
        this.getChildren().addAll(list);
	}
}

package projects.shortproj.gui;

import javafx.collections.*;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import projects.shortproj.util.Context;
import projects.shortproj.util.ElementGroup;

public final class SideBarRight extends VBox {
	Context context;
    ListView<ElementGroup> list = new ListView<ElementGroup>();
	public ObservableList<ElementGroup> items = FXCollections.observableArrayList();
		
	
	public SideBarRight(Context context) {
		this.context = context;
		
        this.setPadding(new Insets(10, 15, 15, 15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #336699;");
                
        // List of groups
        Label groupListLabel = new Label("Groups");
        groupListLabel.setTextFill(Color.WHITE);

        list.setPrefSize(150, 200);                
        list.setItems(items);
        
        // Add list to sidebar
        this.getChildren().addAll(groupListLabel, list);
	}
}

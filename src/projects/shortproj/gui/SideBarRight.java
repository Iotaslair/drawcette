package projects.shortproj.gui;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.*;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.HBox;
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
                
        // Buttons
        HBox btnPanel1 = new HBox();
        btnPanel1.setSpacing(10);
        HBox btnPanel2 = new HBox();
        btnPanel2.setSpacing(10);
        Label lblExistingGroups = new Label("Add/Remove To Existing");
        lblExistingGroups.setTextFill(Color.WHITE);
        
        ToggleButton btnNewGroup = new ToggleButton("New Group");
        btnNewGroup.setToggleGroup(context.toolGroup);
        btnNewGroup.setPrefSize(80, 25);
        btnNewGroup.setUserData("new_group");
        btnNewGroup.setOnAction(getContextClear());
        
        ToggleButton btnDisbandGroup = new ToggleButton("Disband");
        btnDisbandGroup.setToggleGroup(context.toolGroup);
        btnDisbandGroup.setPrefSize(60, 25);
        btnDisbandGroup.setUserData("disband");
        btnDisbandGroup.setOnAction(getDisband());

        ToggleButton btnAddToGroup = new ToggleButton("Add");
        btnAddToGroup.setToggleGroup(context.toolGroup);
        btnAddToGroup.setPrefSize(70, 25);
        btnAddToGroup.setUserData("add");
        btnAddToGroup.setOnAction(getContextClear());

        ToggleButton btnRemoveFromGroup = new ToggleButton("Remove");
        btnRemoveFromGroup.setToggleGroup(context.toolGroup);
        btnRemoveFromGroup.setPrefSize(70, 25);
        btnRemoveFromGroup.setUserData("remove");
        btnRemoveFromGroup.setOnAction(getContextClear());
                
        // List of groups
        Label groupListLabel = new Label("Groups");
        groupListLabel.setTextFill(Color.WHITE);
        
        list.setPrefSize(150, 200);                
        list.setItems(items);
        
        // Default element for no group selection
        ElementGroup background = new ElementGroup(new Group(), "- None -");
        items.add(background);
        
        // Make groups Highlight upon selection.
        list.getSelectionModel().selectedItemProperty().addListener(new ChangeListener<ElementGroup>() {
            public void changed(ObservableValue<? extends ElementGroup> ov, ElementGroup old_val, ElementGroup new_val) {
            		if (old_val != null) old_val.unHighlight();
            		new_val.highlight();
                }
         });
        
        // Horizontal box for buttons
        btnPanel1.getChildren().addAll(btnNewGroup, btnDisbandGroup);
        btnPanel2.getChildren().addAll(btnAddToGroup, btnRemoveFromGroup);
        
        // Add list to sidebar
        this.getChildren().addAll(groupListLabel, list, btnPanel1, lblExistingGroups, btnPanel2);
	}

	private EventHandler<ActionEvent> getContextClear() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				context.resetLastClick();
			}
		};
	}
	
	private EventHandler<ActionEvent> getDisband() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				disband();
			}
		};
	}
	
	public ElementGroup getActiveGroup() {
		return list.getSelectionModel().getSelectedItem();
	}
	
	private void disband() {
		if (list.getSelectionModel().getSelectedItem() == null) return;
		ElementGroup elementGroup = list.getSelectionModel().getSelectedItem();
		if (elementGroup.getGroupName().equals("none")) return;
		Group group = elementGroup.getGroup();
		elementGroup.unHighlight();
		
		// Get rid of each node while respecting their movements.
		for (Node node : group.getChildren()) {
			node.setTranslateX(node.getTranslateX() + node.getParent().getTranslateX());
			node.setTranslateY(node.getTranslateY() + node.getParent().getTranslateY());
			node.setRotate(node.getRotate() + node.getParent().getRotate());
			node.setEffect(null);
		}
		
		context.surface.getChildren().addAll(group.getChildren());
						
		// When group is now empty delete it.
		context.surface.getChildren().remove(group);
		this.items.remove(elementGroup);
	}
}

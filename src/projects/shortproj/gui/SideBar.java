package projects.shortproj.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

import projects.shortproj.util.Context;

public final class SideBar extends VBox {
	
	final ToggleGroup group1;
	Context context;
	Slider thiccness = new Slider(0, 10, 3);
	
	public SideBar(Context context) {
		this.context = context;
		
        this.setPadding(new Insets(10, 15, 15, 15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #336699;");

        // Tool Group 1
        group1 = new ToggleGroup();

        Label toolsLabel1 = new Label("Create");
        toolsLabel1.setTextFill(Color.WHITE);
        
        ToggleButton btnHandDraw = new ToggleButton("Free Hand");
        btnHandDraw.setToggleGroup(group1);
        btnHandDraw.setSelected(true);
        btnHandDraw.setPrefSize(80, 25);
        btnHandDraw.setUserData("freehand");
        btnHandDraw.setOnAction(getContextClear());

        Label lblThickness = new Label("Thickness");
        lblThickness.setTextFill(Color.WHITE);
        thiccness = new Slider(1, 10, 3);
        thiccness.setPrefWidth(60);
        
        Label toolsLabel2 = new Label("Shapes");
        toolsLabel2.setTextFill(Color.WHITE);
        
        ToggleButton btnLine = new ToggleButton("Line");
        btnLine.setToggleGroup(group1);
        btnLine.setPrefSize(80, 25);
        btnLine.setUserData("line");
        btnLine.setOnAction(getContextClear());

        ToggleButton btnSquare = new ToggleButton("Square");
        btnSquare.setToggleGroup(group1);
        btnSquare.setPrefSize(80, 25);
        btnSquare.setUserData("square");
        btnSquare.setOnAction(getContextClear());

        ToggleButton btnCircle = new ToggleButton("Circle");
        btnCircle.setToggleGroup(group1);
        btnCircle.setPrefSize(80, 25);
        btnCircle.setUserData("circle");
        btnCircle.setOnAction(getContextClear());

        ToggleButton btnHandDraw2 = new ToggleButton("Text");
        btnHandDraw2.setToggleGroup(group1);
        btnHandDraw2.setPrefSize(80, 25);
        btnHandDraw2.setUserData("text");
        btnHandDraw2.setOnAction(getContextClear());
        
        Label toolsLabel3 = new Label("Manipulate");
        toolsLabel3.setTextFill(Color.WHITE);
        
        ToggleButton btnDelete = new ToggleButton("Delete");
        btnDelete.setToggleGroup(group1);
        btnDelete.setPrefSize(80, 25);
        btnDelete.setUserData("delete");
        btnDelete.setOnAction(getContextClear());

        ToggleButton btnMove = new ToggleButton("Move");
        btnMove.setToggleGroup(group1);
        btnMove.setPrefSize(80, 25);
        btnMove.setUserData("drag");
        btnMove.setOnAction(getContextClear());
        
        ToggleButton btnRotate = new ToggleButton("Rotate");
        btnRotate.setToggleGroup(group1);
        btnRotate.setPrefSize(80, 25);
        btnRotate.setUserData("rotate");
        btnRotate.setOnAction(getContextClear());
        
        Label toolsLabel4 = new Label("Groups");
        toolsLabel4.setTextFill(Color.WHITE);
        
        ToggleButton btnNewGroup = new ToggleButton("New");
        btnNewGroup.setToggleGroup(group1);
        btnNewGroup.setPrefSize(80, 25);
        btnNewGroup.setUserData("group");
        btnNewGroup.setOnAction(getContextClear());
        
        ToggleButton btnRemoveGroup = new ToggleButton("Remove");
        btnRemoveGroup.setToggleGroup(group1);
        btnRemoveGroup.setPrefSize(80, 25);
        btnRemoveGroup.setUserData("remove");
        btnRemoveGroup.setOnAction(getContextClear());
        
        // Add all elements to the toolbar
        this.getChildren().addAll(toolsLabel1, btnHandDraw, lblThickness, thiccness,
        		toolsLabel2, btnLine, btnSquare, btnCircle, btnHandDraw2, toolsLabel3,
                btnMove, btnRotate, btnDelete, toolsLabel4, btnNewGroup, btnRemoveGroup);
	}
	
	public double getThiccness() {
		return thiccness.getValue();
	}
	
	// Returns the string associated with the depressed button of group 1.
	public String getDepressedButtonGroup1() {
		String depressedButton = (group1.getSelectedToggle() == null) ? "None" : (String) group1.getSelectedToggle().getUserData();
		return depressedButton;
	}
	
	private EventHandler<ActionEvent> getContextClear() {
		return new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				context.resetLastClick();
			}
		};
	}
}

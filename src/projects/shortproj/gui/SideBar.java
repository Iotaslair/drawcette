package projects.shortproj.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import projects.shortproj.util.Context;

public final class SideBar extends VBox {
	
	Context context;
	Slider thiccness = new Slider(0, 10, 3);
	
	public SideBar(Context context) {
		this.context = context;
		
        this.setPadding(new Insets(10, 15, 10, 12));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #336699;");

        Label toolsLabel1 = new Label("Create");
        toolsLabel1.setTextFill(Color.WHITE);
        
        ToggleButton btnHandDraw = new ToggleButton("Free Hand");
        btnHandDraw.setToggleGroup(context.toolGroup);
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
        btnLine.setToggleGroup(context.toolGroup);
        btnLine.setPrefSize(80, 25);
        btnLine.setUserData("line");
        btnLine.setOnAction(getContextClear());

        ToggleButton btnSquare = new ToggleButton("Rectangle");
        btnSquare.setToggleGroup(context.toolGroup);
        btnSquare.setPrefSize(80, 25);
        btnSquare.setUserData("rect");
        btnSquare.setOnAction(getContextClear());

        ToggleButton btnCircle = new ToggleButton("Circle");
        btnCircle.setToggleGroup(context.toolGroup);
        btnCircle.setPrefSize(80, 25);
        btnCircle.setUserData("circle");
        btnCircle.setOnAction(getContextClear());

        ToggleButton btnHandDraw2 = new ToggleButton("Text");
        btnHandDraw2.setToggleGroup(context.toolGroup);
        btnHandDraw2.setPrefSize(80, 25);
        btnHandDraw2.setUserData("text");
        btnHandDraw2.setOnAction(getContextClear());
        
        Label toolsLabel3 = new Label("Manipulate");
        toolsLabel3.setTextFill(Color.WHITE);
        
        ToggleButton btnDelete = new ToggleButton("Delete");
        btnDelete.setToggleGroup(context.toolGroup);
        btnDelete.setPrefSize(80, 25);
        btnDelete.setUserData("delete");
        btnDelete.setOnAction(getContextClear());

        ToggleButton btnMove = new ToggleButton("Move");
        btnMove.setToggleGroup(context.toolGroup);
        btnMove.setPrefSize(80, 25);
        btnMove.setUserData("drag");
        btnMove.setOnAction(getContextClear());
        
        ToggleButton btnRotate = new ToggleButton("Rotate");
        btnRotate.setToggleGroup(context.toolGroup);
        btnRotate.setPrefSize(80, 25);
        btnRotate.setUserData("rotate");
        btnRotate.setOnAction(getContextClear());

        ToggleButton btnCurve = new ToggleButton("Curve");
        btnCurve.setToggleGro,p(context.toolGroup);
        btnCurve.setPrefSize(80,25);
        btnCurve.setUserData("curve");
        btnCurve.setOnAction(getContextClear());
                
        // Add all elements to the toolbar
        this.getChildren().addAll(toolsLabel1, btnHandDraw, lblThickness, thiccness,
        		toolsLabel2, btnLine, btnSquare, btnCircle, btnHandDraw2, toolsLabel3,
                btnMove, btnRotate, btnDelete);
	}
	
	public double getThiccness() {
		return thiccness.getValue();
	}
	
	// Returns the string associated with the depressed button of group 1.
	public String getDepressedButtonGroup1() {
		String depressedButton = (context.toolGroup.getSelectedToggle() == null) ? "None" : (String) context.toolGroup.getSelectedToggle().getUserData();
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

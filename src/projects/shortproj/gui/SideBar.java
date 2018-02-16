package projects.shortproj.gui;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.Tooltip;
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
        btnHandDraw.setTooltip(new Tooltip("Click and drag to draw freeheand."));

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
        btnLine.setTooltip(new Tooltip("Click then move mouse and click again to define start and end points of your line."));

        ToggleButton btnSquare = new ToggleButton("Rectangle");
        btnSquare.setToggleGroup(context.toolGroup);
        btnSquare.setPrefSize(80, 25);
        btnSquare.setUserData("rect");
        btnSquare.setOnAction(getContextClear());
        btnSquare.setTooltip(new Tooltip("Click then move mouse and click again to define top left and bottom right corners of your rectangle."));

        ToggleButton btnCircle = new ToggleButton("Circle");
        btnCircle.setToggleGroup(context.toolGroup);
        btnCircle.setPrefSize(80, 25);
        btnCircle.setUserData("circle");
        btnCircle.setOnAction(getContextClear());
        btnCircle.setTooltip(new Tooltip("Click then move mouse and click again to define center and radius of your circle"));

        ToggleButton btnText = new ToggleButton("Text");
        btnText.setToggleGroup(context.toolGroup);
        btnText.setPrefSize(80, 25);
        btnText.setUserData("text");
        btnText.setOnAction(getContextClear());
        btnText.setTooltip(new Tooltip("Click then input text to display text."));
        
        Label toolsLabel3 = new Label("Manipulate");
        toolsLabel3.setTextFill(Color.WHITE);
        
        ToggleButton btnDelete = new ToggleButton("Delete");
        btnDelete.setToggleGroup(context.toolGroup);
        btnDelete.setPrefSize(80, 25);
        btnDelete.setUserData("delete");
        btnDelete.setOnAction(getContextClear());
        btnDelete.setTooltip(new Tooltip("Click on nodes or groups to delete them."));

        ToggleButton btnMove = new ToggleButton("Move");
        btnMove.setToggleGroup(context.toolGroup);
        btnMove.setPrefSize(80, 25);
        btnMove.setUserData("drag");
        btnMove.setOnAction(getContextClear());
        btnMove.setTooltip(new Tooltip("Click and drag to move nodes or groups."));
        
        ToggleButton btnRotate = new ToggleButton("Rotate");
        btnRotate.setToggleGroup(context.toolGroup);
        btnRotate.setPrefSize(80, 25);
        btnRotate.setUserData("rotate");
        btnRotate.setOnAction(getContextClear());
        btnRotate.setTooltip(new Tooltip("Click on group or node and move mouse away from click point to rotate."));

        ToggleButton btnCurve = new ToggleButton("Curve");
        btnCurve.setToggleGroup(context.toolGroup);
        btnCurve.setPrefSize(80,25);
        btnCurve.setUserData("curve");
        btnCurve.setOnAction(getContextClear());
        btnCurve.setTooltip(new Tooltip("Click a total of 4 times to define start, control1, control2, and end of a Beizer Curve."));
        
        ToggleButton btnCopy = new ToggleButton("Copy");
        btnCopy.setToggleGroup(context.toolGroup);
        btnCopy.setPrefSize(80,25);
        btnCopy.setUserData("copy");
        btnCopy.setOnAction(getContextClear());
        btnCopy.setTooltip(new Tooltip("Click on node or group to create copy, then move mouse and click again to place it."));
        
        ToggleButton btnScale = new ToggleButton("Scale");
        btnScale.setToggleGroup(context.toolGroup);
        btnScale.setPrefSize(80,25);
        btnScale.setUserData("scale");
        btnScale.setOnAction(getContextClear());
        btnScale.setTooltip(new Tooltip("Click on node or group and then move mouse up or down to scale it."));
        
        ToggleButton btnFill = new ToggleButton("Fill");
        btnFill.setToggleGroup(context.toolGroup);
        btnFill.setPrefSize(80,25);
        btnFill.setUserData("fill");
        btnFill.setOnAction(getContextClear());
        btnFill.setTooltip(new Tooltip("Click on a shape to fill in the shape with the selected color"));
                
        // Add all elements to the toolbar
        this.getChildren().addAll(toolsLabel1, btnHandDraw, lblThickness, thiccness,
        		toolsLabel2, btnLine, btnCurve, btnSquare, btnCircle, btnText, toolsLabel3,
                btnMove, btnRotate, btnCopy, btnDelete, btnScale, btnFill);
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

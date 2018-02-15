package projects.shortproj.gui;

import javafx.beans.value.*;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.*;
import projects.shortproj.util.Context;

public class ColorBar extends HBox {
	Context context;
    final ToggleGroup group = new ToggleGroup();

    ToggleButton color1 = new ToggleButton();
    ToggleButton color2 = new ToggleButton();
    ToggleButton color3 = new ToggleButton();
    ToggleButton color4 = new ToggleButton();
    ToggleButton color5 = new ToggleButton();
    ToggleButton color6 = new ToggleButton();
    ToggleButton color7 = new ToggleButton();
    ToggleButton color8 = new ToggleButton();

    public HBox btnPanel = new HBox();
    public Button btnZoomIn = new Button("+");
    public Button btnZoomOut = new Button("-");
    public Button btnResetZoom = new Button("R");
    
	public ColorBar(Context context) {
		this.context = context;
		
		// Set up the panel
        this.setPadding(new Insets(15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #444;");
        
        // Color Palette        
        color1.setToggleGroup(group);
        color1.setTextFill(Color.RED);
        color1.setSelected(true);
        color1.setPrefSize(30, 30);
        color1.setStyle("-fx-base: red;");

        color2.setToggleGroup(group);
        color2.setTextFill(Color.ORANGE);
        color2.setPrefSize(30, 30);
        color2.setStyle("-fx-base: orange;");

        color3.setToggleGroup(group);
        color3.setTextFill(Color.YELLOW);
        color3.setPrefSize(30, 30);
        color3.setStyle("-fx-base: yellow;");
        
        color4.setToggleGroup(group);
        color4.setTextFill(Color.GREEN);
        color4.setPrefSize(30, 30);
        color4.setStyle("-fx-base: green;");

        color5.setToggleGroup(group);
        color5.setTextFill(Color.BLUE);
        color5.setPrefSize(30, 30);
        color5.setStyle("-fx-base: blue;");

        color6.setToggleGroup(group);
        color6.setTextFill(Color.INDIGO);
        color6.setPrefSize(30, 30);
        color6.setStyle("-fx-base: indigo;");

        color7.setToggleGroup(group);
        color7.setTextFill(Color.BLACK);
        color7.setPrefSize(30, 30);
        color7.setStyle("-fx-base: black;");

        color8.setToggleGroup(group);
        color8.setTextFill(Color.WHITE);
        color8.setPrefSize(30, 30);
        color8.setStyle("-fx-base: white;");
        
        btnZoomIn.setStyle("-fx-base: #336699;");
        btnZoomIn.setTextFill(Color.WHITE);
        btnZoomIn.setPrefSize(30, 30);
        btnZoomIn.setFont(Font.font("Verdana", FontWeight.BOLD, 13));
        btnZoomOut.setStyle("-fx-base: #336699;");
        btnZoomOut.setTextFill(Color.WHITE);
        btnZoomOut.setPrefSize(30, 30);
        btnZoomOut.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        btnResetZoom.setStyle("-fx-base: #336699;");
        btnResetZoom.setTextFill(Color.WHITE);
        btnResetZoom.setPrefSize(30, 30);
        btnResetZoom.setFont(Font.font("Verdana", FontWeight.BOLD, 14));
        btnResetZoom.setTooltip(new Tooltip("Resets zoom/scaling to 100%"));
        
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        Label lblZoom = new Label("Zoom:");
        lblZoom.setTextFill(Color.WHITE);
        lblZoom.setPadding(new Insets(6));
        
        btnPanel.setSpacing(5);
        btnPanel.getChildren().addAll(lblZoom, btnZoomIn, btnZoomOut, btnResetZoom);
        
        // Add all elements to pane
        this.getChildren().addAll(color1, color2, color3, color4, color5, color6, color7, color8, spacer, btnPanel);
        
        // Highlight selected color
        changeSelectedColorSize(color1, color1);
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            public void changed(ObservableValue<? extends Toggle> ov, Toggle toggle, Toggle new_toggle) {
        		changeSelectedColorSize(toggle, new_toggle);
        	}
        });
        
        // Handles zoom in
    	btnZoomIn.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	context.surface.setScaleX(context.surface.getScaleX() + 0.1);
            	context.surface.setScaleY(context.surface.getScaleY() + 0.1);
            }
    	});
    	
    	// Handles zoom out - won't zoom out past 0.1. 
    	// Refreshes canvas size to the size of the screen for infinite canvas effect
    	btnZoomOut.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	if (context.surface.getScaleX() > 0.2 && context.surface.getScaleY() > 0.2) {
                	context.surface.setScaleX(context.surface.getScaleX() - 0.1);
                	context.surface.setScaleY(context.surface.getScaleY() - 0.1);            		
            	}
            	double newSurfaceWidth = context.surface.getWidth() * 2;
            	double newSurfaceHeight = context.surface.getHeight() * 2;
            	context.surface.setPrefSize(newSurfaceWidth, newSurfaceHeight);
            	System.out.println(newSurfaceWidth + " " + newSurfaceHeight);
            }
    	});
    	
    	btnResetZoom.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
            	context.surface.setScaleX(1);
            	context.surface.setScaleY(1);
            }
    	});
	}
	
	public Paint getColor() {
		ToggleButton btn = (ToggleButton) group.getSelectedToggle();
		return btn.getTextFill();
	}
	
	public void changeSelectedColorSize(Toggle o, Toggle s) {
		ToggleButton previous = (ToggleButton) o;
		ToggleButton selected = (ToggleButton) s;
        previous.setBorder(null);
        selected.setBorder(new Border(new BorderStroke(Color.WHITESMOKE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.DEFAULT)));
	}
}

package projects.shortproj.gui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.layout.HBox;
import projects.shortproj.util.Context;

public class ColorBar extends HBox {
    final ToggleGroup group = new ToggleGroup();

    ToggleButton color1 = new ToggleButton();
    ToggleButton color2 = new ToggleButton();
    ToggleButton color3 = new ToggleButton();
    ToggleButton color4 = new ToggleButton();
    ToggleButton color5 = new ToggleButton();
    ToggleButton color6 = new ToggleButton();
    ToggleButton color7 = new ToggleButton();
    ToggleButton color8 = new ToggleButton();

	public ColorBar(Context context) {
		// Set up the panel
        this.setPadding(new Insets(20, 20, 15, 15));
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
        color7.setTextFill(Color.VIOLET);
        color7.setPrefSize(30, 30);
        color7.setStyle("-fx-base: violet;");

        color8.setToggleGroup(group);
        color8.setTextFill(Color.BLACK);
        color8.setPrefSize(30, 30);
        color8.setStyle("-fx-base: black;");

        this.getChildren().addAll(color1, color2, color3, color4, color5, color6, color7, color8);
	}
	
	public Paint getColor() {
		ToggleButton btn = (ToggleButton) group.getSelectedToggle();
		return btn.getTextFill();
	}
}

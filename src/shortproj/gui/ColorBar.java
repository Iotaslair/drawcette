package gui;

import javafx.geometry.Insets;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.paint.Color;
import javafx.scene.layout.HBox;
import util.Context;

public class ColorBar extends HBox {
	public ColorBar(Context context) {
		// Set up the panel
        this.setPadding(new Insets(20, 20, 15, 15));
        this.setSpacing(10);
        this.setStyle("-fx-background-color: #444;");
        
        // Color Palate
        final ToggleGroup group = new ToggleGroup();
        
        ToggleButton color1 = new ToggleButton();
        color1.setToggleGroup(group);
        // color1.setUserData(Color.RED);
        color1.setSelected(true);
        color1.setPrefSize(30, 30);
        color1.setStyle("-fx-base: red;");

        ToggleButton color2 = new ToggleButton();
        color2.setToggleGroup(group);
        color2.setUserData(Color.ORANGE);
        color2.setPrefSize(30, 30);
        color2.setStyle("-fx-base: orange;");

        ToggleButton color3 = new ToggleButton();
        color3.setToggleGroup(group);
        color3.setUserData(Color.YELLOW);
        color3.setPrefSize(30, 30);
        color3.setStyle("-fx-base: yellow;");
        
        ToggleButton color4 = new ToggleButton();
        color4.setToggleGroup(group);
        color4.setUserData(Color.GREEN);
        color4.setPrefSize(30, 30);
        color4.setStyle("-fx-base: green;");

        ToggleButton color5 = new ToggleButton();
        color5.setToggleGroup(group);
        color5.setUserData(Color.BLUE);
        color5.setPrefSize(30, 30);
        color5.setStyle("-fx-base: blue;");

        ToggleButton color6 = new ToggleButton();
        color6.setToggleGroup(group);
        color6.setUserData(Color.INDIGO);
        color6.setPrefSize(30, 30);
        color6.setStyle("-fx-base: indigo;");

        ToggleButton color7 = new ToggleButton();
        color7.setToggleGroup(group);
        color7.setUserData(Color.VIOLET);
        color7.setPrefSize(30, 30);
        color7.setStyle("-fx-base: violet;");

        ToggleButton color8 = new ToggleButton();
        color8.setToggleGroup(group);
        color8.setUserData(Color.BLACK);
        color8.setPrefSize(30, 30);
        color8.setStyle("-fx-base: black;");

        this.getChildren().addAll(color1, color2, color3, color4, color5, color6, color7, color8);
	}
}

 

import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
//import projects.shortproj.util.*;
//import projects.shortproj.gui.*;

public class DrawingSurface extends Pane {

    private SideBar sideBar;
    private ColorBar colorBar;
    private Context context;
    
    public DrawingSurface(SideBar sideBar, ColorBar colorBar, Context context) {
        this.sideBar = sideBar;
        this.colorBar = colorBar;
        this.context = context;
        
        // Add an event handler to the pane and make it draw lines for now.
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Asks sidebar what button is down to call appropriate function on surface.
                String depressedButton = sideBar.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                    case "line": drawLine(event);
                                 break;
                                 
                    case "Circle": drawCircle(event);      
                                  break;
                                  
                    case "Square": drawRectangle(event);      
                                  break;
                                 
                    default:     System.out.println("Don't know what to do with this click.");
                                 break;
                }
            }
        });
        
    }
    
    
    /*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void drawLine(MouseEvent event) {
        //System.out.println(event.getX() + ", " + event.getY());
        if (context.firstClick){
            context.firstClick = false;
            context.storedx = event.getX();
            context.storedy = event.getY();
        }
        else {
            context.firstClick = true;
            // Make line
            Line line = new Line();
            Paint c = colorBar.getColor();
            line.setStroke(c);
            
            line.setStartX(context.storedx);
            line.setStartY(context.storedy);
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            this.getChildren().add(line);
                    
            context.storedx = -1;
            context.storedy = -1;
        }
    }
    
    public void drawCircle(MouseEvent event){ //Draws a circle without a graphics context
       double ourX = event.getX();                        //Grabs our mouse coordinates
       double ourY = event.getY();
       Paint paintType = colorBar.getColor();            //Selects our color.
       Circle newCir = new Circle(ourX, ourY, 20.0d, paintType); //Makes a circle
       this.getChildren().add(newCir);  //Draws circle on the pane
   }
    
   
   public void drawRectangle(MouseEvent event){           //Draws a rectangle 
       double ourX = event.getX();                        //Grabs our mouse coordinates
       double ourY = event.getY();
       Paint paintType = colorBar.getColor();            //Selects our color.
       Rectangle newRect = new Rectangle(40.0, 20.0, paintType); //Makes a rectangle
       newRect.setX(ourX - 20);         //This moves rectangle where we clicked it
       newRect.setY(ourY - 10);
       this.getChildren().add(newRect);  //Draws on  pane
   }
   
   public void drawPixel(MouseEvent event){ //Draws a pixel of color.
       double ourX = event.getX();                        //Grabs our mouse coordinates
       double ourY = event.getY();
       Line line = new Line();              //Although there is a method for drawing pixels themselves, I don't think
                                            //will permit us to select them late and move them around.
                                            //SOLUTION: Cheat and draw a line that starts and ends where we click. In
                                            //effect, this makes a pixel.
       Paint paintType = colorBar.getColor();
       line.setStroke(paintType);
       line.setStartX(ourX);
       line.setStartY(ourY);
       line.setEndX(ourX);
       line.setEndY(ourY);
       this.getChildren().add(line);  //Draws circle on the pane
   }
}

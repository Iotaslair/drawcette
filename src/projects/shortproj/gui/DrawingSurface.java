package projects.shortproj.gui;

import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextInputDialog;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.*;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.scene.transform.Translate;
import projects.shortproj.util.Context;
import projects.shortproj.util.ElementGroup;
import java.lang.Math;
import java.util.Optional;

public class DrawingSurface extends Pane {

    private Context context;
    private int groupID;
    
    Group freeHand;
    Path path;  
    
    public DrawingSurface(Context c) {
        this.context = c;
        groupID = -1;
        path = new Path();

        // Add an event handler to the pane that checks what button is pressed for what to do on mouseclick.
        this.addEventFilter(MouseEvent.MOUSE_PRESSED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                // Asks sidebar what button is down to call appropriate function on surface.
                String depressedButton = context.menuBox.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                    case "freehand": 	freeHandDraw(event);
                                    	break;
                    case "line":    lineClick(event);
                                    break;
                    case "rect":    rectangleClick(event);
                                    break;
                    case "circle":  circleClick(event);
                                    break;
                    case "drag":    dragClick(event);
                                    break;
                    case "new_group":   newGroupClick(event);
                                        break;
                    case "rotate":  rotateClick(event);
                                    break;
                    case "remove":  removeFromGroup(event);
                                    break;
                    case "delete":  delete(event);
                                    break;
                    case "text":    textClick(event);
                                    break;
                    case "add":     addClick(event);
                                    break;

                    default:     System.out.println("Don't know what to do with this click.");
                                 break;
                }
                context.refreshZ();
            }
        });
        
        // Add and event handler to handle drags.
        this.addEventFilter(MouseEvent.MOUSE_DRAGGED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String depressedButton = context.menuBox.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                    case "drag":    dragDrag(event);
                    				context.refreshZ();	
                                    break;
                    case "freehand":    freeHandDrawDrag(event);
                                        break;
                    case "new_group":   newGroupDrag(event);
                                        break;
                }
                context.refreshZ();
            }
        });
        
        // Add an event handler for mouse release.
        this.addEventFilter(MouseEvent.MOUSE_RELEASED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String depressedButton = context.menuBox.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                    case "drag":    dragRelease(event);
                                    break;
                    case "new_group":   newGroupRelease(event);
                                        break;
                }
                context.refreshZ();
            }
        });
        
     // Add an event handler for mouse move.
        this.addEventFilter(MouseEvent.MOUSE_MOVED, new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                String depressedButton = context.menuBox.getDepressedButtonGroup1();
                
                switch (depressedButton) {
                    case "rotate":  rotateMove(event);
                                    break;
                    case "line":    lineMove(event);
                                    break;
                    case "rect":    rectangleMove(event);
                                    break;
                    case "circle":  circleMove(event);
                                    break;
                }
                context.refreshZ();
            }
        });
    }
    
    public void freeHandDraw(MouseEvent event) {
        path = new Path();          //Shouldn't this be placed after the path has had its effects applied?
        
        this.getChildren().add(path);
        
        path.setStrokeWidth(context.menuBox.getThiccness());
        path.setStroke(context.colorPicker.getColor());

        path.getElements().clear();
        path.getElements().add(new MoveTo(event.getX(), event.getY()));
    }
    
    public void freeHandDrawDrag(MouseEvent event) {
        path.getElements().add(new LineTo(event.getX(), event.getY()));
    }
        
    public void addClick(MouseEvent event) { //Does this add things to a group?
        //instanceof checks if something is a instance of some class/interface
        if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
            Node node = (Node) event.getTarget();
            
            if (!(node.getParent() instanceof Group)){
                ElementGroup activeGroup = context.sidebarRight.getActiveGroup();
                if (!activeGroup.getGroupName().equals("none")) {
                    activeGroup.getGroup().getChildren().add(node);
                    activeGroup.highlight();
            
                }
            }
        }
    }
    
    /*  Function that given a mouse even and a surface advances 
     *  the line drawing process on that surface by one click.
     */
    public void lineClick(MouseEvent event) {
        if (context.clickCount == 0){  //What is the point of the clickcount and resetContext? -> Used to check whether
                                       //we're trying to click aand make something or click and grab something.
            context.clickCount++;
            
            Line line = new Line();
            
            line.setStroke(context.colorPicker.getColor());
            line.setStrokeWidth(context.menuBox.getThiccness());
            
            line.setStartX(event.getX());
            line.setStartY(event.getY());
            
            line.setEndX(event.getX());
            line.setEndY(event.getY());
            
            this.getChildren().add(line);
            context.storedNode = line;
        }
        else {
            resetContext(); 
        }
    }
    
    public void lineMove(MouseEvent event) {
        if (context.clickCount == 1) {
            Line line = (Line) context.storedNode;
            line.setEndX(event.getX());
            line.setEndY(event.getY());
        }
    }
    
    public void rectangleClick(MouseEvent event) {
        if (context.clickCount == 0){
            context.clickCount++;
        
            Rectangle rect = new Rectangle();
            
            rect.setStrokeWidth(context.menuBox.getThiccness());
            rect.setStroke(context.colorPicker.getColor());
            rect.setFill(null);
            
            rect.setX(event.getX());
            rect.setY(event.getY());
                        
            context.storedx = event.getX();
            context.storedy = event.getY();
            
            this.getChildren().add(rect);
            context.storedNode = rect;
        }
        else {
            resetContext();
        }
    }
    
    public void rectangleMove(MouseEvent event) {
        if (context.clickCount == 1) {
            Rectangle rect = (Rectangle) context.storedNode;

            double deltax = event.getX() - context.storedx;  
            double deltay = event.getY() - context.storedy;
            //How is the ontext.storedx and y used? If we set them everytime we make a new rectangle or circle, won't we
            //keep checking the most recently created object which might not be the rectangle/circle we want?
            
            // Handle when the user moves the cursor above or to the left of the original point.
            if (deltax < 0) {
                rect.setX(event.getX());
                rect.setWidth(-deltax);
            } else
                rect.setWidth(deltax);
            
            if (deltay < 0) {
                rect.setY(event.getY());
                rect.setHeight(-deltay);
            } else
                rect.setHeight(deltay);
        }       
    }

    public void circleClick(MouseEvent event) {
        if (context.clickCount == 0){
            context.clickCount++;

            Circle cir = new Circle(); 
            
            cir.setStrokeWidth(context.menuBox.getThiccness());
            cir.setStroke(context.colorPicker.getColor());
            cir.setFill(null);
            
            cir.setCenterX(event.getX());
            cir.setCenterY(event.getY());
            
            context.storedx = event.getX();
            context.storedy = event.getY();

            this.getChildren().add(cir);
            context.storedNode = cir;
        }
        else {
            resetContext();
        }
    }     
    
    public void circleMove(MouseEvent event) {
        if (context.clickCount == 1) {
            Circle cir = (Circle) context.storedNode;
            cir.setRadius(Math.sqrt(Math.pow(event.getX() - context.storedx, 2) + Math.pow(event.getY() - context.storedy, 2)));
        }
    }   
    
    /* Function that create a selection box, and the nodes 
     * found inside the box are added to a new group.
     */  
    public void newGroupClick(MouseEvent event) {
        // Create highlight box and store original point.
        if (context.clickCount == 0) {
            context.clickCount = 1;
            
            Rectangle highlightBox = new Rectangle();
            this.getChildren().add(highlightBox);
            highlightBox.setX(event.getX());
            highlightBox.setY(event.getY());
            highlightBox.setFill(new Color(.1, .1, .1, .1));
            highlightBox.setStrokeWidth(1);         
            
            context.storedx = event.getX();
            context.storedy = event.getY();
            context.storedNode = highlightBox;
        }
    }
    
    public void newGroupRelease(MouseEvent event) {
        // Create group of elements that were contained in the highlight box.
        if (context.clickCount == 1) {
            context.storedGroup = new Group();
            
            // Check collisions and add things to group.
            Node[] list = new Node[this.getChildren().size()];
            int i = 0;
            for (Node node : this.getChildren()) {
                if (node instanceof Group || node.equals(context.storedNode)) continue;
                if (node.getBoundsInParent().intersects(context.storedNode.getBoundsInParent())) {
                    list[i++] = node;
                }
            }
            for (Node node : list) {
                if (node != null)
                    context.storedGroup.getChildren().add(node);
            }
            
            // Check to make sure the group is not empty before adding it as a group.
            if (context.storedGroup.getChildren().size() != 0) {
                this.getChildren().add(context.storedGroup);
                context.sidebarRight.items.add(new ElementGroup(context.storedGroup, "Group " + ++groupID));
            }
            this.getChildren().remove(context.storedNode);
            resetContext();
        }
    }
    
    public void newGroupDrag(MouseEvent event) {
        if (context.clickCount == 1 && context.storedNode instanceof Rectangle) {
            Rectangle rectangle = (Rectangle) context.storedNode;
            double deltax = event.getX() - context.storedx;
            double deltay = event.getY() - context.storedy;
            
            // Handle when the user moves the cursor above or to the left of the original point.
            if (deltax < 0) {
                rectangle.setX(event.getX());
                rectangle.setWidth(-deltax);
            } else
                rectangle.setWidth(deltax);
            
            if (deltay < 0) {
                rectangle.setY(event.getY());
                rectangle.setHeight(-deltay);
            } else
                rectangle.setHeight(deltay);
        }
    }
    
    public void removeFromGroup(MouseEvent event) {
        if (event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
            Node node = (Node) event.getTarget();
            
            if (!(node.getParent() instanceof DrawingSurface)) {
                // Move the group transforms down onto the removed element.
                node.setTranslateX(node.getTranslateX() + node.getParent().getTranslateX());
                node.setTranslateY(node.getTranslateY() + node.getParent().getTranslateY());
                node.setRotate(node.getRotate() + node.getParent().getRotate());
                node.setEffect(null);
                Group group = (Group) node.getParent();
                this.getChildren().add(node);
                            
                // If group is now empty delete it.
                if(group.getChildrenUnmodifiable().isEmpty()) {
                    this.getChildren().remove(group);
                    
                    ElementGroup removeMe = null;
                    for (ElementGroup item : context.sidebarRight.items) {
                        if (group.getAccessibleText().equals(item.getGroupName()))
                            removeMe = item;
                    }
                    if (removeMe != null)
                        context.sidebarRight.items.remove(removeMe);
                }
                
                System.out.println("Removed a Node from a group.");
            }
        }
    }
    
    
    // Object / group rotation functions.
    public void rotateClick(MouseEvent event) {
        if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface) && context.clickCount == 0) {
            Node node = (Node) event.getTarget();
            if(node.getParent() instanceof Group) node = node.getParent();
            
            context.storedx = event.getX();
            context.storedy = event.getY();
            
            context.storedNode = node;
            context.clickCount++;
            System.out.println("Rotating a Node");
                
        } else if(context.clickCount == 1) {
            context.resetLastClick();
        }
    }
    
    public void rotateMove(MouseEvent event) {
        if(context.clickCount == 1) {
            context.storedNode.setRotate(Math.sqrt(Math.pow(event.getX() - context.storedx, 2) + Math.pow(event.getY() - context.storedy, 2)));
        }  //This rotates by the square root of the object's X position squared plus the square of its y position
    }
    
    
    // Object / group drag functions. Need one for initial click, drag and release.
    public void dragClick(MouseEvent event) {
        if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
            Node node = (Node) event.getTarget();
            if(node.getParent() instanceof Group) node = node.getParent(); //Shouldn't this be enclose and marked out with {}?
            
            context.storedx = node.getTranslateX() - event.getSceneX();
            context.storedy = node.getTranslateX() - event.getSceneY();
        }
    }
    
    public void dragDrag(MouseEvent event) {
        if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
            Node node = (Node) event.getTarget();
            if(node.getParent() instanceof Group) node = node.getParent(); //Is this suppose to be enclosed in the if?
            
            node.setTranslateX(event.getSceneX() + context.storedx);
            node.setTranslateY(event.getSceneY() + context.storedy);
        }
    }
    
    public void dragRelease(MouseEvent event) {
        if(event.getTarget() instanceof Node && !(event.getTarget() instanceof DrawingSurface)) {
            context.storedx = -1;
            context.storedy = -1;
        }
    }
    
    //delete function (Allows for deletion of groups)
    public void delete(MouseEvent event)
    {
        Node node = (Node) event.getTarget();
        
        if(node.getParent() instanceof Group) {
            Alert alert = new Alert(AlertType.CONFIRMATION);
            alert.setTitle("Warning Dialog");
            alert.setHeaderText("Will delete the entire group.");
            alert.setContentText("If you do this you will delete the entire group. Do you want to continue? "
                    + "(If not try removing the element you want to delete from the group first.)");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() != ButtonType.OK) //Where are the {}?
                return;
            
            Group group = (Group) node.getParent();
            ElementGroup removeMe = null;
            for (ElementGroup item : context.sidebarRight.items) {
                if (group.getAccessibleText().equals(item.getGroupName()))
                    removeMe = item;
            }
            if (removeMe != null)  //Aren't there suppose to be { } here?
                context.sidebarRight.items.remove(removeMe);
            this.getChildren().remove(group);
        } else
            this.getChildren().remove(node);
    }
    
    public void textClick(MouseEvent event) {
        TextInputDialog dialog = new TextInputDialog("text");
        dialog.setTitle("Text Input Dialog");
        dialog.setHeaderText("Look, a Text Input Dialog");
        dialog.setContentText("Please enter desired text:");
        
        Optional<String> result = dialog.showAndWait();
        if (result.isPresent()){
            Text text = new Text();
            text.setText(result.get());
            
            text.setX(event.getX());
            text.setY(event.getY());
            
            text.setStroke(context.colorPicker.getColor());
            text.setScaleX(context.menuBox.getThiccness());
            text.setScaleY(context.menuBox.getThiccness());
            
            this.getChildren().add(text);
        }
    }
    
    public void resetContext() {
        // Reset the context
        context.clickCount = 0;

        context.storedNode = null;
                            
        context.storedx = -1;
        context.storedy = -1;
    }

    public void curve(MouseEvent event)
    {
        
    }
}

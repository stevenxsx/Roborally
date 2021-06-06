package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.ConveyorBelt;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;

public class ConveyorBeltView {
    public static void drawConveyorBeltView(SpaceView spaceView, FieldAction fieldAction) {
        ConveyorBelt conveyorBelt = (ConveyorBelt) fieldAction;
        Heading heading = conveyorBelt.getHeading();
        spaceView.getChildren().clear();

        Image right = new Image("Components/Right.png", 50, 50, true, true);
        Image left = new Image("Components/Left.png", 50, 50, true, true);
        Image Up = new Image("Components/Up.png", 50, 50, true, true);
        Image Down = new Image("Components/Down.png", 50, 50, true, true);

        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        switch(heading){
            case WEST -> gc.drawImage(left, 0,0);
            case EAST -> gc.drawImage(right, 0,0);
            case NORTH -> gc.drawImage(Up, 0,0);
            case SOUTH -> gc.drawImage(Down, 0,0);
        }

        spaceView.getChildren().add(canvas);

    }
}

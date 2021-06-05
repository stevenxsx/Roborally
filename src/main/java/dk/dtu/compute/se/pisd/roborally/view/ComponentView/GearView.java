package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.Gear;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class GearView {
    public static void drawGear(SpaceView spaceView, FieldAction fieldAction) {
        Gear gear = (Gear) fieldAction;
        Heading header = gear.getHeading();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        switch (header) {
            case WEST -> gc.setStroke(Color.GREEN);
            case EAST -> gc.setStroke(Color.RED);
        }
        gc.setLineWidth(7);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeOval(2,2,45,45);

        spaceView.getChildren().add(canvas);

    }
}

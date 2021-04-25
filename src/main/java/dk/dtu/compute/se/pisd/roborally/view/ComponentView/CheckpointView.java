package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class CheckpointView {
    public static void drawCheckpoint(SpaceView spaceView, FieldAction fieldA){
        Checkpoint checkpoint = (Checkpoint) fieldA;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(7);
        gc.setLineCap(StrokeLineCap.ROUND);
        gc.strokeOval(2,2,48,48);
        gc.setStroke(Color.RED);
        gc.setLineWidth(1);
        gc.strokeText(String.valueOf(checkpoint.getCheckpoints()), SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH/2);
        spaceView.getChildren().add(canvas);
    }
}

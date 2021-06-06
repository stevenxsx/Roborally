package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.Checkpoint;
import dk.dtu.compute.se.pisd.roborally.controller.FieldAction;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class CheckpointView {
    public static void drawCheckpoint(SpaceView spaceView, FieldAction fieldA){
        Checkpoint checkpoint = (Checkpoint) fieldA;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_WIDTH);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        try {
            Image checkpointImg = new Image("Components/Checkpoint.png", 50, 50, true, true);
            gc.drawImage(checkpointImg, 0, 0);
        }
        catch (Exception e){
            System.out.print("Checkppoint.png not found.");
        }
        gc.setStroke(Color.GREEN);
        gc.setLineWidth(1);
        gc.strokeText(String.valueOf(checkpoint.getCheckpoints()), SpaceView.SPACE_WIDTH/2, SpaceView.SPACE_WIDTH*0.8);
        spaceView.getChildren().add(canvas);
    }
}

package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.Wall;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class WallView {
    public static void drawWall(SpaceView spaceView, Space space) {
        Wall wallSpace = (Wall) space;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(7);
        gc.setLineCap(StrokeLineCap.ROUND);
        for (Heading header : wallSpace.getHeading()) {
            System.out.println(header.ordinal());
            switch (header) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-48, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-48);
                    break;

                case WEST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-48, SpaceView.SPACE_WIDTH-48, SpaceView.SPACE_HEIGHT-2);
                    break;
                case EAST:
                    gc.strokeLine(48, SpaceView.SPACE_HEIGHT-48, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-2);
                    break;
            }
        }
        spaceView.getChildren().add(canvas);

    }
}

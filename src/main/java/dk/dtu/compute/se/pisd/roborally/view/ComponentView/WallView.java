package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.util.List;

public class WallView {
    public static void drawWall(SpaceView spaceView, Space space) {
        List<Heading> walls = space.getWalls();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.RED);
        gc.setLineWidth(7);
        gc.setLineCap(StrokeLineCap.ROUND);
        for (int i = 0; i < walls.size(); i++) {
            Heading header = walls.get(i);
            switch (header) {
                case SOUTH -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                case NORTH -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 48, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 48);
                case WEST -> gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 48, SpaceView.SPACE_WIDTH - 48, SpaceView.SPACE_HEIGHT - 2);
                case EAST -> gc.strokeLine(48, SpaceView.SPACE_HEIGHT - 48, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
            }
        }
        spaceView.getChildren().add(canvas);

    }
}

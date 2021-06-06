package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.util.List;

public class WallView {
    public static void drawWall(SpaceView spaceView, Space space) {
        List<Heading> walls = space.getWalls();
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Image wallNorthSouth = new Image("Components/WallNORTHSOUTH.png", 50, 50, true, true);
        Image wallWestEast = new Image("Components/WallWESTEAST.png", 50, 50, true, true);

        for (int i = 0; i < walls.size(); i++) {
            Heading header = walls.get(i);
            switch (header) {
                case SOUTH -> gc.drawImage(wallNorthSouth,0,44);
                case NORTH -> gc.drawImage(wallNorthSouth,0,0);
                case WEST ->  gc.drawImage(wallWestEast,0,0);
                case EAST ->  gc.drawImage(wallWestEast,44,0);
            }
        }
        spaceView.getChildren().add(canvas);

    }
}

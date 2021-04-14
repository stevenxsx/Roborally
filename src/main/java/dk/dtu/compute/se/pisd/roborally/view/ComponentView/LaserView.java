package dk.dtu.compute.se.pisd.roborally.view.ComponentView;

import dk.dtu.compute.se.pisd.roborally.model.Components.Laser;
import dk.dtu.compute.se.pisd.roborally.model.Space;
import dk.dtu.compute.se.pisd.roborally.view.SpaceView;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

public class LaserView {
        public  static void drawLaser(SpaceView spaceView, Space space) {
        Laser tempSpace = (Laser) space;
        Canvas canvas = new Canvas(SpaceView.SPACE_WIDTH, SpaceView.SPACE_HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setStroke(Color.YELLOW);
        gc.setLineWidth(5);
        gc.setLineCap(StrokeLineCap.ROUND);/*
        if(tempSpace.getLaserType() == Laser.whatKindOfLaser.START){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-73, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
                case NORTH:

                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;
                case WEST:
                    gc.strokeLine(73, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-73, SpaceView.SPACE_HEIGHT-73);
                    break;
            }
        }
        else if(tempSpace.getLaserType() == Laser.whatKindOfLaser.END){
            switch (tempSpace.getHeadin()) {
                case SOUTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - 2, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - 2);
                    break;
                case NORTH:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-73, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
                case WEST:
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-73, SpaceView.SPACE_HEIGHT-73);
                    break;
                case EAST:
                    gc.strokeLine(73, SpaceView.SPACE_HEIGHT-2, SpaceView.SPACE_WIDTH-2, SpaceView.SPACE_HEIGHT-73);
                    break;
            }
        }
        gc.setStroke(Color.RED);
        switch (tempSpace.getHeadin()){
            case EAST:
            case WEST:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(2, SpaceView.SPACE_HEIGHT - i * 20, SpaceView.SPACE_WIDTH - 2, SpaceView.SPACE_HEIGHT - i * 20);
                }
                break;
            case NORTH:
            case SOUTH:
                for(int i = 1;i<tempSpace.getAmountOFLaser()+1;i++) {
                    gc.strokeLine(SpaceView.SPACE_WIDTH -i*20, 2, SpaceView.SPACE_WIDTH -i*20, SpaceView.SPACE_HEIGHT - 2);
                }
                break;
        }*/
        spaceView.getChildren().add(canvas);
    }
}

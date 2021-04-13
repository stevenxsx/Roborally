package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;

import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class ConveyorBelt extends Space {

    private int velocity;

    Heading heading;

    /**
     * @param board       The board that space is part of.
     * @param x           x-coordinate on the board.
     * @param y           y-coordinate on the board.
     */
    public ConveyorBelt(Board board, int x, int y, int velocity, Heading heading) {
        super(board, x, y);
        this.velocity = velocity;
        this.heading = heading;
    }

    public int getSpeed() {
        return velocity;
    }

    public Heading getHeading() {
        return heading;
    }
}

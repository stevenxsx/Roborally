package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Blueprint for Gear component in RoboRally.
 */
public class Gear extends Space {
    private Heading heading;
    /**
     * Constructor for Space.
     *
     * @param board       The board the space is on.
     * @param x           x-coordinate position.
     * @param y           y-coordinate position.
     */
    public Gear(Board board, int x, int y, Heading heading) {
        super(board, x, y);
        this.heading = heading;
    }
    
    public Heading getHeading(){
        return heading;
    }

}
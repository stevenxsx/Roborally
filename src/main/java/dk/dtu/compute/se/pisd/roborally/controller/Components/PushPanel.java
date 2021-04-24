package dk.dtu.compute.se.pisd.roborally.controller.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

/**
 * Class for the push panel board element
 */
public class PushPanel extends Space {
    private Heading heading;
    /**
     * Constructor for Space.
     *
     * @param board       The board that is being played with.
     * @param x           x-position on board.
     * @param y           y-position on board.
     * @param description
     */
    public PushPanel(Board board, int x, int y, String description, Heading heading) {
        super(board, x, y);
        this.heading = heading;
    }

    public Heading getHeading(){
        return heading;
    }

}

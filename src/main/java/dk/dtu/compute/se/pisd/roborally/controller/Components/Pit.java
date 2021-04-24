package dk.dtu.compute.se.pisd.roborally.controller.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Pit extends Space {
    /**
     * @param board       Board that spaces are put on.
     * @param x           x-position on board.
     * @param y           y-position on board.
     */
    public Pit(Board board, int x, int y, String description) {
        super(board, x, y);
    }


}


package dk.dtu.compute.se.pisd.roborally.controller.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class RebootTokens extends Space {
    /**
     * Constructor for RebootTokens.
     *
     * @param board       The board being played with.
     * @param x           x-axis position on board.
     * @param y           y-axis position on board.
     */
    public RebootTokens(Board board, int x, int y) {
        super(board, x, y);
    }

}

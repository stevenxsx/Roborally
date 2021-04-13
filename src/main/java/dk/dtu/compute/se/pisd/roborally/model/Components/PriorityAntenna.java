package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class PriorityAntenna extends Space {
    /**
     * @param board       Board game is played with.
     * @param x           x-position on board.
     * @param y           y-position on board.
     */
    public PriorityAntenna(Board board, int x, int y) {
        super(board, x, y);
    }
}

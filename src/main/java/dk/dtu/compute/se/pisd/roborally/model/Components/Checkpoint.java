package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Checkpoint extends Space {
    /**
     * @param board       The board that space is part of.
     * @param x           x-coordinate on the board.
     * @param y           y-coordinate on the board.
     */
    public Checkpoint(Board board, int x, int y) {
        super(board, x, y);
    }

}

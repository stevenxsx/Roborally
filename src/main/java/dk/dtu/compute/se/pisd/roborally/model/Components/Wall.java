package dk.dtu.compute.se.pisd.roborally.model.Components;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.List;
import java.util.ArrayList;

public class Wall extends Space {
    /**
     * @author Lucas
     * Needs to be a List or Array of headings - will Implement later.
     */
    private Heading walls;

    public Wall(Board board, int x, int y, Heading walls) {
        super(board, x, y);
        this.walls = walls;
    }

}
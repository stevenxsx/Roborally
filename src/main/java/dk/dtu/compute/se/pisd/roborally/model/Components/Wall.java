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
    private Heading heading[];

    public Wall(Board board, int x, int y, Heading heading[]) {
        super(board, x, y);
        this.heading = heading;
    }
    public Heading[] getHeading(){
        return heading;
    }

}
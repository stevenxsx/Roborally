package dk.dtu.compute.se.pisd.roborally.controller.Components;
import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

import java.util.List;

public class Wall extends Space {
    /**
     * @author Lucas
     * Needs to be a List or Array of headings - will Implement later.
     */
    private List<Heading> heading;

    public Wall(Board board, int x, int y, List<Heading> heading) {
        super(board, x, y);
        this.heading = heading;
    }
    public List<Heading> getHeading(){
        return heading;
    }

}
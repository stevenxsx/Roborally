package dk.dtu.compute.se.pisd.roborally.model;
import java.util.List;
import java.util.ArrayList;

public class Wall extends Space {
    /**
     * @authour Lucas
     * Needs to be a List or Array of headings - will Implement later.
     */
    private Heading walls;

    public Wall(Board board, int x, int y, Heading walls) {
        super(board, x, y);
        this.walls = walls;
    }

    public Heading getWalls() {
        return walls;
    }
}
package dk.dtu.compute.se.pisd.roborally.model.Components;

import dk.dtu.compute.se.pisd.roborally.model.Board;
import dk.dtu.compute.se.pisd.roborally.model.Heading;
import dk.dtu.compute.se.pisd.roborally.model.Space;

public class Laser extends Space {

    private int laserStrength;
    private Heading heading;
    /**
     * Constructor for Laser.
     *
     * @param board       The current board being played on.
     * @param x           x-position on board.
     * @param y           y-position on board.
     * @param laserStrength damage a robot will take if hit by laser.
     * @param heading Used to check direction of laser.
     */
    public Laser(Board board, int x, int y, int laserStrength, Heading heading) {
        super(board, x, y);
        this.laserStrength = laserStrength;
        this.heading = heading;
    }



    public Heading getHeadin() {
        return heading;
    }


    public int getLaserStrength() {
        return laserStrength;
    }


}

package box_pusher;
import java.awt.Color;
/**
 * An interface for representing the different tiles on the game board.
 */
public interface Tile {
    
    /**
     * Gets the signifier of the tile, which represents what the tile is. (Empty space, wall tile, etc.)
     */
    public String getSignifier();

    

    /** 
     * Behavior for what to do when something tries to interact, or move through, this tile.
     * @param otherTile The tile that is trying to interact with this one.
     * @param tileArray The array of tiles to modify.
     */
    public void interact(Tile otherTile, Tile[][] tileArray);


    /**
     * Returns the color of the tile so it can be drawn on the screen.
     */
    public Color getColor();
    
    

}

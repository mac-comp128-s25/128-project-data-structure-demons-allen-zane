package box_pusher;
/**
 * An interface for representing the different tiles on the game board.
 */
public interface Tile {
    
    /**
     * Gets the signifier of the tile, which represents what the tile is. (Empty space, wall tile, etc.)
     */
    public String getSignifier();

    /** 
     * behavior for what to do when something tries to interact, or move through, this tile.
     */
    public void interact(Tile otherTile, Tile[][] tileArray);
    

}

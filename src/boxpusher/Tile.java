package boxpusher;
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
     * Behavior for what to do when something tries to interact with, or move through, this tile.
     * @param otherTile The tile that is trying to interact with this one.
     * @param tileArray The array of tiles to modify.
     */
    public void interact(Tile otherTile, Tile[][] tileArray);


    /**
     * Returns the color of the tile so it can be drawn on the screen.
     */
    public Color getColor();
    
    /**
     * 
     * Returns the row of the Tile in the array.
     */
    public int getIndex1();

    /**
     * 
     * Returns the column of the Tile in the array.
     */
    public int getIndex2();

    /**
     * Sets the current row of the Tile in terms of an Array index.
     */
    public void setIndex1(int newIndex);

    /**
     * sets the current column of the Tile in terms of an Array index.
     */
    public void setIndex2(int newIndex);


}

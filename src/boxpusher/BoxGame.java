package boxpusher;
import edu.macalester.graphics.CanvasWindow;
/**
 * The main class that handles things like managing the tile array, and player movement
 */
public class BoxGame {
    private Tile[][] tileArray;
    private CanvasWindow canvas;
    private Levels levels;

    public BoxGame(){
        levels = new Levels();
        tileArray = levels.getTestLevel1();
        canvas = new CanvasWindow("Box Pusher!", 1000, 1500);
    }

    public Tile[][] getTileArray(){
        return this.tileArray;
    }
    public CanvasWindow getCanvas(){
        return canvas;
    }
    public static void main(String[] args) {
        BoxGame boxGame = new BoxGame();
        TileGraphics.showTiles(boxGame.getTileArray(), boxGame.getCanvas());

        Tile playerTile = Levels.getPlayerTile(boxGame.getTileArray());
        playerTile.interact(playerTile, null);
    }

}

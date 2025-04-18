package boxpusher;
import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.events.Key;
/**
 * The main class that handles things like managing the tile array, and player movement
 */
public class BoxGame {
    private Tile[][] tileArray;
    private CanvasWindow canvas;
    private LevelGenerator levelGenerator;

    //for level gen
    private int levelSize = 10;
    private int walkCount = 10;
    private int minWalkDistance = 1;

    public BoxGame(){
        levelGenerator = new LevelGenerator();
        tileArray = levelGenerator.generate(levelSize, walkCount, minWalkDistance);
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

        boxGame.getCanvas().onKeyDown(event -> boxGame.move(event.getKey()));
    }

    private void move(Key key) {
        Tile playerTile = Levels.getPlayerTile(tileArray);
        if (key.equals(Key.valueOf("DOWN_ARROW")) &&  playerTile.getIndex1()+1 <  tileArray.length){
            Tile interactTile = tileArray[playerTile.getIndex1()+1][playerTile.getIndex2()];
            playerTile.interact(interactTile, tileArray);
        }
        if (key.equals(Key.valueOf("LEFT_ARROW")) &&  playerTile.getIndex2()-1 >= 0){
            Tile interactTile = tileArray[playerTile.getIndex1()][playerTile.getIndex2()-1];
            playerTile.interact(interactTile, tileArray);
        }
        if (key.equals(Key.valueOf("UP_ARROW")) &&  playerTile.getIndex1()-1 >=0){
            Tile interactTile = tileArray[playerTile.getIndex1()-1][playerTile.getIndex2()];
            playerTile.interact(interactTile, tileArray);
        }
        if (key.equals(Key.valueOf("RIGHT_ARROW")) &&  playerTile.getIndex2()+1 < tileArray[0].length){
            Tile interactTile = tileArray[playerTile.getIndex1()][playerTile.getIndex2()+1];
            playerTile.interact(interactTile, tileArray);
        }
        TileGraphics.showTiles(tileArray, canvas);
    }

}

package boxpusher;

import java.util.Arrays;

/**
 * Stores all the arrays for levels in the game, so the BoxGame class can copy them as needed. 
 */
public class Levels {
    private final Tile[][] testLevel1;

    public Levels(){
        testLevel1 = new Tile[][]{{new EmptyTile(0, 0), new EmptyTile(0, 1), new EmptyTile(0, 2)}, 
        {new EmptyTile(1, 0), new PlayerTile(1, 1), new EmptyTile(1, 2)},
        {new EmptyTile(2, 0), new EmptyTile(2, 1), new EmptyTile(2, 2)}};
    }

    public Tile[][] getTestLevel1(){
        Tile[][] arrayCopy = new Tile[3][3];
        for (int i = 0; i < 3; i++){
            arrayCopy[i] = Arrays.copyOf(testLevel1[i], testLevel1[0].length);
        }
        return arrayCopy;
    }
    public static Tile getPlayerTile(Tile[][] tileArray){
        for (int i = 0; i < tileArray.length; i++){
            for (int j = 0; j < tileArray[0].length; j++ ){
                if (tileArray[i][j].getSignifier().equals("Player")){
                    return tileArray[i][j];
                }

            }
        }
        return null;
    }
}

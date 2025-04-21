package boxpusher;

import java.util.Arrays;
import java.util.LinkedList;

/**
 * Stores all the arrays for levels in the game, so the BoxGame class can copy them as needed. 
 */
public class TestLevels {
    private final Tile[][] testLevel1;
    private final Tile[][] testLevel2;

    public TestLevels(){
        testLevel1 = new Tile[][]{{new EmptyTile(0, 0), new EmptyTile(0, 1), new EmptyTile(0, 2)}, 
        {new EmptyTile(1, 0), new PlayerTile(1, 1), new EmptyTile(1, 2)},
        {new EmptyTile(2, 0), new EmptyTile(2, 1), new EmptyTile(2, 2)}};

        testLevel2 = new Tile[][]{{new EmptyTile(0, 0), new EmptyTile(0, 1), new EmptyTile(0, 2), new EmptyTile(0, 3)}, 
        {new EmptyTile(1, 0), new PlayerTile(1, 1), new BoxTile(1, 2), new EmptyTile(1, 3)},
        {new EmptyTile(2, 0), new EmptyTile(2, 1), new EmptyTile(2, 2), new EmptyTile(2, 3)},
        {new EmptyTile(3, 0), new EmptyTile(3, 1), new VictoryTile(3, 2), new EmptyTile(3, 3)}};
    }

    public Tile[][] getTestLevel1(){
        Tile[][] arrayCopy = new Tile[3][3];
        for (int i = 0; i < 3; i++){
            arrayCopy[i] = Arrays.copyOf(testLevel1[i], testLevel1[0].length);
        }
        return arrayCopy;
    }
    public Tile[][] getTestLevel2(){
        Tile[][] arrayCopy = new Tile[4][4];
        for (int i = 0; i < 4; i++){
            arrayCopy[i] = Arrays.copyOf(testLevel2[i], testLevel2[0].length);
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
        return null; //THIS SHOULD NEVER RETURN NULL
    }

        public static VictoryTile getVictoryTile(Tile[][] tileArray){
            for (int i = 0; i < tileArray.length; i++){
                for (int j = 0; j < tileArray[0].length; j++ ){
                    if (tileArray[i][j].getSignifier().equals("Victory")){
                        return (VictoryTile) tileArray[i][j];
                    }
    
                }
            }
            return null; //THIS SHOULD NEVER RETURN NULL
    }

      
    

    
}

package boxpusher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;

import org.junit.jupiter.api.BeforeEach;

public class BoxPusherTest {
    private Levels levels;
    @BeforeEach
    public void setup(){
        levels = new Levels();
    }

    @Test 
    public void playerCanMoveThroughEmpty(){
        Tile[][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = Levels.getPlayerTile(testLevel1);
        int currentPlayerIndex1 = playerTile.getIndex1();
        int currentPlayerIndex2 = playerTile.getIndex2(); 
        playerTile.interact(testLevel1[playerTile.getIndex1()][playerTile.getIndex2()-1], testLevel1);
        assertEquals("Empty", testLevel1[currentPlayerIndex1][currentPlayerIndex2].getSignifier());
    }
    @Test 
    public void findPlayerTest(){
        Tile [][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = Levels.getPlayerTile(testLevel1);
        assertEquals("Player", playerTile.getSignifier());
    }

    @Test
    public void testLevelGeneratorWalk(){
        LevelGenerator levelGenerator = new LevelGenerator();
        levelGenerator.generate();
    }

}

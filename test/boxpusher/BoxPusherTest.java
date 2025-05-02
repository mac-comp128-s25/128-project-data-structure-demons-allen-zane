package boxpusher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;

import org.junit.jupiter.api.BeforeEach;

public class BoxPusherTest {
    private TestLevels levels;
    @BeforeEach
    public void setup(){
        levels = new TestLevels();
    }

    @Test 
    public void playerCanMoveThroughEmpty(){
        Tile[][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = TestLevels.getPlayerTile(testLevel1);
        int currentPlayerIndex1 = playerTile.getIndex1();
        int currentPlayerIndex2 = playerTile.getIndex2(); 
        playerTile.interact(testLevel1[playerTile.getIndex1()][playerTile.getIndex2()-1], testLevel1);
        assertEquals("Empty", testLevel1[currentPlayerIndex1][currentPlayerIndex2].getSignifier());
    }
    @Test 
    public void findPlayerTest(){
        Tile [][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = TestLevels.getPlayerTile(testLevel1);
        assertEquals("Player", playerTile.getSignifier());
    }

    @Test 
    public void findVictoryTest(){
        Tile [][] testLevel2 = levels.getTestLevel2();
        Tile victoryTile = TestLevels.getVictoryTile(testLevel2);
        assertEquals("Victory", victoryTile.getSignifier());
    }

    @Test
    public void generatelevelWithSeed(){
        BetterLevelGenerator levelGenerator = new BetterLevelGenerator();
        levelGenerator.generate(5, 5, 1, 1);
    }

}

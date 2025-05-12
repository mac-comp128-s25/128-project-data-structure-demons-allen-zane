package boxpusher;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.logging.Level;

import org.junit.jupiter.api.BeforeEach;
/**
 * Most of our tests were done graphically, so there aren't many here. 
 */
public class BoxPusherTest {
    private TestLevels levels;
    @BeforeEach
    public void setup(){
        levels = new TestLevels();
    }

    /**
     * Tests if the Player can move through empty tiles. This test was created before it could be tested graphically.
     */
    @Test 
    public void playerCanMoveThroughEmpty(){
        Tile[][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = TestLevels.getPlayerTile(testLevel1);
        int currentPlayerIndex1 = playerTile.getIndex1();
        int currentPlayerIndex2 = playerTile.getIndex2(); 
        playerTile.interact(testLevel1[playerTile.getIndex1()][playerTile.getIndex2()-1], testLevel1);
        assertEquals("Empty", testLevel1[currentPlayerIndex1][currentPlayerIndex2].getSignifier());
    }
    /**
     * Tests the getPlayerTile method of TestLevels.
     */
    @Test 
    public void findPlayerTest(){
        Tile [][] testLevel1 = levels.getTestLevel1();
        Tile playerTile = TestLevels.getPlayerTile(testLevel1);
        assertEquals("Player", playerTile.getSignifier());
    }

    /**
     * Tests the getVictoryTile method of TestLevels.
     */
    @Test 
    public void findVictoryTest(){
        Tile [][] testLevel2 = levels.getTestLevel2();
        Tile victoryTile = TestLevels.getVictoryTile(testLevel2);
        assertEquals("Victory", victoryTile.getSignifier());
    }

    /**
     * Tests seeding.
     */
    @Test
    public void generatelevelWithSeed(){
        BetterLevelGenerator levelGenerator = new BetterLevelGenerator();
        levelGenerator.generate(5, 5, 1, 1);
    }

}

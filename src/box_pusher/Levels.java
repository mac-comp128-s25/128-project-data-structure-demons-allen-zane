package box_pusher;
/**
 * Stores all the arrays for levels in the game, so the BoxGame class can copy them as needed. 
 */
public class Levels {
    private final Tile[][] level1;

    public Levels(){
        level1 = new Tile[5][5];
    }
}

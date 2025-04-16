package boxpusher;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

public class LevelGenerator {

    private final int levelSize = 10;
    private final int walkCount = 10;
    private final int minWalkDistance = 1; //the min. distance for a walk to be for it to be considered valid

    private Tile[][] level = new Tile[levelSize][levelSize];

    private List<Integer> notAllowedDir;
    
    public Tile[][] generate(){
        notAllowedDir = new LinkedList<Integer>(); //setup notAllowedDir

        //init 2D array with empty tiles
        for (int i = 0; i < levelSize; i++) {
            for (int j = 0; j < levelSize; j++) {
                level[i][j] = new EmptyTile(i, j);
            }
        }

        //init player
        final int[] playerPos = {getRandomNumberInRange(0, levelSize), getRandomNumberInRange(0, levelSize)};

        //put the box adjacent to the player (not out of bounds tho)
        int[] tempBoxPos = {playerPos[0], playerPos[1]};
        if (getRandomNumberInRange(0,2) == 0){
            if (tempBoxPos[0] == 9){
                tempBoxPos[0] = 8;
            } else if (tempBoxPos[0] == 0){
                tempBoxPos[0] = 1;
            } else if (getRandomNumberInRange(0,2) == 0){
                tempBoxPos[0] += 1;
            } else {
                tempBoxPos[0] -= 1;
            }
        } else {
            if (tempBoxPos[1] == 9){
                tempBoxPos[1] = 8;
            } else if (tempBoxPos[1] == 0){
                tempBoxPos[1] = 1;
            } else if (getRandomNumberInRange(0,2) == 0){
                tempBoxPos[1] += 1;
            } else {
                tempBoxPos[1] -= 1;
            }
        }
        final int[] boxPos = {tempBoxPos[0], tempBoxPos[1]};
        
        boolean[][] walk = genWalk(playerPos, boxPos);
        
        for (int i = 0; i < walk.length; i++) {
            for (int j = 0; j < walk.length; j++) {
                if(walk[i][j] == true){
                    level[i][j] = new EmptyTile(i, j);
                } else {
                    level[i][j] = new WallTile(i, j);
                }
            }
        }

        level[playerPos[0]][playerPos[1]] = new PlayerTile(playerPos[0], playerPos[1]);
        level[boxPos[0]][boxPos[1]] = new BoxTile(boxPos[0], boxPos[1]);

        return level;
    }

    //recursive method that generates a valid walk
    private boolean[][] genWalk(int[] pPos, int[] bPos){

        boolean[][] walk = new boolean[10][10]; //stores the walk if value is true then has been walked on

        //create new playerPos and boxPos
        int[] playerPos = {pPos[0], pPos[1]};
        int[] boxPos = {bPos[0], bPos[1]};

        for (int i = 0; i < walkCount; i++) {
            walk[playerPos[0]][playerPos[1]] = true;
            walk[boxPos[0]][boxPos[1]] = true;
            move(playerPos, boxPos);
        }

        //check that walk traveled more than minWalkDistance (x and y) -> return our walk
        if(Math.abs(playerPos[0] - pPos[0]) > minWalkDistance){
            if(Math.abs(playerPos[1] - pPos[1]) > minWalkDistance){
                return walk;
            }
        }

        //if not call genWalk again
        //System.out.println("WALK FAILED");
        return genWalk(pPos, bPos);
    }

    //moves the player randomly and makes sure it is valid -> updates positions, if not -> nothing changes
    private void move(int[] pPos, int[] bPos){ 

        //System.out.println("player Pos: " + pPos[0] + ", " + pPos[1] + " box Pos: " + bPos[0] + ", " + bPos[1]);

        //player cannot move out of horizontal bounds
        if(pPos[0] == 9){
            notAllowedDir.add(1);
        } else if(pPos[0] == 0){
            notAllowedDir.add(0);
        }

        //player cannot move out of vertical bounds
        if(pPos[1] == 9){
            notAllowedDir.add(3);
        } else if(pPos[1] == 0){
            notAllowedDir.add(2);
        }

        //make sure moving into a box is valid
        if(pPos[1] == bPos[1]){ //x
            if(bPos[0] == 9 && pPos[0] == 8){
                notAllowedDir.add(1);
            } else if(bPos[0] == 0 && pPos[0] == 1){
                notAllowedDir.add(0);
            }

        } else if(pPos[0] == bPos[0]){ //y
            if(bPos[1] == 9 && pPos[1] == 8){
                notAllowedDir.add(3);
            } else if(bPos[1] == 0 && pPos[1] == 1){
                notAllowedDir.add(2);
            }
        }

        //move player
        int dir = getRandomNewDirection(); //returns one of: 0 = left, 1 = right, 2 = up, 3 = down;
        if(dir == 0){
            pPos[0] -= 1;
            notAllowedDir.add(1);
        } else if(dir == 1){
            pPos[0] += 1;
            notAllowedDir.add(0);
        } else if(dir == 2){
            pPos[1] -= 1;
            notAllowedDir.add(3);
        } else{
            pPos[1] += 1;
            notAllowedDir.add(2);
        }    
        
        //move box if needed
        if(pPos[0] == bPos[0] && pPos[1] == bPos[1]){
            if(dir == 0){
                bPos[0] -= 1;
            } else if(dir == 1){
                bPos[0] += 1;
            } else if(dir == 2){
                bPos[1] -= 1;
            } else{
                bPos[1] += 1;
            }
        }
    }

    //min is inclusive, max is not, for example gRNIR(3,12) can return 3 but not 12
    private int getRandomNumberInRange(int min, int max){ 
        Random random = new Random();
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    public Tile[][] getLevel(){
        return level;
    }

    //same as getRandomNumberInRange but you cant move backwards
    private int getRandomNewDirection(){
        List<Integer> possibleDir = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            possibleDir.add(i);
        }

        possibleDir.removeAll(notAllowedDir);
        notAllowedDir.clear();
        return possibleDir.get(getRandomNumberInRange(0, possibleDir.size()));
    }
}

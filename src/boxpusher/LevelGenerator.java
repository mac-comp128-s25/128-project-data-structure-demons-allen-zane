package boxpusher;

import java.util.Random;

public class LevelGenerator {

    private final int levelSize = 10;
    private final int walkCount = 10;
    private final int minWalkDistance = 1; //the min. distance for a walk to be for it to be considered valid

    private Tile[][] level = new Tile[levelSize][levelSize];
    
    public void generate(){
        //init 2D array with empty tiles
        for (int i = 0; i < levelSize; i++) {
            for (int j = 0; j < levelSize; j++) {
                level[i][j] = new EmptyTile(i, j);
            }
        }

        //init player
        final int[] playerPos = {getRandomNumberInRange(0, levelSize), getRandomNumberInRange(0, levelSize)};
        level[playerPos[0]][playerPos[1]] = new PlayerTile(playerPos[0], playerPos[1]);

        //init box
        int[] boxPos = {playerPos[0], playerPos[1]};
        while(boxPos == playerPos){
            //set new boxPos
            boxPos[0] += getRandomNumberInRange(-1, 2);
            boxPos[1] += getRandomNumberInRange(-1, 2);

            //make sure boxPosX is valid
            if(boxPos[0] > 9 || boxPos[0] < 0){
                boxPos[0] = playerPos[0];
            }
            //make sure boxPosY is valid
            if(boxPos[1] > 9 || boxPos[1] < 0){
                boxPos[1] = playerPos[1];
            }
        }
        //level[boxPos[0]][boxPos[1]] = new BoxTile(boxPos[0], boxPos[1]); TODO: update box tile

        boolean[][] walk = genWalk(playerPos, boxPos);
        
        //TODO: actually generate the level off of our valid walk
    }

    //recursive method that generates a valid walk
    private boolean[][] genWalk(int[] pPos, int[] bPos){

        boolean[][] walk = new boolean[10][10]; //stores the walk if value is true then has been walked on

        //create new playerPos and boxPos
        int[] playerPos = {pPos[0], pPos[1]};
        int[] boxPos = {bPos[0], bPos[1]};

        for (int i = 0; i < walkCount; i++) {
            move(playerPos, boxPos);
        }

        //check that walk traveled more than minWalkDistance (x and y) -> return our walk
        if(Math.abs(playerPos[0] - pPos[0]) > minWalkDistance){
            if(Math.abs(playerPos[1] - pPos[1]) > minWalkDistance){
                return walk;
            }
        }

        //if not call genWalk again
        return genWalk(pPos, bPos);
    }

    //moves the player randomly and makes sure it is valid -> updates positions, if not -> nothing changes
    private void move(int[] pPos, int[] bPos){ 
        int[] newPos = pPos;

        //increment new Pos
        int dir = getRandomNumberInRange(0, 4); //0 = left, 1 = right, 2 = up, 3 = down;
        if(dir == 0){
            newPos[0] -= 1;
        } else if(dir == 1){
            newPos[0] += 1;
        } else if(dir == 2){
            newPos[1] -= 1;
        } else{
            newPos[1] += 1;
        }

        //make sure newPosX is valid
        if(newPos[0] > 9 || newPos[0] < 0){
            newPos[0] = pPos[0];
        }

        //make sure boxPosY is valid
        if(newPos[1] > 9 || newPos[1] < 0){
            newPos[1] = pPos[1];
        }

        //if we move into a box check it is valid
        int[] newBoxPos = newPos;
        if(newPos == bPos){
            if(dir == 0){
                newBoxPos[0] -= 1;
            } else if(dir == 1){
                newBoxPos[0] += 1;
            } else if(dir == 2){
                newBoxPos[1] -= 1;
            } else{
                newBoxPos[1] += 1;
            }

            //make sure newPosX is valid
            if(newBoxPos[0] > 9 || newBoxPos[0] < 0){
                newPos[0] = pPos[0];
                newBoxPos[0] = bPos[0];
            }

            //make sure boxPosY is valid
            if(newBoxPos[1] > 9 || newBoxPos[1] < 0){
                newPos[1] = pPos[1];
                newBoxPos[1] = bPos[1];
            }
        }

        //update to our new positions
        pPos = newPos;
        bPos = newBoxPos;
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
}

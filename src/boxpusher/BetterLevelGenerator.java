package boxpusher;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import java.util.Deque;

public class BetterLevelGenerator {

    private int seed = 0;
    
    private int levelSize;
    private int walkCount;
    private int minWalkDistance; //the min. distance for a walk to be for it to be considered valid

    private Tile[][] level;

    private List<Integer> notAllowedDir;

    public Tile[][] generate(int lvlSize, int wCount, int mWDistance, int seed){
        notAllowedDir = new LinkedList<Integer>(); //setup notAllowedDir\\
        
        this.seed = seed;
        this.levelSize = lvlSize;
        this.walkCount = wCount;
        this.minWalkDistance = mWDistance;
        level = new Tile[levelSize][levelSize];

        //init 2D array with wall tiles
        for (int i = 0; i < levelSize; i++) {
            for (int j = 0; j < levelSize; j++) {
                level[i][j] = new WallTile(i, j);
            }
        }

        //init box
        final int[] boxPos = {getRandomNumberInRange(1, levelSize-1), getRandomNumberInRange(1, levelSize-1)}; //cannot start at edge

        //put the player adjacent to the box (not out of bounds tho)
        int[] tempPlayerPos = {boxPos[0], boxPos[1]};
        if (getRandomNumberInRange(0,2) == 0){
            if (tempPlayerPos[0] == levelSize - 1){
                tempPlayerPos[0] = levelSize - 2;
            } else if (tempPlayerPos[0] == 0){
                tempPlayerPos[0] = 1;
            } else if (getRandomNumberInRange(0,2) == 0){
                tempPlayerPos[0] += 1;
            } else {
                tempPlayerPos[0] -= 1;
            }
        } else {
            if (tempPlayerPos[1] == levelSize - 1){
                tempPlayerPos[1] = levelSize - 2;
            } else if (tempPlayerPos[1] == 0){
                tempPlayerPos[1] = 1;
            } else if (getRandomNumberInRange(0,2) == 0){
                tempPlayerPos[1] += 1;
            } else {
                tempPlayerPos[1] -= 1;
            }
        }

        final int[] playerPos = {tempPlayerPos[0], tempPlayerPos[1]};
        int[] endPos = {0,0};
        
        Deque<int[]> walkTiles = genWalk(playerPos, boxPos, endPos);
        
        while(!walkTiles.isEmpty()){
            int[] tile = walkTiles.pop();
            level[tile[0]][tile[1]] = new EmptyTile(tile[0], tile[1]);
        }

        level[playerPos[0]][playerPos[1]] = new PlayerTile(playerPos[0], playerPos[1]);
        level[boxPos[0]][boxPos[1]] = new BoxTile(boxPos[0], boxPos[1]);
        level[endPos[0]][endPos[1]] = new VictoryTile(endPos[0], endPos[1]);

        Tile[][] levelCopy = new Tile[levelSize][levelSize];
        for (int i = 0; i < levelSize; i++){
            levelCopy[i] = Arrays.copyOf(level[i], level[0].length);
        }
        return levelCopy;
    }

    //recursive method that generates a valid walk
    private Deque<int[]> genWalk(int[] pPos, int[] bPos, int[] ePos){

        Deque<int[]> walkTiles = new ArrayDeque<>(); //stores all tiles that must be empty

        //create new playerPos and boxPos
        int[] playerPos = {pPos[0], pPos[1]};
        int[] boxPos = {bPos[0], bPos[1]};

        for (int i = 0; i < walkCount; i++) {
            walkTiles.push(playerPos.clone());
            walkTiles.push(boxPos.clone());
            move(playerPos, boxPos, walkTiles);
        }

        //update victory tile location
        ePos[0] = boxPos[0];
        ePos[1] = boxPos[1];

        //check that walk traveled more than minWalkDistance (x and y) -> return our walk
        if((Math.abs(playerPos[0] - pPos[0]) + (Math.abs(playerPos[1] - pPos[1]))) > minWalkDistance){
            if(bPos[0] != ePos[0] || bPos[1] != ePos[1]){
                return walkTiles;
            } 
        }

        //if not call genWalk again
        return genWalk(pPos, bPos, ePos);
    }

    //moves the box randomly, then updates
    private void move(int[] pPos, int[] bPos, Deque<int[]> walkTiles){ 

        //box cannot move out of horizontal bounds + gets stuck on vertical edge
        if(bPos[0] == levelSize - 1 || bPos[0] == 0){
            notAllowedDir.add(1);
            notAllowedDir.add(0);
        }

        //box cannot move out of vertical bounds + gets stuck on horizontal edge
        if(bPos[1] == levelSize - 1 || bPos[1] == 0){
            notAllowedDir.add(3);
            notAllowedDir.add(2);
        }

        //make sure moving not moving into player
        if(pPos[1] == bPos[1]){ //x
            if(bPos[0] == pPos[0] + 1){
                notAllowedDir.add(0);
            } else if(bPos[0] == pPos[0] - 1){
                notAllowedDir.add(1);
            }

        } else if(pPos[0] == bPos[0]){ //y
            if(bPos[1] == pPos[1] + 1){
                notAllowedDir.add(2);
            } else if(bPos[1] == pPos[1] - 1){
                notAllowedDir.add(3);
            }
        }

        int[] bPosOld = {bPos[0], bPos[1]}; //store old box pos
        int[] pPosReq = {bPos[0], bPos[1]}; //where the player would have been to push the box

        //move box
        int dir = getRandomNewDirection(); //returns one of: 0 = left, 1 = right, 2 = up, 3 = down; 4 = no possible dir
        if(dir == 4){
            //cannot move
        } else if(dir == 0){
            pPosReq[0] += 1;
            bPos[0] -= 1;
        } else if(dir == 1){
            pPosReq[0] -= 1;
            bPos[0] += 1;
        } else if(dir == 2){
            pPosReq[1] += 1;
            bPos[1] -= 1;
        } else{
            pPosReq[1] -= 1;
            bPos[1] += 1;
        }    
        
        //clear the corner tile in box turning situations
        //pPosReq is where the player must have been to move the box
        //pPos is the players current position
        //bPosOld is the old boxes position a.k.a we cannot go through it

        walkTiles.push(pPosReq.clone());

        if(dir == 0 || dir == 1){ //if box moved left or right
            if(pPosReq[0] > pPos[0]){
                pPos[0]++;
            } else{
                pPos[0]--;
            }

            if(pPos[0] >= 0 && pPos[0] < levelSize && pPos[1] >= 0 && pPos[1] < levelSize){ //out of bounds check
                walkTiles.push(pPos.clone());
            } 

        } else if(dir == 2 || dir == 3){ //if box moved up or down
            if(pPosReq[1] > pPos[1]){
                pPos[1]++;
            } else{
                pPos[1]--;
            }

            if(pPos[0] >= 0 && pPos[0] < levelSize && pPos[1] >= 0 && pPos[1] < levelSize){ //out of bounds check
                walkTiles.push(pPos.clone());
            } 
        }


        //update player pos
        pPos[0] = bPosOld[0];
        pPos[1] = bPosOld[1];

    }

    //min is inclusive, max is not, for example gRNIR(3,12) can return 3 but not 12
    private int getRandomNumberInRange(int min, int max){ 
        Random random;

        if(seed != 0){
            random = new Random(seed);
        } else {
            random = new Random();
        }
        
        int randomNumber = random.nextInt(max - min) + min;
        return randomNumber;
    }

    public Tile[][] getLevel(){
        Tile[][] levelCopy = new Tile[levelSize][levelSize];
        for (int i = 0; i < levelSize; i++){
            levelCopy[i] = Arrays.copyOf(level[i], level[0].length);
        }
        return resetLevel(levelCopy);
    }

    public Tile[][] resetLevel(Tile [][] levelCopy){
        for (int i = 0; i < levelSize; i++){
            for (int j = 0; j < levelSize; j++){
                Tile currentTile = levelCopy[i][j];
                currentTile.setIndex1(i);
                currentTile.setIndex2(j);
            }
        }
        return levelCopy;
    }

    //same as getRandomNumberInRange but you cant move backwards
    private int getRandomNewDirection(){
        List<Integer> possibleDir = new LinkedList<>();
        for (int i = 0; i < 4; i++) {
            possibleDir.add(i);
        }

        possibleDir.removeAll(notAllowedDir);
        notAllowedDir.clear();

        if(possibleDir.size() == 0){
            return 4;
        }

        return possibleDir.get(getRandomNumberInRange(0, possibleDir.size()));
    }
}
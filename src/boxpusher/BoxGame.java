package boxpusher;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;
/**
 * The main class that handles things like managing the tile array, and player movement
 */
public class BoxGame {
    private Tile[][] tileArray;
    private CanvasWindow canvas;
    private BetterLevelGenerator levelGenerator;

    //for level gen
    private int levelSize = 5;
    private int walkCount = 5;
    private int minWalkDistance = 1;
    private boolean canMove;

    private final double time = 10;
    private double timer = time;
    private Integer score;

    public BoxGame(){
        levelGenerator = new BetterLevelGenerator();
        canMove = true;
        tileArray = levelGenerator.generate(levelSize, walkCount, minWalkDistance, 0);
        canvas = new CanvasWindow("Box Pusher!", 1000, 1500);
        score = 0;
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

        boxGame.updateTiles();

    }

    public void switchLevel(){
        levelSize++;
        walkCount += 4;
        minWalkDistance++;
        tileArray = levelGenerator.generate(levelSize, walkCount, minWalkDistance, 0);
        timer +=5;
    }

    public void updateTiles(){
        GraphicsText timerText = new GraphicsText(String.format("%2$,3.2f %1$s", "seconds left", timer), 50, 50);
        GraphicsText scoreText = new GraphicsText("Score: " + score.toString() + " points", 75, 75);
        scoreText.setFillColor(Color.BLACK);
        canvas.add(timerText);
        canvas.add(scoreText);
        timerText.setFillColor(Color.BLACK);
        canvas.animate((deltaTime)->{
            timerText.setText(String.format("%2$,3.2f %1$s", "seconds left", timer));
            scoreText.setText("Score: " + score.toString() + " points");
            timer-=deltaTime;
            if (timer <= 0 && canMove){
                canvas.removeAll();
                GraphicsText gameOverText = new GraphicsText("Game Over!");
                
                gameOverText.setFillColor(Color.RED);
                gameOverText.setFontSize(50);
                gameOverText.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
                canvas.add(gameOverText);
                scoreText.setCenter(canvas.getWidth()/2, gameOverText.getY()+50);
                canvas.add(scoreText);
                canMove = false;
            }
            if (TestLevels.getVictoryTile(tileArray).getWinStatus()){
                switchLevel();
                score++;
                TileGraphics.showTiles(tileArray, canvas);
            }
        
        });
    }

    private void move(Key key) {
        if (canMove){
            Tile playerTile = TestLevels.getPlayerTile(tileArray);
            if (key.equals(Key.valueOf("R"))){
                tileArray = levelGenerator.getLevel();
            }
            if (key.equals(Key.valueOf("DOWN_ARROW")) &&  playerTile.getIndex2()+1 <  tileArray.length){
                Tile interactTile = tileArray[playerTile.getIndex1()][playerTile.getIndex2()+1];
                playerTile.interact(interactTile, tileArray);
            }
            if (key.equals(Key.valueOf("LEFT_ARROW")) &&  playerTile.getIndex1()-1 >= 0){
                Tile interactTile = tileArray[playerTile.getIndex1()-1][playerTile.getIndex2()];
                playerTile.interact(interactTile, tileArray);
            }
            if (key.equals(Key.valueOf("UP_ARROW")) &&  playerTile.getIndex2()-1 >=0){
                Tile interactTile = tileArray[playerTile.getIndex1()][playerTile.getIndex2()-1];
                playerTile.interact(interactTile, tileArray);
            }
            if (key.equals(Key.valueOf("RIGHT_ARROW")) &&  playerTile.getIndex1()+1 < tileArray[0].length){
                Tile interactTile = tileArray[playerTile.getIndex1()+1][playerTile.getIndex2()];
                playerTile.interact(interactTile, tileArray);
            }
            TileGraphics.showTiles(tileArray, canvas);
        }
        }
    }



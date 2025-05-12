package boxpusher;
import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;
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
    private int incrementSizeOrNot;
    private boolean canMove;

    private final double TIME = 10;
    private double timer = TIME;
    private double timeToAdd = 5;
    private Integer score;
    boolean doTheCanvasAnimateCallYesOrNo = true;
    private GraphicsText timerText;
    private GraphicsText scoreText;

    private final int canvasSize = 1000;

    public BoxGame(){
        levelGenerator = new BetterLevelGenerator();
        incrementSizeOrNot = 0;
        canMove = false;
        canvas = new CanvasWindow("Box Pusher!", canvasSize, canvasSize);
        score = 0;
        timerText = new GraphicsText(String.format("%2$,3.2f %1$s", "seconds left", timer), 25, 50);
        scoreText = new GraphicsText("Score: " + score.toString() + " points", 25, 75);
    }

    public Tile[][] getTileArray(){
        return this.tileArray;
    }

    public CanvasWindow getCanvas(){
        return canvas;
    }

    public static void main(String[] args) {
        BoxGame boxGame = new BoxGame();
        TileGraphics.showTitle(boxGame.getCanvas(), boxGame);
    }
    /**
     * Initializes the game. This includes drawing the first level, drawing UI elements, allowing the player to move, and starting the timer.
     */
    public void init(){
        canMove = true;
        canvas.removeAll();
        tileArray = levelGenerator.generate(levelSize, walkCount, minWalkDistance, 0);
        TileGraphics.showTiles(tileArray, canvas, levelSize, canvasSize);
        if (doTheCanvasAnimateCallYesOrNo == true){
            canvas.onKeyDown(event -> move(event.getKey()));
        }
        timer = TIME;
        updateTiles();
    }
    /**
     * Generates a new level, and switches to it.
     */
    public void switchLevel(){
        incrementSizeOrNot++;
        if (incrementSizeOrNot%3 == 0){
            levelSize++;
            minWalkDistance++;
        }
        walkCount += 4;
        tileArray = levelGenerator.generate(levelSize, walkCount, minWalkDistance, 0);
        timer +=timeToAdd;
    }

    /**
     * Updates the tiles due to movement.
     */
    public void updateTiles(){
        scoreText.setFillColor(Color.BLACK);
        scoreText.setFontSize(25);
        scoreText.setCenter((canvas.getWidth()/5)*2,25);
        timerText.setFillColor(Color.BLACK);
        timerText.setCenter((canvas.getWidth()/5)*3,27);
        timerText.setFontSize(25);
        canvas.add(timerText);
        canvas.add(scoreText);
        if (doTheCanvasAnimateCallYesOrNo == true){
            doTheCanvasAnimateCallYesOrNo = false;
            canvas.animate((deltaTime)->{
                timerText.setText(String.format("%2$,3.2f %1$s", "seconds left", timer));
                scoreText.setText("Score: " + score.toString() + " points");
                timer-=deltaTime;
                if (timer <= 0 && canMove){
                    canvas.removeAll();
                    TileGraphics.removeCanvasObjects();
                    GraphicsText gameOverText = new GraphicsText("Game Over!");
                    
                    gameOverText.setFillColor(Color.RED);
                    gameOverText.setFontSize(50);
                    gameOverText.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
                    canvas.add(gameOverText);
                    scoreText.setCenter(canvas.getWidth()/2, gameOverText.getY()+50);
                    canvas.add(scoreText);
                    canMove = false;

                    Button startButton = new Button("Restart");
                    canvas.add(startButton);
                    startButton.setCenter(canvas.getWidth()/2, scoreText.getY()+50);
                    startButton.onClick(() -> init());
                }
                if (TestLevels.getVictoryTile(tileArray).getWinStatus()){
                    switchLevel();
                    score++;
                    TileGraphics.showTiles(tileArray, canvas, levelSize, canvasSize);
                }
            
            });
    }
    }
    

    /**
     * Allows the player to move the player tile.
     * @param key the key pressed, so the function knows which direction to move in.
     */
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
            TileGraphics.showTiles(tileArray, canvas, levelSize, canvasSize);
        }
        }
    }



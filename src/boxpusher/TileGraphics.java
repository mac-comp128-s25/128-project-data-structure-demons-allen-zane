package boxpusher;


import java.awt.Color;
import java.util.ArrayDeque;
import java.util.Queue;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.ui.Button;

public class TileGraphics {
    private static final double TILE_SPACING = 70;
    private static final double TILE_SIZE = 50;

    private static Queue<Rectangle> canvasObjects = new ArrayDeque<Rectangle>();
    
    public static void showTitle(CanvasWindow canvas, BoxGame boxGame){
        GraphicsText titleText = new GraphicsText("Box Pusher!");
                
        titleText.setFillColor(Color.RED);
        titleText.setFontSize(50);
        titleText.setCenter(canvas.getWidth()/2, canvas.getHeight()/2);
        canvas.add(titleText);
        Button startButton = new Button("Start");
        canvas.add(startButton);
        startButton.setCenter(canvas.getWidth()/2, titleText.getY()+50);
        startButton.onClick(() -> boxGame.init());
    }
    public static void removeCanvasObjects(){
        if (!canvasObjects.isEmpty()){
            while(!canvasObjects.isEmpty()){
                canvasObjects.remove();
            }
        }
    }
    public static void showTiles(Tile[][] tileArray, CanvasWindow canvas){
        
        if (!canvasObjects.isEmpty()){
            while(!canvasObjects.isEmpty()){
                canvas.remove(canvasObjects.remove());
            }
        }

        double currentX = 0;
        double currentY = 50 ;
        for (int i = 0; i < tileArray.length; i++){
            if (i != 0){
                currentY += TILE_SPACING;
            }
            currentX = canvas.getWidth()/5;
            for (int j = 0; j < tileArray[i].length; j++){
                Tile currentTile = tileArray[j][i];
                Rectangle currentRectangle = new Rectangle(currentX, currentY, TILE_SIZE, TILE_SIZE);
                currentRectangle.setFillColor(currentTile.getColor());
                canvas.add(currentRectangle);
                canvasObjects.add(currentRectangle);
                currentX+= TILE_SPACING;
            }
        }
    }
}

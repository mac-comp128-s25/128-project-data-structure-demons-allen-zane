package boxpusher;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.Point;
import edu.macalester.graphics.Rectangle;

public class TileGraphics {
    private static final double TILE_SPACING = 70;
    private static final double TILE_SIZE = 50;
    

    public static void showTiles(Tile[][] tileArray, CanvasWindow canvas){
        double currentX = 0;
        double currentY = canvas.getHeight()/3;
        for (int i = 0; i < tileArray.length; i++){
            if (i != 0){
                currentY += TILE_SPACING;
            }
            currentX = canvas.getWidth()/5;
            for (int j = 0; j < tileArray[i].length; j++){
                Tile currentTile = tileArray[i][j];
                Rectangle currentRectangle = new Rectangle(currentX, currentY, TILE_SIZE, TILE_SIZE);
                currentRectangle.setFillColor(currentTile.getColor());
                canvas.add(currentRectangle);
                currentX+= TILE_SPACING;
            }
        }
    }
}

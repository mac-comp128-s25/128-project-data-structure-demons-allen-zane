package boxpusher;

import java.awt.Color;

import edu.macalester.graphics.events.Key;

public class BoxTile implements Tile{

    private String signifier;
    private Color color;
    private int index1;
    private int index2;

    public BoxTile(int index1, int index2){
        color = new Color(110, 71, 0);
        signifier = "Box";
        this.index1 = index1;
        this.index2 = index2;
    }
    @Override
    public String getSignifier() {
        return signifier;
    }

    @Override
    public void interact(Tile otherTile, Tile[][] tileArray) {
        Tile playerTile = Levels.getPlayerTile(tileArray);
        int indexDist1 = index1-playerTile.getIndex1();
        int indexDist2 = index2-playerTile.getIndex2();
        if(otherTile.getSignifier().equals("Player")){
            if ((indexDist2 == index2+1) &&  index2+1 < tileArray.length){
                Tile interactTile = tileArray[index1][index2+1];
                interactTile.interact(this, tileArray);
            }
            if ((indexDist1 == index1+1) &&  index1+1 < tileArray.length){
                Tile interactTile = tileArray[index1+1][index2];
                interactTile.interact(this, tileArray);
            }
            if ((indexDist1 == index1-1) &&  index1-1 >= 1){
                Tile interactTile = tileArray[index1+1][index2];
                interactTile.interact(this, tileArray);
            }
            if ((indexDist2 == index2-1) &&  index2-1 >=1){
                Tile interactTile = tileArray[index1][index2-1];
                interactTile.interact(this, tileArray);
            }
        }
    }

    @Override
    public Color getColor() {
       return color;
    }

    @Override
    public int getIndex1() {
        return index1;
    }

    @Override
    public int getIndex2() {
        return index2;
    }

    @Override
    public void setIndex1(int newIndex) {
       index1 = newIndex;
    }

    @Override
    public void setIndex2(int newIndex) {
        index2 = newIndex;
    }

}

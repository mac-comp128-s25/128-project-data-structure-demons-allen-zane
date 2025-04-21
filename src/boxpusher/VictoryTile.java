package boxpusher;

import java.awt.Color;

public class VictoryTile implements Tile{

    private int index1;
    private int index2;
    private Color color;
    private Color color2;
    private String signifier;
    private boolean hasWon;
    public VictoryTile(int index1, int index2){
        this.index1 = index1;
        this.index2 = index2;
        color = Color.BLUE;
        color2 = Color.WHITE;
        signifier = "Victory";
        hasWon = false;
    }
    @Override
    public String getSignifier() {
       return signifier;
    }

    @Override
    public void interact(Tile otherTile, Tile[][] tileArray) {
        if (otherTile.getSignifier().equals("Box")){
            tileArray[otherTile.getIndex1()][otherTile.getIndex2()] = new EmptyTile(otherTile.getIndex1(), otherTile.getIndex2());
            tileArray[index1][index2] = otherTile;
            tileArray[0][0] = this;
            otherTile.setIndex1(0);
            otherTile.setIndex2(0);
            otherTile.setIndex1(index1);
            otherTile.setIndex2(index2);
            hasWon = true;
        }
    }

    @Override
    public Color getColor() {
        
        if (!hasWon) return color;
         else return color2;
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

    public boolean getWinStatus(){
        return hasWon;
    }

}

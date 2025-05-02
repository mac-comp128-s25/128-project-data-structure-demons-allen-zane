package boxpusher;

import java.awt.Color;

public class PlayerTile implements Tile{

    private int index1;
    private int index2;
    private Color color;
    private String signifier;
    public PlayerTile(int index1, int index2){
        this.index1 = index1;
        this.index2 = index2;
        color = Color.RED;
        signifier = "Player";
    }
    @Override
    public String getSignifier() {
        return signifier;
    }

    @Override
    public void interact(Tile otherTile, Tile[][] tileArray) {
        otherTile.interact(this, tileArray);
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

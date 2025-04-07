package boxpusher;
import java.awt.Color;

public class EmptyTile implements Tile{


    private String signifier;
    private Color color;
    private int index1;
    private int index2;
    public EmptyTile(int index1, int index2){
        color = Color.WHITE;
        signifier = "Empty";
        this.index1 = index1;
        this.index2 = index2;
    }
    @Override
    public String getSignifier() {
        return signifier;
    }

    @Override
    public void interact(Tile otherTile, Tile[][] tileArray) {
        int index1Copy = index1;
        int index2Copy = index2;
        tileArray[index1][index2] = otherTile;
        tileArray[otherTile.getIndex1()][otherTile.getIndex2()] = this;
        setIndex1(otherTile.getIndex1());
        setIndex2(otherTile.getIndex2());
        otherTile.setIndex1(index1Copy);
        otherTile.setIndex2(index2Copy);
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

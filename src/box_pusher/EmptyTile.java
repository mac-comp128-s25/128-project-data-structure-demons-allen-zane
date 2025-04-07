package box_pusher;

public class EmptyTile implements Tile{


    private String signifier;
    private Color color;

    public EmptyTile(){
        color = Color.WHITE;
        signifier = "Empty";
    }
    @Override
    public String getSignifier() {
        return signifier;
    }

    @Override
    public void interact(Tile otherTile, Tile[][] tileArray) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'interact'");
    }

    @Override
    public Color getColor() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'getColor'");
    }
    
}

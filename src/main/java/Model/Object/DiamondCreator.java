package Model.Object;

public class DiamondCreator implements ObjectGameCreator{
    @Override
    public Diamond createObject(int posX, int posY) {
        return new Diamond(posX, posY);
    }
}

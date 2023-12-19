package Model.Object;

public class ChestCreator implements ObjectGameCreator{
    @Override
    public Chest createObject(int posX, int posY) {
        return new Chest(posX,posY);
    }
}

package Model.Object;

public class MeatCreator implements ObjectGameCreator{
    @Override
    public Meat createObject(int posX, int posY) {
        return new Meat(posX, posY);
    }
}

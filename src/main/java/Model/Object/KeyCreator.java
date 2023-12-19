package Model.Object;

public class KeyCreator implements ObjectGameCreator{
    @Override
    public Key createObject(int posX, int posY) {
        return  new Key(posX, posY);
    }
}

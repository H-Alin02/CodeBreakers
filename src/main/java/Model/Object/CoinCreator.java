package Model.Object;

public class CoinCreator implements ObjectGameCreator{
    @Override
    public Coin createObject(int posX, int posY) {
        return new Coin(posX,posY);
    }
}

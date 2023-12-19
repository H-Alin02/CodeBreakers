package Model.Object;

public class MoneyCreator implements ObjectGameCreator{
    @Override
    public Money createObject(int posX, int posY) {
        return new Money(posX, posY);
    }
}

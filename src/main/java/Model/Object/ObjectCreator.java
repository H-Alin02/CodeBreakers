package Model.Object;

public class ObjectCreator implements ObjectGameCreator{

    @Override
    public ObjectGame createObject(String nameObject, int posX, int posY) {
        if (nameObject == null){
            return null;
        }
        else if (nameObject.equalsIgnoreCase("chest")){
            return new Chest(posX,posY);
        } else if (nameObject.equalsIgnoreCase("coin")) {
            return new Coin(posX,posY);
        } else if (nameObject.equalsIgnoreCase("diamond")) {
            return new Diamond(posX,posY);
        } else if (nameObject.equalsIgnoreCase("key")) {
            return new Key(posX,posY);
        } else if (nameObject.equalsIgnoreCase("meat")) {
            return new Meat(posX,posY);
        }else if (nameObject.equalsIgnoreCase("money")){
            return new Money(posX,posY);
        }
        return null;
    }
}

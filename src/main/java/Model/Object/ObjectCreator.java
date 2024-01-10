package Model.Object;

public class ObjectCreator implements ObjectGameCreator{

    @Override
    public GameObject createObject(String nameObject, int posX, int posY) {
        if (nameObject == null){
            return null;
        } else if (nameObject.equalsIgnoreCase("coin")) {
            return new Coin(posX,posY);
        }  else if (nameObject.equalsIgnoreCase("key")) {
            return new Key(posX,posY);
        } else if (nameObject.equalsIgnoreCase("meat")) {
            return new Meat(posX,posY);
        } else if (nameObject.equalsIgnoreCase("ammunition")){
            return new Ammunition(posX,posY);
        }else if (nameObject.equalsIgnoreCase("medikit")){
            return new Medikit(posX,posY);
        }else if (nameObject.equalsIgnoreCase("keyUsb")){
            return new KeyUSB(posX,posY);
        }
        return null;
    }
}

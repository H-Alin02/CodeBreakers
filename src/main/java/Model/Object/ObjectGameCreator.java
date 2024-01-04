package Model.Object;

import java.lang.reflect.InvocationTargetException;

public interface ObjectGameCreator {
    GameObject createObject(String nameObject,int posX, int posY);

    public <T extends GameObject> GameObject
    createObject(Class<T> type, int posX, int posY)
            throws NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException;
}

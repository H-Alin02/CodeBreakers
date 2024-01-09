package Model.Object;

import com.badlogic.gdx.utils.Array;

public class Item {

    private final Array<GameObject> items;

    public <T extends GameObject> void add(T item)
    {
        items.add(item);
        System.out.println(item.getName() + " picked, number of items: " + items.size);
    }

    public Item(){
        this.items = new Array<>();
    }

    public <T extends GameObject> int typeCount(Class<T> type)
    {
        int value = 0;

        for (GameObject item : items)
            if(type.isInstance(item))
                value ++;

        return value;
    }

    public <T extends GameObject> int typeValue(Class<T> type)
    {
        int value = 0;

        for (GameObject item : items)
            if(type.isInstance(item))
                value += item.getValue();

        return value;
    }

    public int getCoin() {
        return typeValue(Coin.class);
    }

    public int getKey() { return typeCount(Key.class); }

    public int getAmmunition(){
        return typeValue(Ammunition.class);
    }
    public void update(float delta){

    }
}

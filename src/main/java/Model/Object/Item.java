package Model.Object;

import Model.Player;
import com.badlogic.gdx.utils.Array;

public class Item {
    private Array<Coin> coin;
    private Array<Diamond> diamond;
    private Array<Money> money;
    private Array<Key> key;
    private Array<Ammunition> ammunition;
    private Player player;

    public Item(){
        this.coin = new Array<>();
        this.diamond = new Array<>();
        this.money = new Array<>();
        this.key = new Array<>();
        this.ammunition = new Array<>();
        this.player = Player.getInstance();
    }

    public void addCoin(ObjectGame obj){
        coin.add((Coin) obj);
        System.out.println("coin " + coin.size);
    }

    public void addDiamond(ObjectGame obj){
        diamond.add((Diamond) obj);
        System.out.println("diamond "+ diamond.size);
    }

    public void addMoney(ObjectGame obj){
        money.add((Money) obj);
        System.out.println("money " + money.size);
    }

    public void addKey(ObjectGame obj){
        key.add((Key) obj);
        System.out.println("key " + key.size);
    }

    public void addAmmunition(ObjectGame obj){
        ammunition.add((Ammunition) obj);
        int value = 0;
        for (Ammunition a : ammunition){
            value += a.getValue();
        }
        player.setBulletCount(value);
    }

    public int getCoin() {
        int value = 0;
        for (Coin c : coin){
            value += c.getValue();
        }
        return value;
    }

    public int getDiamond() {
        return diamond.size;
    }

    public int getMoney() {
        int value = 0;
        for (Money m : money){
            value += m.getValue();
        }
        return value;
    }

    public int getKey() {
        return key.size;
    }

    public int getAmmunition(){
        return player.getBulletCount();
    }
    public void update(float delta){
        getCoin();
        getKey();
        getMoney();
        getDiamond();
        getAmmunition();
    }
}

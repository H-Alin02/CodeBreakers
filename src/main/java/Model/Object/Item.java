package Model.Object;

import com.badlogic.gdx.utils.Array;

public class Item {
    private Array<Coin> coin;
    private Array<Diamond> diamond;
    private Array<Money> money;
    private Array<Key> key;
    private Array<Ammunition> ammunition;


    public Item(){
        this.coin = new Array<>();
        this.diamond = new Array<>();
        this.money = new Array<>();
        this.key = new Array<>();
        this.ammunition = new Array<>();
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
    }

    public int getCoin() {
        return coin.size;
    }

    public int getDiamond() {
        return diamond.size;
    }

    public int getMoney() {
        return money.size;
    }

    public int getKey() {
        return key.size;
    }

    public int getAmmunition(){
        return  ammunition.size;
    }
    public void update(float delta){
        getCoin();
        getKey();
        getMoney();
        getDiamond();
        getAmmunition();
    }
}

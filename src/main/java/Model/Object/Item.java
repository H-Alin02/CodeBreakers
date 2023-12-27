package Model.Object;

import com.badlogic.gdx.utils.Array;

public class Item {
    private Array<Coin> coin;
    private Array<Diamond> diamond;
    private Array<Money> money;
    private Array<Meat> meat;
    private Array<Key> key;
    private Array<Ammunition> ammunition;
    private Array<Medikit> medikit;

    public Item(){
        this.coin = new Array<>();
        this.diamond = new Array<>();
        this.money = new Array<>();
        this.meat = new Array<>();
        this.key = new Array<>();
        this.ammunition = new Array<>();
        this.medikit = new Array<>();
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

    public void addMeat(ObjectGame obj){
        meat.add((Meat) obj);
        System.out.println("meat " + meat.size);
    }

    public void addKey(ObjectGame obj){
        key.add((Key) obj);
        System.out.println("key " + key.size);
    }

    public void addAmmunition(ObjectGame obj){
        ammunition.add((Ammunition) obj);
    }
    public void addMedikit(ObjectGame obj){
        medikit.add((Medikit) obj);
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

    public int getMeat() {
        return meat.size;
    }

    public int getKey() {
        return key.size;
    }

    public int getAmmunition(){
        return  ammunition.size;
    }
    public int getMedikit(){
        return medikit.size;
    }

    public void update(float delta){
        getCoin();
        getKey();
        getMeat();
        getMoney();
        getDiamond();
        getAmmunition();
        getMedikit();
    }
}

package Model.Object;

import Model.SoundPlayer;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Coin implements GameObject{
    private final Animation<TextureRegion> coin;
    private int coinX, coinY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private float stateTime;
    private boolean remove;
    private final float SCALE = 1.2f;
    private String name ;
    private int coinValue;
    public Coin(int coinX, int coinY) {
        this.coinX = coinX;
        this.coinY = coinY;

        //Animation coin
        Array<TextureRegion> coinFrames = new Array<>();
        for (int i = 0; i<=13; i++){
            coinFrames.add(new TextureRegion(new Texture("object/coin/frame" + i + ".png")));
        }
        coin = new Animation<>(0.05f, coinFrames, Animation.PlayMode.LOOP);

        name = "coin";
        remove = false;
        coinValue = 50;

    }

    public SoundPlayer getPickSound()
    {
        return new SoundPlayer("sound_effects/pick_coin.wav");
    }

    public void update(float delta){
        stateTime += delta;
    }

    @Override
    public Rectangle getArea() {
        return new Rectangle(coinX, coinY,OBJECT_WIDTH, OBJECT_HEIGHT);
    }

    @Override
    public boolean isRemove() {
        return remove;
    }

    @Override
    public void setRemove(boolean b) {
        remove = b;
    }

    public void draw(SpriteBatch batch) {
        batch.draw(getKeyFrame(),coinX,coinY,OBJECT_WIDTH * SCALE,OBJECT_HEIGHT * SCALE);
    }

    public TextureRegion getKeyFrame() {
        return coin.getKeyFrame(stateTime, true);
    }
    @Override
    public String getName(){return name;}

    @Override
    public int getValue(){
        return coinValue;
    }

    @Override
    public void setValue(int value) {
        coinValue += value;
    }
}

package Model.Object;

import Model.Player;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

public class Coin implements ObjectGame{
    private final Animation<TextureRegion> coin;
    private int coinX, coinY;
    private final int OBJECT_HEIGHT = 32;
    private final int OBJECT_WIDTH = 32;
    private float stateTime;
    private boolean remove;
    private final float SCALE = 1.2f;

    public Coin(int coinX, int coinY) {
        this.coinX = coinX;
        this.coinY = coinY;

        //Animation coin
        Array<TextureRegion> coinFrames = new Array<>();
        for (int i = 0; i<=13; i++){
            coinFrames.add(new TextureRegion(new Texture("object/coin/frame" + i + ".png")));
        }
        coin = new Animation<>(0.05f, coinFrames, Animation.PlayMode.LOOP);

        remove = false;

    }

    public void update(float delta){
        stateTime += delta;
    }

    @Override
    public boolean collide(Player player) {
        Rectangle rect = getArea();
        Rectangle rect2 = player.getArea();

        return rect2.overlaps(rect);
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




}

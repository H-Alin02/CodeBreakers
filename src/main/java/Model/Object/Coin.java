package Model.Object;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.Array;

public class Coin {
    private final Animation<TextureRegion> coin;
    private int coinX, coinY;
    private float stateTime;
    private boolean visibility = false;
    public Coin(int coinX, int coinY) {
        this.coinX = coinX;
        this.coinY = coinY;

        //Animation coin
        Array<TextureRegion> coinFrames = new Array<>();
        for (int i = 0; i<=13; i++){
            coinFrames.add(new TextureRegion(new Texture("object/coin/frame" + i + ".png")));
        }
        coin = new Animation<>(0.01f, coinFrames, Animation.PlayMode.LOOP);

    }

    public void update(float delta){
        stateTime += delta;
    }
    public void draw(SpriteBatch batch) {
        batch.draw(getKeyFrame(),coinX,coinY,32,32);
    }

    public TextureRegion getKeyFrame() {
        return coin.getKeyFrame(stateTime, true);
    }

    public boolean isVisible(){
        return visibility;
    }
    public void setVisibility(boolean b){visibility = b;}


}

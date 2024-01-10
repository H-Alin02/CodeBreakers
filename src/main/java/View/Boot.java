package View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {
    //Singleton pattern
    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private OrthographicCamera orthographicCamera;
    private GameScreen gameScreen;

    public Boot(){
        INSTANCE = this;
    }

    @Override
    public void create() {

        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false,screenWidth,screenHeight);
        //setScreen(new GameScreen(orthographicCamera));
        setScreen(new MainMenuScreen(orthographicCamera));
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public GameScreen getGameScreen() {
        return gameScreen;
    }

    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}

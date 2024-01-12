package View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

public class Boot extends Game {
    //Singleton pattern
    public static Boot INSTANCE = new Boot();
    private static int screenWidth, screenHeight;
    private static GameScreen gameScreen;

    public Boot() {}

    @Override
    public void create() {

        screenWidth = Gdx.graphics.getWidth();
        screenHeight = Gdx.graphics.getHeight();
        OrthographicCamera orthographicCamera = new OrthographicCamera();
        orthographicCamera.setToOrtho(false,screenWidth,screenHeight);
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
        Boot.gameScreen = gameScreen;
    }
}

package View;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;

/**
 * La classe `Boot` rappresenta la classe principale dell'applicazione, estendendo la classe `Game` di LibGDX.
 * Gestisce la creazione dell'applicazione e la transizione tra le schermate principali.
 * @author Alin Marian Habasescu
 * @author Francesco Gambone
 */
public class Boot extends Game {
    // Design Pattern Singleton
    public static Boot INSTANCE;
    private int screenWidth, screenHeight;
    private OrthographicCamera orthographicCamera;
    private GameScreen gameScreen;

    /**
     * Costruttore della classe `Boot`. Inizializza l'istanza unica del singleton.
     */
    public Boot() {
        INSTANCE = this;
    }

    /**
     * Metodo chiamato quando l'applicazione viene creata. Inizializza le dimensioni dello schermo,
     * la telecamera ortografica e imposta la schermata principale come il menu principale.
     */
    @Override
    public void create() {
        this.screenWidth = Gdx.graphics.getWidth();
        this.screenHeight = Gdx.graphics.getHeight();
        this.orthographicCamera = new OrthographicCamera();
        this.orthographicCamera.setToOrtho(false, screenWidth, screenHeight);
        setScreen(new MainMenuScreen(orthographicCamera));
    }

    /**
     * Restituisce la larghezza dello schermo.
     *
     * @return La larghezza dello schermo.
     */
    public int getScreenWidth() {
        return screenWidth;
    }

    /**
     * Restituisce l'altezza dello schermo.
     *
     * @return L'altezza dello schermo.
     */
    public int getScreenHeight() {
        return screenHeight;
    }

    /**
     * Restituisce l'istanza di `GameScreen` attualmente attiva.
     *
     * @return L'istanza di `GameScreen` attualmente attiva.
     */
    public GameScreen getGameScreen() {
        return gameScreen;
    }

    /**
     * Imposta l'istanza di `GameScreen` attualmente attiva.
     *
     * @param gameScreen L'istanza di `GameScreen` da impostare.
     */
    public void setGameScreen(GameScreen gameScreen) {
        this.gameScreen = gameScreen;
    }
}

package View;

import Model.MusicPlayer;
import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;

/**
 * La classe `MainMenuScreen` rappresenta la schermata del menu principale del gioco.
 * Fornisce opzioni come "Nuova Partita", "Carica Partita", "Opzioni" e "Esci dal gioco".
 * Gestisce anche il cambio di schermata quando i pulsanti vengono premuti.
 * @author Alin Marian Habasescu
 * @author Francesco Gambone
 * @author Gabriele Zimmerhofer
 */
public class MainMenuScreen extends ScreenAdapter {
    private static Stage stage;
    private OrthographicCamera camera;
    private static final SoundPlayer buttonClickSound = new SoundPlayer("sound_effects/abs-confirm-1.mp3");

    /**
     * Costruttore della classe `MainMenuScreen`.
     *
     * @param camera La camera ortografica utilizzata per la visualizzazione.
     */
    public MainMenuScreen(OrthographicCamera camera) {
        // Inizializzazione della telecamera e del Stage
        this.camera = camera;
        stage = new Stage(new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), camera));
        Gdx.input.setInputProcessor(stage);

        // Avvia la musica di sfondo del menu principale
        MusicPlayer.play("main menu");

        // Carica le immagini di sfondo e del logo del gioco
        Texture backgroundImage = new Texture(Gdx.files.internal("MainMenu/Background.png"));
        Texture logoImage = new Texture(Gdx.files.internal("MainMenu/Logo_gioco.png"));

        // Crea istanze di Image utilizzando le texture
        Image background = new Image(backgroundImage);
        Image logo = new Image(logoImage);

        // Imposta le dimensioni delle immagini
        background.setSize(stage.getWidth(), stage.getHeight());
        logo.setSize(stage.getWidth() / 2, stage.getHeight() / 2f);
        logo.setPosition(300, 500);

        // Aggiunge le immagini al Stage
        stage.addActor(background);
        stage.addActor(logo);

        // Crea una tabella per posizionare i pulsanti
        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Aggiunge pulsanti
        TextButton startButton = createTextButton("Nuova Partita");
        TextButton loadButton = createTextButton("Carica Partita");
        TextButton optionsButton = createTextButton("Opzioni");
        TextButton exitGameButton = createTextButton("Esci dal gioco");

        // Aggiunge azione per la transizione alla schermata di gioco quando il pulsante "Nuova Partita" viene premuto
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.1f);
                MusicPlayer.currentMusic.dispose();
                Boot.INSTANCE.setScreen(new CutsceneScreen(MainMenuScreen.this.camera));
                MainMenuScreen.this.dispose();
            }
        });

        // Aggiunge azioni per i pulsanti "Carica Partita" e "Opzioni"
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.1f);
                // Aggiungi qui la logica per il pulsante "Carica Partita"
                System.out.println("Load Game clicked");
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aggiungi qui la logica per il pulsante "Opzioni"
                buttonClickSound.play(0.1f);
                System.out.println("Options clicked");
                OptionScreen optionScreen = new OptionScreen(MainMenuScreen.this.camera, false);
                Boot.INSTANCE.setScreen(optionScreen);
                MainMenuScreen.this.dispose();
            }
        });

        // Aggiunge azione per la chiusura del gioco quando il pulsante "Esci dal gioco" viene premuto
        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.1f);
                System.out.println("Exit Game clicked");
                Gdx.app.exit();
            }
        });

        // Aggiunge i pulsanti alla tabella
        table.add(startButton).padBottom(20).row();
        table.add(loadButton).padBottom(20).row();
        table.add(optionsButton).padBottom(20).row();
        table.add(exitGameButton).padBottom(20).row();
    }

    /**
     * Metodo per la creazione di un pulsante contenente il testo immesso con uno stile personalizzato.
     *
     * @param text Testo del pulsante.
     * @return TextButton creato.
     */
    private TextButton createTextButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        // Stile del testo normale
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(2f);
        buttonStyle.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Colore del testo normale
        buttonStyle.fontColor = Color.WHITE;

        // Colore del testo quando il pulsante è focused
        buttonStyle.overFontColor = Color.YELLOW;

        // Colore del testo quando il pulsante è attivo (premuto)
        buttonStyle.downFontColor = Color.RED;

        // Crea un TextButton direttamente impostando il colore del testo
        TextButton button = new TextButton(text, buttonStyle);

        // Aggiunge un ChangeListener per gestire il cambio di focus
        button.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                // Cambia il colore del testo in base allo stato del pulsante
                if (button.isPressed()) {
                    button.getLabel().setColor(buttonStyle.downFontColor);
                    button.getLabel().setColor(buttonStyle.fontColor);
                } else if (button.isOver()) {
                    button.getLabel().setColor(buttonStyle.overFontColor);
                } else {
                    button.getLabel().setColor(buttonStyle.fontColor);
                }
            }
        });
        return button;
    }

    // Metodi della classe ScreenAdapter

    @Override
    public void show() {
    }

    /**
     * Renderizza la schermata del menu principale.
     *
     * @param delta Il tempo trascorso tra i frame.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Aggiorna e disegna il Stage
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        // Aggiorna le dimensioni del viewport dello Stage
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
    }

    @Override
    public void resume() {
    }

    @Override
    public void hide() {
    }
    /**
     * Rilascia le risorse utilizzate dalla schermata del menu principale.
     */
    @Override
    public void dispose() {
        // Libera le risorse quando la schermata viene chiusa
        stage.dispose();
    }
}

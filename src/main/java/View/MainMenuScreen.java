package View;

import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
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

public class MainMenuScreen extends ScreenAdapter {
    private static Stage stage;
    private OrthographicCamera camera;
    private static final Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/main_soundtrack.mp3"));;
    private static final SoundPlayer buttonClickSound = new SoundPlayer("sound_effects/abs-confirm-1.mp3");

    public MainMenuScreen(OrthographicCamera camera) {
        this.camera = camera;
        stage = new Stage(new FitViewport(Boot.INSTANCE.getScreenWidth(),Boot.INSTANCE.getScreenHeight() ,camera));
        Gdx.input.setInputProcessor(stage);

        // Carica la musica dal tuo progetto (assumi che il file sia nella cartella "assets")
        // Imposta la ripetizione della musica in modo che continui a suonare
        backgroundMusic.setLooping(true);
        // Avvia la musica
        backgroundMusic.play();
        backgroundMusic.setVolume(0.3f);

        // Carica l'immagine PNG dal tuo progetto
        Texture backgroundImage = new Texture(Gdx.files.internal("MainMenu/Background.png"));
        Texture logoImage = new Texture(Gdx.files.internal("MainMenu/Logo_gioco.png"));

        // Crea un'istanza di Image utilizzando la texture
        Image background = new Image(backgroundImage);
        Image logo = new Image(logoImage);

        // Posiziona l'immagine in modo che copra l'intero Stage
        background.setSize(stage.getWidth(), stage.getHeight());
        logo.setSize(stage.getWidth()/2, stage.getHeight()/2f);
        logo.setPosition(300,500);

        // Aggiungi l'Image al tuo Stage in modo che sia disegnato sotto gli altri attori
        stage.addActor(background);
        stage.addActor(logo);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Aggiungi pulsanti
        TextButton startButton = createTextButton("Start Game");
        TextButton loadButton = createTextButton("Load Game");
        TextButton optionsButton = createTextButton("Options");
        TextButton exitGameButton = createTextButton("Exit Game");

        // Aggiungi azione per la transizione alla schermata di gioco quando il pulsante viene premuto
        startButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.1f);
                backgroundMusic.dispose();
                //Boot.INSTANCE.setScreen(new GameScreen(MainMenuScreen.this.camera));
                Boot.INSTANCE.setScreen(new CutsceneScreen(MainMenuScreen.this.camera));
            }
        });

        // Aggiungi azioni per i pulsanti "Load Game" e "Options"
        loadButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                buttonClickSound.play(0.1f);
                // Aggiungi qui la logica per il pulsante "Load Game"
                System.out.println("Load Game clicked");
            }
        });

        optionsButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aggiungi qui la logica per il pulsante "Options"
                buttonClickSound.play(0.1f);
                System.out.println("Options clicked");
            }
        });

        exitGameButton.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                // Aggiungi qui la logica per il pulsante "Options"
                buttonClickSound.play(0.1f);
                System.out.println("Exit Game clicked");
                Gdx.app.exit();

            }
        });

        table.add(startButton).padBottom(20).row();
        table.add(loadButton).padBottom(20).row();
        table.add(optionsButton).padBottom(20).row();
        table.add(exitGameButton).padBottom(20).row();
    }

    private TextButton createTextButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        // Stile del testo normale
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(2f);
        // Migliora la risoluzione della scritta dopo lo Scale
        buttonStyle.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Colore del testo normale
        buttonStyle.fontColor = Color.WHITE;

        // Colore del testo quando il pulsante è focused
        buttonStyle.overFontColor = Color.YELLOW; // Puoi cambiare il colore del testo focused secondo le tue preferenze

        // Colore del testo quando il pulsante è attivo (premuto)
        buttonStyle.downFontColor = Color.RED; // Puoi cambiare il colore del testo quando il pulsante è attivo secondo le tue preferenze

        // Crea un TextButton direttamente impostando il colore del testo
        TextButton button = new TextButton(text, buttonStyle);

        // Aggiungi un ChangeListener per gestire il cambio di focus
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

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
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

    @Override
    public void dispose() {
        // Libera le risorse quando la schermata viene chiusa
        buttonClickSound.dispose();
        stage.dispose();
    }
}

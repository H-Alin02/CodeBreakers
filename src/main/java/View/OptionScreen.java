package View;

import Model.MusicPlayer;
import Model.SoundPlayer;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;

public class OptionScreen extends ScreenAdapter {
    private SpriteBatch batch = new SpriteBatch();
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera camera;
    private static final Music backgroundMusic = Gdx.audio.newMusic(Gdx.files.internal("Music/main_soundtrack.mp3"));;
    private static final SoundPlayer buttonClickSound = new SoundPlayer("sound_effects/abs-confirm-1.mp3");
    private float soundMusic = MusicPlayer.getVolumeFactor();
    private float backmusic = SoundPlayer.getVolumeFactor();
    private float volume;
    public OptionScreen(OrthographicCamera camera){
        this.camera = camera;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);

        setVolume(MusicPlayer.getVolumeFactor());

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

        //creare i label
        Label musicVolume = new Label("Volume musica", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
        Label effectVolume = new Label("Volume effetti", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        // Aggiungi pulsanti

        TextButton mute = createTextButton("Disattiva volume");
        TextButton unMute = createTextButton("Attiva volume");
        TextButton musicP = createTextButton("[+]");
        TextButton musicN = createTextButton("[-]");
        TextButton effectP = createTextButton("[+]");
        TextButton effectN = createTextButton("[-]");
        TextButton ritorno = createTextButton("Ritorna al menù");


        mute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);
                MusicPlayer.setGeneralVolume(0);
                SoundPlayer.setGlobalVolume(0);
            }
        });

        unMute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);
                MusicPlayer.setGeneralVolume(volume);
                SoundPlayer.setGlobalVolume(volume);
            }
        });

        musicP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);

                backmusic += 0.1f;
                if (backmusic > 1){
                    backmusic = 1;
                }
                MusicPlayer.setGeneralVolume(backmusic);
            }
        });

        musicN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);

                backmusic -= 0.1f;
                if (backmusic < 0){
                    backmusic = 0;
                }
                MusicPlayer.setGeneralVolume(backmusic);
                volume = MusicPlayer.getVolumeFactor();
            }
        });

        effectP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);

                soundMusic += 0.1f;
                if (soundMusic >= 1){
                    soundMusic = 1f;
                }
                SoundPlayer.setGlobalVolume(soundMusic);
            }
        });

        effectN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);

                soundMusic -= 0.1f;
                if (soundMusic <= 0){
                    soundMusic = 0;
                }
                SoundPlayer.setGlobalVolume(soundMusic);
            }
        });

        ritorno.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                buttonClickSound.play(0.1f);
                Boot.INSTANCE.setScreen(new MainMenuScreen(camera));
            }
        });

        table.add(mute).padRight(50);
        table.add(unMute).row();
        table.add(musicVolume).padTop(10).padRight(30);
        table.add(musicP).padTop(10).padRight(30);
        table.add(musicN).padTop(10).row();
        table.add(effectVolume).padRight(30).padTop(10);
        table.add(effectP).padRight(30).padTop(10);
        table.add(effectN).padTop(10).row();
        table.add(ritorno).padTop(50);
    }
    private TextButton createTextButton(String text) {
        TextButton.TextButtonStyle buttonStyle = new TextButton.TextButtonStyle();

        // Stile del testo normale
        buttonStyle.font = new BitmapFont();
        buttonStyle.font.getData().setScale(1.2f);
        // Migliora la risoluzione della scritta dopo lo Scale
        buttonStyle.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);

        // Colore del testo normale
        buttonStyle.fontColor = Color.YELLOW;

        // Colore del testo quando il pulsante è focused
        buttonStyle.overFontColor = Color.RED; // Puoi cambiare il colore del testo focused secondo le tue preferenze

        // Colore del testo quando il pulsante è attivo (premuto)
        buttonStyle.downFontColor = Color.BLUE; // Puoi cambiare il colore del testo quando il pulsante è attivo secondo le tue preferenze

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

    public void setVolume(float volume) {
        this.volume = volume;
    }
}

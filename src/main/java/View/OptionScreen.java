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
    private boolean returnToGame = false;

    //creare i label
    Label musicVolume = new Label("Volume musica", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label effectVolume = new Label("Volume effetti", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    Label musicPercent = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));
    Label soundPercent = new Label("", new Label.LabelStyle(new BitmapFont(), Color.WHITE));

    // Aggiungi pulsanti
    TextButton mute = createTextButton("");
    TextButton musicP = createTextButton("[+]");
    TextButton musicN = createTextButton("[-]");
    TextButton effectP = createTextButton("[+]");
    TextButton effectN = createTextButton("[-]");
    TextButton ritorno;

    public void resetMusicLabel() {
        musicPercent.setText(Math.round(100 * MusicPlayer.getVolumeFactor()) + " %");
    }

    public void resetSoundLabel() {
        soundPercent.setText(Math.round(100 * SoundPlayer.getVolumeFactor()) + " %");
    }

    public void resetMuteLabel(boolean isMute) {
        mute.setText((isMute ? "Attiva" : "Disattiva") + " volume");
    }

    public OptionScreen(OrthographicCamera camera, boolean returnToGame){
        this.camera = camera;
        this.returnToGame = returnToGame;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Gdx.input.setInputProcessor(stage);
        create();

    }
    private void create(){

        resetMusicLabel();
        resetSoundLabel();
        resetMuteLabel(MusicPlayer.isMute() && SoundPlayer.isMute());

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

        if(returnToGame){
            ritorno = createTextButton("Ritorna al gioco");
        } else {
            ritorno = createTextButton("Ritorna al menù");
        }

        mute.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                boolean isMute = MusicPlayer.isMute() && SoundPlayer.isMute();

                MusicPlayer.setMute(!isMute);
                SoundPlayer.setMute(!isMute);

                resetMuteLabel(!isMute);
                SoundPlayer.playClickSound();
            }
        });

        musicP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicPlayer.sumGeneralVolume(0.1f);
                resetMusicLabel();
                SoundPlayer.playClickSound();
            }
        });

        musicN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                MusicPlayer.sumGeneralVolume(-0.1f);
                resetMusicLabel();
                SoundPlayer.playClickSound();
            }
        });

        effectP.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.sumGeneralVolume(0.1f);
                resetSoundLabel();
                SoundPlayer.playClickSound();
            }
        });

        effectN.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                SoundPlayer.sumGeneralVolume(-0.1f);
                resetSoundLabel();
                SoundPlayer.playClickSound();
            }
        });

        ritorno.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {

                SoundPlayer.playClickSound();
                OptionScreen.this.dispose();

                if(!returnToGame){
                    Boot.INSTANCE.setScreen(new MainMenuScreen(camera));
                } else {
                    MusicPlayer.multiplyVolume(0.2f);
                    Boot.INSTANCE.setScreen(Boot.INSTANCE.getGameScreen());
                }
            }
        });

        table.add(mute).center().row();

        table.add(musicVolume).padRight(30);
        table.add(musicP).padRight(15);
        table.add(musicPercent).padRight(15);
        table.add(musicN).row();

        table.add(effectVolume).padRight(30);
        table.add(effectP).padRight(15);
        table.add(soundPercent).padRight(15);
        table.add(effectN).row();

        table.add(ritorno).padTop(50).colspan(2);
        //table.setDebug(true);

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
        //buttonClickSound.dispose();
        batch.dispose();
        stage.dispose();
    }
}

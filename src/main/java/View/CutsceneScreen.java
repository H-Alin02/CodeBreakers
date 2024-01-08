package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;

import java.util.Arrays;
import java.util.List;

public class CutsceneScreen extends ScreenAdapter {
    private Stage stage;
    private OrthographicCamera camera;
    private BitmapFont font;
    private List<String> cutsceneTexts;
    private List<String> imagePaths;
    private Image background;
    private Image currentImage;
    private Image textBox;
    private Label currentTextLabel;
    private int currentIndex;


    public CutsceneScreen(OrthographicCamera camera) {
        this.camera = camera;
        stage = new Stage(new FitViewport(Boot.INSTANCE.getScreenWidth(), Boot.INSTANCE.getScreenHeight(), camera));
        font = new BitmapFont();
        font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        cutsceneTexts = Arrays.asList(
                "New Thebes, la città del futuro. Una metropoli dove la tecnologia, il lusso e il potere si fondono " +
                        "in un armonia apparente. Ma dietro la facciata scintillante, si nasconde una realtà oscura e crudele.",
                "Un piccolo gruppo di ricche famiglie si spartisce il controllo della città, con misure sempre più restrittive e autoritarie. " +
                        "Queste famiglie controllano ogni singolo cittadino grazie a nuove ed avanzatissime tecnologie di " +
                        "sorveglianza, impianti cibernetici e vere proprie compagnie militari private.",
                "In questo scenario distopico, i coraggiosi Codebreaker, partiti come una modesta crew hacker, hanno evoluto le loro abilità da semplici " +
                        "hacker a veri guerriglieri. Intercettano comunicazioni, eseguono imboscate e riciclano apparecchiature e armi catturate nella " +
                        "lotta contro l'oligarchia cittadina.",
                "Il Giocatore, uno dei migliori membri dei Codebreaker, si unisce al gruppo per vendicare la perdita della sua famiglia a " +
                        "causa della violenza oligarchica. L'obiettivo è liberare la città, ottenendo informazioni cruciali sulla base segreta di una " +
                        "famiglia e intraprendendo una missione di sabotaggio per svelarne le attività. \nLa missione inizia."
        );
        imagePaths = Arrays.asList(
                "Cutscene/img1.png",
                "Cutscene/img2.png",
                "Cutscene/img3.png",
                "Cutscene/img4.png"
        );

        // Inizia la cutscene
        currentIndex = 0;
        loadContent();
    }

    private void loadContent() {
        if (currentIndex < imagePaths.size()) {
            // Carica l'immagine PNG dal tuo progetto
            Texture back = new Texture(Gdx.files.internal("Cutscene/img.png"));
            back.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            background = new Image(back);
            background.setColor(new Color(0.5f, 0.5f, 0.5f, 1f));
            stage.addActor(background);

            Texture cutsceneImage = new Texture(Gdx.files.internal(imagePaths.get(currentIndex)));
            cutsceneImage.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            currentImage = new Image(cutsceneImage);
            currentImage.setSize(stage.getWidth() / 2 + 100, stage.getHeight() / 2 +  100);
            currentImage.setPosition(stage.getWidth() / 4 - 50, stage.getHeight() / 3 );
            stage.addActor(currentImage);

            Texture texture = new Texture(Gdx.files.internal("Cutscene/TextBox.png"));
            texture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
            textBox = new Image(texture);
            textBox.setSize(stage.getWidth(), stage.getHeight()/4 + 50);
            textBox.setPosition(0,0);
            stage.addActor(textBox);


            // Aggiungi un Label per il testo
            Label.LabelStyle labelStyle = new Label.LabelStyle(font, Color.WHITE);
            currentTextLabel = new Label("", labelStyle);
            currentTextLabel.setWrap(true);
            currentTextLabel.setAlignment(Align.center);
            currentTextLabel.setWidth(stage.getWidth() - 100); // Larghezza del Label
            currentTextLabel.setPosition(50, 120); // Posizione del Label
            currentTextLabel.setFontScale(2f); // Scala il testo
            stage.addActor(currentTextLabel);

            // Imposta il testo corrente
            currentTextLabel.setText(cutsceneTexts.get(currentIndex));
        } else {
            // Fine della cutscene
            // Aggiungi qui la logica per passare alla schermata successiva
            System.out.println("Fine della cutscene");
            Boot.INSTANCE.setScreen(new GameScreen(CutsceneScreen.this.camera));
        }
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act();
        stage.draw();

        // Controlla se è stato premuto il tasto Enter
        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER)) {
            currentIndex++;
            stage.clear(); // Rimuovi attori precedenti
            loadContent(); // Carica il nuovo contenuto
        }
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
}

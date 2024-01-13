package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL20;
/**
 * La classe `GameOverScreen` rappresenta la schermata di Game Over o di completamento della demo.
 * Viene visualizzata quando il gioco termina, indicando se il giocatore ha vinto o perso.
 * @author Francesco Gambone
 */
public class GameOverScreen extends ScreenAdapter {
    private SpriteBatch batch = new SpriteBatch();
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera camera;
    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    /**
     * Costruttore della classe `GameOverScreen`.
     *
     * @param camera           La telecamera ortografica utilizzata per la visualizzazione.
     * @param gameEndingWon    Boolean che indica se il gioco è stato vinto.
     */
    public GameOverScreen(OrthographicCamera camera, boolean gameEndingWon) {
        this.camera = camera;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/1.5f,Boot.INSTANCE.getScreenHeight()/1.5f, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        this.font.font.getRegion().getTexture().setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        if(gameEndingWon){
            Label gameWonLabel = new Label("DEMO COMPLETATA", font);
            Label congratulationsLabel = new Label("            Complimenti! hai completato la demo\n" +
                    "Il team 11 ti ringrazia per aver giocato la DEMO di \n" +
                    "             Codebreakers : The revenge", font);
            Label returnToMenuLabel = new Label("Clicca per ritornare al menù", font);
            table.add(gameWonLabel).expandX();
            table.row();
            table.add(congratulationsLabel).expandX().padTop(10f);
            table.row();
            table.add(returnToMenuLabel).expandX().padTop(10f);



        } else {
            Label gameOverLabel = new Label("GAME OVER", font);
            Label returnToMenuLabel = new Label("Clicca per ritornare al menù", font);
            table.add(gameOverLabel).expandX();
            table.row();
            table.add(returnToMenuLabel).expandX().padTop(10f);

        }



        stage.addActor(table);
    }
    /**
     * Renderizza la schermata di game over. cliccando sullo schermo si ritorna al menù principale
     *
     * @param delta Il tempo trascorso tra i frame.
     */
    @Override
    public void render(float delta) {
        if(Gdx.input.justTouched()){
            Boot.INSTANCE.setScreen(new MainMenuScreen(GameOverScreen.this.camera));
            dispose();
        }

        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.draw();
    }
    /**
     * Rilascia le risorse utilizzate dalla schermata di game over.
     */
    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}

package View;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import org.lwjgl.opengl.GL20;

public class GameOverScreen extends ScreenAdapter {
    private SpriteBatch batch = new SpriteBatch();
    private Viewport viewport;
    private Stage stage;
    private OrthographicCamera camera;
    Label.LabelStyle font = new Label.LabelStyle(new BitmapFont(), Color.WHITE);

    public GameOverScreen(OrthographicCamera camera, boolean gameEndingWon) {
        this.camera = camera;
        viewport = new FitViewport(Boot.INSTANCE.getScreenWidth()/2,Boot.INSTANCE.getScreenHeight()/2, new OrthographicCamera());
        stage = new Stage(viewport, batch);
        Table table = new Table();
        table.center();
        table.setFillParent(true);

        if(gameEndingWon){
            Label gameWonLabel = new Label("DEMO COMPLETATA", font);
            Label congratulationsLabel = new Label("Complimenti! hai completato la demo", font);
            Label returnToMenuLabel = new Label("Clicca per ritornare al menù", font);
            table.add(gameWonLabel).expandX();
            table.row();
            table.add(congratulationsLabel).expandX();
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

    @Override
    public void dispose() {
        stage.dispose();
        batch.dispose();
    }
}

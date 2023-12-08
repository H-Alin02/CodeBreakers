package View;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.FitViewport;

public class MainMenuScreen extends ScreenAdapter {
    private Stage stage;
    private OrthographicCamera camera;

    public MainMenuScreen(OrthographicCamera camera) {
        this.camera = camera;
        stage = new Stage(new FitViewport(Boot.INSTANCE.getScreenWidth(),Boot.INSTANCE.getScreenHeight() ,camera));
        Gdx.input.setInputProcessor(stage);

        Table table = new Table();
        table.setFillParent(true);
        stage.addActor(table);

        Label.LabelStyle textStyle = new Label.LabelStyle(new BitmapFont(), Color.WHITE);
        textStyle.font.getData().setScale(3); // Imposta la dimensione del testo

        Label gameTitle = new Label("CODEBREAKERS\n     The revenge", textStyle);
        Label startGame = new Label("Premi Invio per iniziare il gioco", textStyle);

        table.add(gameTitle).padBottom(200).row(); // Aggiunge il titolo con uno spazio alla fine
        table.add(startGame).padBottom(20).row(); // Aggiunge il testo con uno spazio alla fine
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

        if (Gdx.input.isKeyPressed(Input.Keys.ENTER)) {
            //((Game) Gdx.app.getApplicationListener()).setScreen(new GameScreen(this.camera));
            Boot.INSTANCE.setScreen(new GameScreen(this.camera));
        }
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
        stage.dispose();
    }
}

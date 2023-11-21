package Model;

import Controller.PlayerInputManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Player{
    private static Player INSTANCE;
    private final int PLAYER_WIDTH = 32;
    private final int PLAYER_HEIGHT = 32;
    private final int SCALE = 3;
    private final int SPEED = 3;
    private PlayerState currentState;
    private PlayerInputManager inputManager;
    private PlayerAnimationManager animationManager;
    private int playerX = 300;
    private int playerY = 300;

    public Player() {
        currentState = PlayerState.STANDING;
        inputManager = new PlayerInputManager(this);
        animationManager = new PlayerAnimationManager();
    }

    public static Player getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Player();
        }
        return INSTANCE;
    }

    public void update(float delta) {
        handleInput();
        animationManager.update(delta);
    }

    private void handleInput() {
        // Update the player state based on input
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            moveUp();
        } else if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            moveDown();
        } else if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            moveLeft();
        } else if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            moveRight();
        } else {
            // If no movement keys are pressed, set the player to standing
            currentState = PlayerState.STANDING;
        }
    }

    public void moveUp() {
        currentState = PlayerState.WALK_UP;
        playerY += SPEED;
    }

    public void moveDown() {
        currentState = PlayerState.WALK_DOWN;
        // Update player position
        playerY -= SPEED;
    }

    public void moveLeft() {
        currentState = PlayerState.WALK_LEFT;
        playerX -= SPEED;
    }

    public void moveRight() {
        currentState = PlayerState.WALK_RIGHT;
        playerX += SPEED;
    }

    public TextureRegion getCurrentFrame() {
        return animationManager.getKeyFrame(currentState);
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY(){
        return playerY;
    }
    public int getPLAYER_HEIGHT() {
        return PLAYER_HEIGHT * SCALE;
    }

    public int getPLAYER_WIDTH() {
        return PLAYER_WIDTH * SCALE;
    }
}


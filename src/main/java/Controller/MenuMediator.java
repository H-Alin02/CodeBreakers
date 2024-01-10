package Controller;

public class MenuMediator implements Mediator{

    private boolean changeGameState;
    private boolean changeToOptionsScreen;
    private boolean changeToMainMenuScreen;
    public void MenuMediator(){
        this.changeGameState = false;
        this.changeToOptionsScreen = false;
        this.changeToMainMenuScreen = false;
    }

    public boolean isChangeGameState() {

        return changeGameState;
    }

    public boolean isChangeToMainMenuScreen() {

        return changeToMainMenuScreen;
    }

    public boolean isChangeToOptionsScreen() {

        return changeToOptionsScreen;
    }

    @Override
    public void changeGameStatus() {

        this.changeGameState = !this.changeGameState;
    }


    @Override
    public void changeToMenuScreen() {

        this.changeToMainMenuScreen = !this.changeToMainMenuScreen;

    }

    @Override
    public void changeToOptionsScreen() {

        this.changeToOptionsScreen = !this.changeToOptionsScreen;

    }
}

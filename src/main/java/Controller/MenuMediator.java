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
        if(changeGameState){
            this.changeGameState= false;
        } else if (!changeGameState) {
            this.changeGameState = true;
        }
    }


    @Override
    public void changeToMenuScreen() {
        if(changeToMainMenuScreen){
            this.changeToMainMenuScreen = false;
        } else if (!changeToMainMenuScreen) {
            this.changeToMainMenuScreen = true;
        }
    }

    @Override
    public void changeToOptionsScreen() {
        if(changeToOptionsScreen){
            this.changeToOptionsScreen = false;
        } else if (!changeToOptionsScreen) {
            this.changeToOptionsScreen = true;
        }
    }
}

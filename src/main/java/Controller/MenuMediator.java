package Controller;

public class MenuMediator implements Mediator{

    private boolean changeGameState;
    private boolean changeScreen;
    public void MenuMediator(){
        this.changeGameState = false;
        this.changeScreen = false;
    }

    public boolean isChangeGameState() {
        return changeGameState;
    }

    public boolean isChangeScreen() {
        return changeScreen;
    }

    @Override
    public void changeGameStatus() {
        if(changeGameState){
            this.changeGameState= false;
        } else if (!changeGameState) {
            this.changeGameState =true;
        }
    }

    @Override
    public void changeScreen() {
        if(changeScreen){
            this.changeScreen= false;
        } else if (!changeScreen) {
            this.changeScreen =true;
        }
    }
}

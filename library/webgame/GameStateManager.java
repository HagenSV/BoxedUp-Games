package library.webgame;

public class GameStateManager {
    private int gamePhase;
    protected WebGame game;

    public GameStateManager(){}

    public int getPhase(){
        return gamePhase;
    }

    public void setPhase(int phase){
        game.serverViewManager.renderScreen(phase);
        this.gamePhase = phase;
    }
}

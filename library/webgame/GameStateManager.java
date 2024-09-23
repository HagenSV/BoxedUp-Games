package library.webgame;

public abstract class GameStateManager {
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

    /**
     * @return true if the game is in a state where players can join
     */
    public abstract boolean canJoin();
}

package library.webgame;

public class GameStateManager {
    private int gamePhase;
    private ServerViewManager output;

    public GameStateManager(ServerViewManager out){
        this.output = out;
    }

    public int getPhase(){
        return gamePhase;
    }

    public void setPhase(int phase){
        output.renderScreen(phase);
        this.gamePhase = phase;
    }
}

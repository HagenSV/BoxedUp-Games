package library;

public class GameStateManager {
    private int gamePhase;
    private OutputManager output;

    public GameStateManager(OutputManager out){
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

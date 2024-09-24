package explain_yourself;

import static explain_yourself.ExplainGameConfigs.*;

import library.webgame.GameStateManager;

public class ExplainGameSM extends GameStateManager {
    public final ExplainGameData gameData;

    public ExplainGameSM(ExplainGameData data){
        gameData = data;
    }

    @Override
    public void setPhase(int phase) {
        super.setPhase(phase);
        game.playerManager.setAllPlayerPhases(0);

        if (phase == PROMPT_PHASE){
            gameData.promptTimer.setTime(PROMPT_TIME);
        }
        else if (phase == VOTE_PHASE){
            gameData.voteTimer.setTime(VOTE_TIME);
        }
    }

    @Override
    public boolean canJoin() {
        return getPhase() == JOIN_PHASE;
    }

    @Override
    public void start(){
        gameData.init();
        setPhase(PROMPT_PHASE);
    }

    @Override
    public void endGame(){
        setPhase(GAME_OVER);
    }

}

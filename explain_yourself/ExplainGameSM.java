package explain_yourself;

import static explain_yourself.ExplainGameConfigs.JOIN_PHASE;

import library.webgame.GameStateManager;

public class ExplainGameSM extends GameStateManager {

    @Override
    public void setPhase(int phase) {
        super.setPhase(phase);
        game.playerManager.setAllPlayerPhases(0);
    }

    @Override
    public boolean canJoin() {
        return getPhase() == JOIN_PHASE;
    }

}

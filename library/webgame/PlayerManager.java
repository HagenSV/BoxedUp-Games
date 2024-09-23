package library.webgame;

import java.util.ArrayList;
import java.util.List;

public class PlayerManager {

    private final int MIN_PLAYERS;
    private final int MAX_PLAYERS;

    private List<String> playerList;
    private List<Integer> playerPhase;

    /**
     * Creates a new player manager
     * @param minPlayers the minimum number of players required for the game to start
     * @param maxPlayers the maximum number of players the game can have
     */
    public PlayerManager(int minPlayers, int maxPlayers){
        this.MIN_PLAYERS = minPlayers;
        this.MAX_PLAYERS = maxPlayers;

        playerList = new ArrayList<>(MAX_PLAYERS);
        playerPhase = new ArrayList<>(MAX_PLAYERS);
    }

    /**
     * Attempts to add a player to the game
     * @param playerName the desired name of the player to add
     * @return the resolved name of the player or null if the player could not be added
     */
    public String addPlayer(String playerName){
        if ( playerList.size() == MAX_PLAYERS ) {
            return null;
        }

        if ( playerList.contains( playerName ) ) {
            for ( int i = 1; i < MAX_PLAYERS; i++ ) {
                if ( !playerList.contains( playerName+" "+i ) ) {
                    playerList.add( playerName+" "+i );
                    return playerName+" "+i;
                }
            }
        }

        playerList.add( playerName );

        return playerName;
    }
    
    /**
     * @return a copy of the list of current players
     */
    public List<String> getPlayers(){
        return new ArrayList<>(playerList);
    }

    public int getPlayerCount(){
        return playerList.size();
    }

    /**
     * 
     * @param playerId
     * @return the phase id of the player
     */
    public int getPlayerPhase(int playerId){
        return playerPhase.get(playerId);
    }

    public boolean hasMin(){ return playerList.size() >= MIN_PLAYERS; }


    /**
     * Sets the phase of ALL the players in the game
     * @param phase
     */
    public void setAllPlayerPhases(int phase){
        for (int i = 0; i < playerPhase.size(); i++){
            playerPhase.set(i,phase);
        }
    }

    /**
     * Sets the phase of a player
     * @param playerId
     * @param phaseId
     */
    public void setPlayerPhase(int playerId, int phaseId){
        playerPhase.set(playerId, phaseId);
    }
}

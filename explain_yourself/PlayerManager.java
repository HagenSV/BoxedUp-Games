package explain_yourself;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class PlayerManager {

    private static final File PROMPT_FILE = new File("explain_yourself/data/prompts.txt");
    private static final File COUNTRIES_FILE = new File("explain_yourself/data/countries.txt");
    private static final File PLACES_FILE = new File("explain_yourself/data/places.txt");

    private static final int PROMPTS_PER_PLAYER = 2;
    private final int playerCount;
    private final ExplainGame game;

    //Stores the prompts
    private String[] prompts;

    //Stores player prompt ids as [player][promptNum]
    private int[][] playerPrompts;

    //Stores player prompt responses as [player][responseNum]
    private String[][] playerResponses;

    //Stores response votes as [promptId][responseNum]
    private int[][] votes;

    //Phases of the player
    private int[] playerPhases;
    
    //Number of responses submitted by a player
    private int[] promptsSubmitted;
    
    //If the player has voted for the current card
    private boolean[] voted;

    public PlayerManager(ExplainGame game){
        this.game = game;
        this.playerCount = game.getPlayerCount();

        prompts = new String[playerCount];
        playerPrompts = new int[playerCount][PROMPTS_PER_PLAYER];
        playerResponses = new String[playerCount][PROMPTS_PER_PLAYER];
        votes = new int[playerCount][PROMPTS_PER_PLAYER];

        playerPhases = new int[playerCount];
        promptsSubmitted = new int[playerCount];
        voted = new boolean[playerCount];

        generatePrompts();
        assignPrompts();

    }

    public String getPrompt(int promptId){
        return prompts[ promptId ];
    }

    public String getPrompt(int playerId, int promptNum){
        return prompts[ playerPrompts[playerId][promptNum] ];
    }

    public int getPlayerPromptId(int playerId, int promptNum){
        return playerPrompts[playerId][promptNum];
    }

    public String[] getPromptResponses(int promptId){
        return getPromptResponses(promptId, null);
    }

    public String[] getPromptResponses(int promptId, String defaultValue){
        String[] responses = new String[PROMPTS_PER_PLAYER];

        for (int i = 0; i < playerCount; i++){
            for (int j = 0; j < PROMPTS_PER_PLAYER; j++){
                if (playerPrompts[i][j] == promptId){
                    responses[j] = playerResponses[i][j] == null ? defaultValue : playerResponses[i][j];
                }
            }
        }
        
        return responses;
    }

    public String getPlayerResponse(int playerId, int promptNum){
        return playerResponses[playerId][promptNum];
    }

    public void storeResponse(int playerId, int promptId, String response){
        playerResponses[playerId][promptId] = response;
        promptsSubmitted[playerId]++;
    }

    public int getVotes(int promptId, int responseNum){
        return votes[promptId][responseNum];
    }


    public int[] calcScores(){

        int[] scores = new int[playerCount];

        for (int i = 0; i < playerCount; i++){
            int prompt1Id = getPlayerPromptId(i, 0);
            int prompt2Id = getPlayerPromptId(i, 1);

            int prompt1Votes = 10*getVotes(prompt1Id,0);
            int prompt1Bonus = getVotes(prompt1Id,1) == 0 ? 2 : 1;
            
            int prompt2Votes = 10*getVotes(prompt2Id,1);
            int prompt2Bonus = getVotes(prompt2Id,0) == 0 ? 2 : 1;

            scores[i] = prompt1Votes * prompt1Bonus + prompt2Votes * prompt2Bonus;
        }

        return scores;
    }

    public int getPlayerPhase(int playerId){
        return playerPhases[playerId];
    }

    public void setPhase(int playerId, int phase){
        playerPhases[playerId] = phase;
    }

    public void setAllPlayerPhases(int phase){
        for (int i = 0; i < playerPhases.length; i++){
            playerPhases[i] = phase;
        }
    }

    public int getResponseCount(int playerId){
        return promptsSubmitted[playerId];
    }

    public void vote(int playerId, int promptId, int choice){
        
        votes[promptId][choice]++;
        
        //Remove previous vote if necessary
        if (voted[playerId]){
            votes[promptId][choice == 0 ? 1 : 0]--;
        }

        voted[playerId] = true;
    }

    public void clearAllVotes(){
        for (int i = 0; i < voted.length; i++){
            voted[i] = false;
        }
    }


    private void generatePrompts(){

        List<String> promptPool = readFile(PROMPT_FILE);
        List<String> countryPool = readFile(COUNTRIES_FILE);
        List<String> placesPool = readFile(PLACES_FILE);
        List<String> playerPool = game.getPlayers();

        for (int i = 0; i < playerCount ; i++ ){
            if ( promptPool.isEmpty() ) { promptPool = readFile(PROMPT_FILE); }
            if ( countryPool.isEmpty()) { countryPool = readFile(COUNTRIES_FILE); }
            if ( placesPool.isEmpty() ) { placesPool = readFile(PLACES_FILE); }
            if ( placesPool.isEmpty() ) { playerPool = game.getPlayers(); }

            String prompt = removeRandom(promptPool);
            
            // PROMPT FORMATTING

            prompt = prompt.replaceAll( "\\{country}", removeRandom(countryPool) );
            prompt = prompt.replaceAll( "\\{place}", removeRandom(placesPool) );
            prompt = prompt.replaceAll( "\\{player}", removeRandom(playerPool) );


            //END PROMPT FORMATTING

            prompts[i] = prompt;
        }
    }

    /**
     * Removes a random item from a list and returns it
     * @param list
     * @return
     */
    private String removeRandom(List<String> list){
        return list.remove( ( int )( Math.random()*list.size() ) );
    }

    private void assignPrompts(){
        List<Integer> promptPool1 = new ArrayList<Integer>(playerCount);
        List<Integer> promptPool2 = new ArrayList<Integer>(playerCount);

        for ( int i = 0; i < playerCount; i++ ) {
            promptPool1.add(i);
            promptPool2.add(i);
        }

        for ( int i = 0; i < playerCount; i++ ){
            playerPrompts[i][0] = ( promptPool1.remove( (int)(Math.random()*promptPool1.size() ) ) );
            playerPrompts[i][1] = ( promptPool2.remove( (int)(Math.random()*promptPool2.size() ) ) );
        }

        for ( int i = 0; i < playerCount; i++ ){
            if ( playerPrompts[i][0] == playerPrompts[i][1] ){
                for ( int j = 0; i < playerCount; j++ ){
                    if ( playerPrompts[j][1] != playerPrompts[i][0]  ){
                        int promptId = playerPrompts[j][1];
                        playerPrompts[j][1] = playerPrompts[i][1];
                        playerPrompts[i][1] = promptId;
                        break;
                    }
                }
            }
        }
    }

    /**
     * Reads the data from a file
     * @param f the file to read
     * @return an arraylist containing the lines of strings in the file, or null if the file could not be found
     */
    public static List<String> readFile(File f){

        try ( Scanner file = new Scanner( f ) ) {
            
            List<String> lines = new ArrayList<>();
            
            while (file.hasNextLine()) {
                String line = file.nextLine();
                if (!line.isEmpty() && line.charAt(0) != '#'){
                    lines.add(line);
                }
            }

            return lines;
            
        } catch (FileNotFoundException ignored){
            return null;
        }
    }
}

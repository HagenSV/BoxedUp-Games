package explain_yourself;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import library.webgame.PlayerManager;

public class ExplainGameData {
    private static final File PROMPT_FILE = new File("game_files/explain_yourself/data/prompts.txt");
    private static final File COUNTRIES_FILE = new File("game_files/explain_yourself/data/countries.txt");
    private static final File PLACES_FILE = new File("game_files/explain_yourself/data/places.txt");

    private static final int PROMPTS_PER_PLAYER = 2;

    private int PLAYER_COUNT;

    //Stores the prompts
    private String[] prompts;

    //Stores player prompt ids as [player][promptNum]
    private int[][] playerPrompts;

    //Stores player prompt responses as [player][responseNum]
    private String[][] playerResponses;

    //Stores response votes as [promptId][responseNum]
    private int[][] votes;
    
    //Number of responses submitted by a player
    private int[] promptsSubmitted;
    
    //If the player has voted for the current card
    private boolean[] voted;

    private int cardIndex;

    private final ExplainGame game;

    public ExplainGameData(ExplainGame g){
        game = g;
    }

    public void init(){
        PLAYER_COUNT = playerManager.getPlayerCount();
        prompts = new String[PLAYER_COUNT];
        playerPrompts = new int[PLAYER_COUNT][PROMPTS_PER_PLAYER];
        playerResponses = new String[PLAYER_COUNT][PROMPTS_PER_PLAYER];
        votes = new int[PLAYER_COUNT][PROMPTS_PER_PLAYER];

        promptsSubmitted = new int[PLAYER_COUNT];
        voted = new boolean[PLAYER_COUNT];

        cardIndex = -1;

        generatePrompts();
        assignPrompts();
    }

    public int getCardIndex(){
        return cardIndex;
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

        for (int i = 0; i < PLAYER_COUNT; i++){
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

    public void nextCard(){
        cardIndex += 1;
    }


    public int[] calcScores(){

        int[] scores = new int[PLAYER_COUNT];

        for (int i = 0; i < PLAYER_COUNT; i++){
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
        List<String> playerPool = playerManager.getPlayers();

        for (int i = 0; i < PLAYER_COUNT ; i++ ){
            if ( promptPool.isEmpty() ) { promptPool = readFile(PROMPT_FILE); }
            if ( countryPool.isEmpty()) { countryPool = readFile(COUNTRIES_FILE); }
            if ( placesPool.isEmpty() ) { placesPool = readFile(PLACES_FILE); }
            if ( placesPool.isEmpty() ) { playerPool = playerManager.getPlayers(); }

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
        List<Integer> promptPool1 = new ArrayList<Integer>(PLAYER_COUNT);
        List<Integer> promptPool2 = new ArrayList<Integer>(PLAYER_COUNT);

        for ( int i = 0; i < PLAYER_COUNT; i++ ) {
            promptPool1.add(i);
            promptPool2.add(i);
        }

        for ( int i = 0; i < PLAYER_COUNT; i++ ){
            playerPrompts[i][0] = ( promptPool1.remove( (int)(Math.random()*promptPool1.size() ) ) );
            playerPrompts[i][1] = ( promptPool2.remove( (int)(Math.random()*promptPool2.size() ) ) );
        }

        for ( int i = 0; i < PLAYER_COUNT; i++ ){
            if ( playerPrompts[i][0] == playerPrompts[i][1] ){
                for ( int j = 0; i < PLAYER_COUNT; j++ ){
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

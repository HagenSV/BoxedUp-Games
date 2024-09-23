package explain_yourself.api;

import java.io.IOException;

import com.sun.net.httpserver.HttpExchange;

import explain_yourself.PlayerData;
import library.webgame.WebGame;
import library.webgame.api.APIRequest;

public class CardData extends APIRequest {

    public static final String PATH = "/card-data/";

    public CardData(String path, WebGame game) {
        super(path, game);
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        // TODO Auto-generated method stub
        Object uncastData = game.playerManager.getPlayerData();
        if (!(uncastData instanceof PlayerData)){
            System.err.println("Player Data has incorrect format!");
            return;
        }

        PlayerData playerData = (PlayerData)uncastData;

        playerData.get
    }
    
}

package mainContent.objects;

import java.util.HashMap;
import java.util.TreeMap;

public class GameLibrary {
    private String libraryName;
    private HashMap<String, GameObject> libraryGames = new HashMap<String, GameObject>();

    public GameLibrary(String libraryName){
        this.libraryName = libraryName;
    }

    public void addGame(GameObject game){
        this.libraryGames.put(game.name, game);
    }

    public HashMap<String, GameObject> getLibrary(){
        return libraryGames;
    }

    public String getLibraryName(){
        return libraryName;
    }

}

package net.tiagofar78.pirates.managers;

import net.tiagofar78.pirates.game.PGame;

import java.util.ArrayList;
import java.util.List;

public class GameManager {

    private static final int MAX_GAMES = 0;

    private static PGame[] games = new PGame[MAX_GAMES];

    public static PGame getGame(int id) {
        for (PGame game : games) {
            if (game != null && game.getId() == id) {
                return game;
            }
        }

        return null;
    }

    public static List<Integer> getGamesIds() {
        List<Integer> ids = new ArrayList<>();

        for (PGame game : games) {
            if (game != null) {
                ids.add(game.getId());
            }
        }

        return ids;
    }

    public static void removeGame(int id) {
        for (int i = 0; i < games.length; i++) {
            if (games[i] != null && games[i].getId() == id) {
                games[i] = null;
            }
        }
    }

}

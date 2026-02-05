package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.game.MinigamePlayer;

public class PiratesPlayer extends MinigamePlayer {

    private boolean isAlive = true;

    private int kills = 0;
    private int deaths = 0;
    private int spawnPointsBroken = 0;

    public PiratesPlayer(String playerName) {
        super(playerName);
    }

    public boolean isAlive() {
        return isAlive;
    }

    public void spawn() {
        isAlive = true;
    }

    public int getKills() {
        return kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public int getSpawnPointsBroken() {
        return spawnPointsBroken;
    }

    public void killed() {
        kills++;
    }

    public void died() {
        isAlive = false;
        deaths++;
    }

    public void brokeSpawnPoints() {
        spawnPointsBroken++;
    }

}

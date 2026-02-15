package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.game.MinigameTeam;
import io.github.tiagofar78.grindstone.game.TeamPreset;

import net.kyori.adventure.text.format.NamedTextColor;

import java.util.List;

public class PiratesTeam<T extends PiratesPlayer> extends MinigameTeam<T> {

    private int spawnPointsRemaining;

    public PiratesTeam(TeamPreset preset) {
        super(preset);
    }

    public PiratesTeam(String name, NamedTextColor chatColor, List<T> members) {
        super(name, chatColor, members);
    }

    public int getSpawnPointsRemaining() {
        return spawnPointsRemaining;
    }

    public void setSpawnPointsRemaining(int spawnPoints) {
        this.spawnPointsRemaining = spawnPoints;
    }

}

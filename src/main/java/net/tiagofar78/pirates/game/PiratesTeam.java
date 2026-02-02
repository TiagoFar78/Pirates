package net.tiagofar78.pirates.game;

import io.github.tiagofar78.grindstone.game.MinigameTeam;
import io.github.tiagofar78.grindstone.game.TeamPreset;

import org.bukkit.ChatColor;

import java.util.List;

public class PiratesTeam<T extends PiratesPlayer> extends MinigameTeam<T> {

    public PiratesTeam(TeamPreset preset) {
        super(preset);
    }

    public PiratesTeam(String name, ChatColor chatColor, List<T> members) {
        super(name, chatColor, members);
    }

}

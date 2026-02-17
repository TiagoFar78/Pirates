package net.tiagofar78.pirates;

import io.github.tiagofar78.grindstone.Grindstone;
import io.github.tiagofar78.grindstone.commands.MinigameCommandBinder;
import io.github.tiagofar78.grindstone.game.MapFactory;
import io.github.tiagofar78.grindstone.game.MinigameFactory;
import io.github.tiagofar78.grindstone.game.MinigameSettings;
import io.github.tiagofar78.messagesrepo.MessagesRepo;

import net.tiagofar78.pirates.game.PiratesGame;
import net.tiagofar78.pirates.listener.GameRestrictionsListener;
import net.tiagofar78.pirates.listener.PlayerListener;
import net.tiagofar78.pirates.maps.PiratesMapFactory;

import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class Pirates extends JavaPlugin {

    private static Pirates instance;

    public static Pirates getInstance() {
        return instance;
    }

    @Override
    public void onEnable() {
        if (!new File(getDataFolder(), "config.yml").exists()) {
            saveDefaultConfig();
        }

        instance = (Pirates) Bukkit.getServer().getPluginManager().getPlugin("TF_Pirates");

        MessagesRepo.getTranslations().register(this.getClassLoader(), "/lang");

        PiratesConfig.load();

        registerGamemodes();
        Grindstone.registerCommands(this);

        getServer().getPluginManager().registerEvents(new PlayerListener(), this);
        getServer().getPluginManager().registerEvents(new GameRestrictionsListener(), this);
    }

    private void registerGamemodes() {
        String worldName = PiratesConfig.getInstance().worldName;
        MinigameFactory piratesFactory = (m, s, p, kt) -> new PiratesGame(m, s, p, kt);

        MinigameSettings duelSettings = duelSettings();
        List<MapFactory> duelMaps = List.of(
                new PiratesMapFactory("ClassicBoatDuel"),
                new PiratesMapFactory("LandInvasion"),
                new PiratesMapFactory("LargeBoatDuel")
        );
        String duelCommandLabel = "pirates_duel";
        MinigameCommandBinder.bindQueueCommands(this, piratesFactory, duelSettings, duelMaps, duelCommandLabel);
        MinigameCommandBinder.bindForceStartCommands(this, piratesFactory, duelSettings, duelMaps, duelCommandLabel);
        MinigameCommandBinder.bindPasteMapsCommand(this, getDataFolder(), duelCommandLabel, duelMaps, worldName);

        MinigameSettings fourBoatsSettings = fourBoatsSettings();
        List<MapFactory> fourBoatsMaps = List.of(new PiratesMapFactory("FourBoats"));
        String fourBoatsCommandLabel = "pirates_four_boats";
        MinigameCommandBinder.bindQueueCommands(
                this,
                piratesFactory,
                fourBoatsSettings,
                fourBoatsMaps,
                fourBoatsCommandLabel
        );
        MinigameCommandBinder.bindForceStartCommands(
                this,
                piratesFactory,
                fourBoatsSettings,
                fourBoatsMaps,
                fourBoatsCommandLabel
        );
        MinigameCommandBinder.bindPasteMapsCommand(
                this,
                getDataFolder(),
                fourBoatsCommandLabel,
                fourBoatsMaps,
                worldName
        );
    }

    private MinigameSettings duelSettings() {
        return new MinigameSettings() {

            @Override
            public int minTeams() {
                return 2;
            }

            @Override
            public int minPlayers() {
                return 4;
            }

            @Override
            public int maxTeams() {
                return 2;
            }

            @Override
            public int maxPlayersPerTeam() {
                return 7;
            }

            @Override
            public int maxPartySize() {
                return maxPlayersPerTeam() * maxTeams();
            }

            @Override
            public String getName() {
                return "Pirates Duel";
            }
        };
    }

    private MinigameSettings fourBoatsSettings() {
        return new MinigameSettings() {

            @Override
            public int minTeams() {
                return 2;
            }

            @Override
            public int minPlayers() {
                return 4;
            }

            @Override
            public int maxTeams() {
                return 4;
            }

            @Override
            public int maxPlayersPerTeam() {
                return 5;
            }

            @Override
            public int maxPartySize() {
                return maxTeams() * maxPlayersPerTeam();
            }

            @Override
            public String getName() {
                return "Pirates Four Boats";
            }
        };
    }

}

package plugin.perworldchatplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class PlayerJoin implements Listener {

    private final JavaPlugin plugin;
    public PlayerJoin(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        World playerWorld = player.getWorld();

        String joinMessage = plugin.getConfig().getString("messages.joinMessage");
        if (joinMessage == null) return;
        joinMessage = joinMessage.replace("%player%",playerName).replace("&","§");

        ConfigurationSection group = plugin.getConfig().getConfigurationSection("worldGroups");
        if (group == null) return;
        Set<String> groupNames = group.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = group.getStringList(groupName + ".worlds");

            if (worldNames.contains(playerWorld.getName())) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayers.getWorld().getName())) {
                        onlinePlayers.sendMessage(joinMessage);
                    }
                }
            }
        }
        event.setJoinMessage(null);
    }

}

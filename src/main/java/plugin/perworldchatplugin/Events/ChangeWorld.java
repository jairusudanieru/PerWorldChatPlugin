package plugin.perworldchatplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class ChangeWorld implements Listener {

    private final JavaPlugin plugin;
    public ChangeWorld(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onChangeWorld(PlayerChangedWorldEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        World newWorld = player.getWorld();
        World oldWorld = event.getFrom();

        String joinMessage = plugin.getConfig().getString("messages.joinMessage");
        if (joinMessage == null) return;
        joinMessage = joinMessage.replace("%player%",playerName).replace("&","§");
        String quitMessage = plugin.getConfig().getString("messages.quitMessage");
        if (quitMessage == null) return;
        quitMessage = quitMessage.replace("%player%",playerName).replace("&","§");

        ConfigurationSection group = plugin.getConfig().getConfigurationSection("worldGroups");
        if (group == null) return;
        Set<String> groupNames = group.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = group.getStringList(groupName + ".worlds");

            if (worldNames.contains(newWorld.getName()) && !worldNames.contains(oldWorld.getName())) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayers.getWorld().getName())) {
                        onlinePlayers.sendMessage(joinMessage);
                    }
                }
            }

            if (worldNames.contains(oldWorld.getName()) && !worldNames.contains(newWorld.getName())) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayers.getWorld().getName())) {
                        onlinePlayers.sendMessage(quitMessage);
                    }
                }
            }

        }

    }

}

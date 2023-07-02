package plugin.perworldchatplugin.Events;

import fr.xephi.authme.api.v3.AuthMeApi;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class PlayerQuit implements Listener {

    private final JavaPlugin plugin;
    public PlayerQuit(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        String playerName = player.getName();
        World playerWorld = player.getWorld();

        Plugin authMe = Bukkit.getServer().getPluginManager().getPlugin("AuthMe");
        if (authMe != null) if (!AuthMeApi.getInstance().isAuthenticated(player)) return;

        String quitMessage = plugin.getConfig().getString("messages.quitMessage");
        if (quitMessage == null) return;
        quitMessage = quitMessage.replace("%player%",playerName).replace("&","§");

        ConfigurationSection group = plugin.getConfig().getConfigurationSection("worldGroups");
        if (group == null) return;
        Set<String> groupNames = group.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = group.getStringList(groupName + ".worlds");

            if (worldNames.contains(playerWorld.getName())) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayers.getWorld().getName())) {
                        onlinePlayers.sendMessage(quitMessage);
                    }
                }
            }

        }
        event.setQuitMessage(null);
    }

}

package plugin.perworldchatplugin.Events;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;
import java.util.Set;

public class PlayerChat implements Listener {

    private final JavaPlugin plugin;
    public PlayerChat(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();
        String message = event.getMessage();
        String playerName = player.getName();
        World playerWorld = player.getWorld();

        String fullMessage = plugin.getConfig().getString("messages.chatMessage");
        if (fullMessage == null) return;
        fullMessage = fullMessage.replace("%player%",playerName).replace("%message%",message).replace("&","§");

        ConfigurationSection group = plugin.getConfig().getConfigurationSection("worldGroups");
        if (group == null) return;
        Set<String> groupNames = group.getKeys(false);
        for (String groupName : groupNames) {
            List<String> worldNames = group.getStringList(groupName + ".worlds");

            if (worldNames.contains(playerWorld.getName())) {
                for (Player onlinePlayers : Bukkit.getOnlinePlayers()) {
                    if (worldNames.contains(onlinePlayers.getWorld().getName())) {
                        onlinePlayers.sendMessage(fullMessage);
                    }
                }
            }

        }
        Bukkit.getLogger().info("<" + playerName + "> " + message);
        event.setCancelled(true);
    }

}

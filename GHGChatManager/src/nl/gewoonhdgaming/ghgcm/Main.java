package nl.gewoonhdgaming.ghgcm;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import nl.gewoonhdgaming.ghgcm.commands.ChatManagerCMD;
import nl.gewoonhdgaming.ghgcm.events.ChatEvent;

public class Main extends JavaPlugin {
	
	public HashMap<UUID, Integer> schelden = new HashMap<>();
	
	public String prefix = ChatColor.AQUA + "GHGChatManager " + ChatColor.WHITE;

	public boolean AdminChat = false;
	
	private static Main instance;
	
	@Override
	public void onEnable() {
		instance = this;
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		console.sendMessage(prefix + getDescription().getName() + " versie: " + getDescription().getVersion() + 
				" is opgestart!");
		getConfig().options().copyDefaults(true);
		saveConfig();
		getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
		getCommand("cm").setExecutor(new ChatManagerCMD(this));
	}
	
	
	
    public static Main getInstance() {
        return instance;
    }
	
	@Override
	public void onDisable() {
		ConsoleCommandSender console = Bukkit.getServer().getConsoleSender();
		console.sendMessage(prefix + "Plugin is successvol afgesloten!");
	}
	

}

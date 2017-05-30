package nl.gewoonhdgaming.ghgcm.utils;

import java.util.HashMap;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import nl.gewoonhdgaming.ghgcm.Main;



public class ChatManagerUtils {
	
	
	public static boolean AdminChat = false;
	
	private static HashMap<UUID, Integer> COOLDOWN = new HashMap<>();

	public static boolean slowChat = false;
	
	public static int get(Player p) {
		if(COOLDOWN.get(p.getUniqueId()) == null) {
			COOLDOWN.put(p.getUniqueId(), 0);
			return 0;
		}
		return COOLDOWN.get(p.getUniqueId());
	}
	
	public static boolean has(Player p) {
		if(get(p) <= 0) {
			return false;
		}
		p.sendMessage(Main.getInstance().prefix + "Je moet nog " + get(p) + " seconde(s) wachten!");
		return true;
	}
	
	public static void add(Player p, int value) {
		if(!has(p)) {
			COOLDOWN.put(p.getUniqueId(), value);
			new BukkitRunnable() {
				
				@Override
				public void run() {
					if(get(p.getPlayer()) > 0) {
						COOLDOWN.put(p.getUniqueId(), get(p) - 1);
					} else {
						COOLDOWN.put(p.getUniqueId(), 0);
						this.cancel();
					}
				}
			}.runTaskTimerAsynchronously(Main.getInstance(), 20, 20);
		}
	}

}

package nl.gewoonhdgaming.ghgcm.events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_10_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import net.minecraft.server.v1_10_R1.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle;
import net.minecraft.server.v1_10_R1.PacketPlayOutTitle.EnumTitleAction;
import nl.gewoonhdgaming.ghgcm.Main;
import nl.gewoonhdgaming.ghgcm.utils.ChatManagerUtils;

@SuppressWarnings("deprecation")
public class ChatEvent implements Listener {
	
    private Main instance;

    public ChatEvent(Main instance) {
        this.instance = instance;
    }
	
	@EventHandler
	public void onPlayerChat(PlayerChatEvent e) {
		for (String word : e.getMessage().split(" ")) {
			if (instance.getConfig().getStringList("badwords").contains(word.toLowerCase())) {
				if(!e.getPlayer().hasPermission("GHGCM.Admin")) {
				e.getMessage().replace(word, "****");
				String afterModified = e.getMessage().toLowerCase().replace(word, "****" );
				e.setMessage(afterModified);
				if(!instance.schelden.containsKey(e.getPlayer().getUniqueId())) {
					instance.schelden.put(e.getPlayer().getUniqueId(), 1);
					e.getPlayer().sendMessage(instance.prefix + ChatColor.RED + "Schelden is niet toegestaan!(waarschuwing 1/3)");
					PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"Schelden is niet toegestaan!\"}"), 20, 40, 20);
                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"Je hebt nu 1 waarschuwing!\"}"), 20, 40, 20);
                    ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(title);
                    ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(subtitle);
                    return;
				}
				if(instance.schelden.containsKey(e.getPlayer().getUniqueId())) {
					if(instance.schelden.containsValue(1)) {
						instance.schelden.remove(e.getPlayer().getUniqueId());
						instance.schelden.put(e.getPlayer().getUniqueId(), 2);
						e.getPlayer().sendMessage(instance.prefix + ChatColor.RED + "Schelden is niet toegestaan!(waarschuwing 2/3)");
						PacketPlayOutTitle title = new PacketPlayOutTitle(EnumTitleAction.TITLE, ChatSerializer.a("{\"text\":\"Schelden is niet toegestaan!\"}"), 20, 40, 20);
	                    PacketPlayOutTitle subtitle = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, ChatSerializer.a("{\"text\":\"Je hebt nu 2 waarschuwingen! De volgende keer wordt je gekickt!\"}"), 20, 40, 20);
	                    ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(title);
	                    ((CraftPlayer) e.getPlayer()).getHandle().playerConnection.sendPacket(subtitle);
	                    return;
					} else
					if(instance.schelden.containsValue(2)) {
						instance.schelden.remove(e.getPlayer().getUniqueId());
						for(Player p : Bukkit.getOnlinePlayers()) {
							if(p.hasPermission("GHGCM.Admin")) {
								p.sendMessage(instance.prefix + ChatColor.RED + e.getPlayer().getName() + ChatColor.GOLD
										+ " Is gekickt omdat hij meer dan 2 waarschuwingen had!");
							}
						}
						e.getPlayer().kickPlayer(instance.prefix + ChatColor.RED + "Schelden is niet toegestaan \n" +
						ChatColor.WHITE + "Je bent gekickt door: " + ChatColor.BOLD + "GHGChatManager!");
	                    return;
					}
				}
				}
			}
		}
	}
	
	@EventHandler
	public void onChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(instance.AdminChat  == true) {
			if(!p.hasPermission("GHGCM.Admin")) {
				e.setCancelled(true);
				p.sendMessage(instance.prefix + ChatColor.RED + "Je mag niet praten omdat AdminChat aan staat!");
				return;
			}
			e.setCancelled(true);
			for(Player pl : Bukkit.getOnlinePlayers()) {
			pl.sendMessage(ChatColor.AQUA + "STAFF " + p.getName() + ChatColor.GREEN +  " > " + e.getMessage());
			}
		}
	}
	
	@EventHandler
	public void muted(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(ChatManagerUtils.muted.contains(p)) {
			p.sendMessage(instance.prefix + "Je bent gemute door een stafflid. Mocht je vinden dat dit onterecht is,"
					+ " kan je een bericht sturen naar het staffteam door /staff <bericht> te doen. Dit commando mag"
					+ " niet misbruikt worden anders volgen er gevolgen!");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void slowChat(AsyncPlayerChatEvent e) {
		Player p = e.getPlayer();
		if(ChatManagerUtils.slowChat == true) {
			if(!p.hasPermission("GHGCM.Admin")) {
				if(ChatManagerUtils.has(p)) {
					e.setCancelled(true);
				} else {
					ChatManagerUtils.add(p, Main.getInstance().getConfig().getInt("cooldown"));
				}
			}
		}
	}
		
}

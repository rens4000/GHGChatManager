package nl.gewoonhdgaming.ghgcm.commands;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.ghgcm.Main;
import nl.gewoonhdgaming.ghgcm.utils.ChatManagerUtils;

public class ChatManagerCMD implements CommandExecutor {
	
    private Main instance;

    public ChatManagerCMD(Main instance) {
        this.instance = instance;
    }
	
    @Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("cm")) {
			if(args.length != 0) {
				if(args[0].equalsIgnoreCase("reload")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						instance.saveConfig();
						sender.sendMessage(instance.prefix + "Config herladen!");
					} else {
						
						sender.sendMessage(instance.prefix + "Je hebt geen permissie voor dit commando!");
					}
				}	
				if(args[0].equalsIgnoreCase("add")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						if(args[1].equals(null)) {
							sender.sendMessage(instance.prefix + "Verkeerd gebruik van commando! Gebruik: /cm add <scheldwoord>");
						} else {
							if(instance.getConfig().getStringList("badwords").contains(args[1].toString())) {
								sender.sendMessage(instance.prefix + ChatColor.RED + "Scheldwoord bestaat al in de config!");
								return false;
							} else {
								List<String> list = instance.getConfig().getStringList("badwords");
								list.add(args[1].toString());
								instance.getConfig().set("badwords", list);
								instance.saveConfig();
								instance.saveConfig();
							sender.sendMessage(instance.prefix + args[1].toString() + " is toegevoegd aan de config!");
							}
						}
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				if(args[0].equalsIgnoreCase("remove")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						if(args[1].equals(null)) {
							sender.sendMessage(instance.prefix + ChatColor.RED + "Verkeerd gebruik van commando! Gebruik: /cm remove <scheldwoord>");
						} else {
							if(!instance.getConfig().getStringList("badwords").contains(args[1].toString())) {
								sender.sendMessage(instance.prefix + ChatColor.RED + "Scheldwoord bestaat niet in de config!");
								return false;
							} else {
								instance.getConfig().getStringList("badwords").remove(args[1].toString());
							List<String> list = instance.getConfig().getStringList("badwords");
							list.remove(args[1].toString());
							instance.getConfig().set("badwords", list);
							instance.saveConfig();
							sender.sendMessage(instance.prefix + args[1].toString() + " is verwijderd uit de config!");
							}
						}
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				if(args[0].equalsIgnoreCase("list")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						List<String> list = new ArrayList<String>();
						list = instance.getConfig().getStringList("badwords");
						for(String s : list){
						sender.sendMessage(s);
						}
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				if(args[0].equalsIgnoreCase("cc")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						for(int i = 0; i < 200; i++) {
							Bukkit.broadcastMessage("");
						}
						Bukkit.broadcastMessage(instance.prefix + "De chat is gecleared!");
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				if(args[0].equalsIgnoreCase("admin")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						instance.AdminChat = !instance.AdminChat;
						if(instance.AdminChat == true) {
							for(Player pl : Bukkit.getOnlinePlayers()) {
								pl.sendMessage(instance.prefix + "Adminchat is ingeschakeld!");
								}
						} else {
							for(Player pl : Bukkit.getOnlinePlayers()) {
								pl.sendMessage(instance.prefix + "Adminchat is uitgeschakeld!");
								}
						}
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				if(args[0].equalsIgnoreCase("slow")) {
					if(sender.hasPermission("GHGCM.Admin")) {
						ChatManagerUtils.slowChat = !ChatManagerUtils.slowChat;
						if(ChatManagerUtils.slowChat == true) {
							for(Player pl : Bukkit.getOnlinePlayers()) {
								pl.sendMessage(instance.prefix + "SlowChat is ingeschakeld!");
								}
						} else {
							for(Player pl : Bukkit.getOnlinePlayers()) {
								pl.sendMessage(instance.prefix + "SlowChat is uitgeschakeld!");
								}
						}
					} else {
						sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
					}
				}
				return false;
			} else {
				if(sender.hasPermission("GHGCM.Admin")) {
				sender.sendMessage(instance.prefix + "-_-_-_-_-_- GHGCM -_-_-_-_- ");
				sender.sendMessage(instance.prefix + "/cm reload - reload de config");
				sender.sendMessage(instance.prefix + "-_-_-_-_-_- AntiCurse -_-_-_-_- ");
				sender.sendMessage(instance.prefix + "/cm add <scheldwoord> - voeg een scheldwoord toe");
				sender.sendMessage(instance.prefix + "/cm remove <scheldwoord> - verwijder een scheldwoord");
				sender.sendMessage(instance.prefix + "-_-_-_-_-_- Overig chat -_-_-_-_- ");
				sender.sendMessage(instance.prefix + "/cm admin - schakel adminchat mode in/uit");
				sender.sendMessage(instance.prefix + "/cm slow - schakel slowchat in/uit");
				sender.sendMessage(instance.prefix + "/cm cc - clear de chat");
				return false;
				} else {
					sender.sendMessage(instance.prefix + ChatColor.RED + "Je hebt geen permissie voor dit commando!");
				}
			}
		}
		return false;
	}

}

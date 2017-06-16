package nl.gewoonhdgaming.ghgcm.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.ghgcm.Main;

public class PartyCMD implements CommandExecutor {
	
	private Main instance;

    public PartyCMD(Main instance) {
        this.instance = instance;
    }

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(cmd.getName().equalsIgnoreCase("party")) {
			if(!(sender instanceof Player)) {
				sender.sendMessage(instance.prefix + "Je moet een speler zijn om dit commando uit te voeren!");
				return false;
			}
			if(args.length == 0) {
				sender.sendMessage(instance.prefix + "-_-_-_-_-_- Party -_-_-_-_- ");
				sender.sendMessage(instance.prefix + "/party create <naam> - maak een party");
				sender.sendMessage(instance.prefix + "/party remove <naam> - verwijder je party");
				return false;
			}
		}
		return false;
	}

}

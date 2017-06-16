package nl.gewoonhdgaming.ghgcm.chatparty;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import nl.gewoonhdgaming.ghgcm.Main;

public class PartyManager {
	
	public ArrayList<Party> partys = new ArrayList<Party>();
	
	public Party getPartyByName(String name) {
		for(Party pd : partys) {
			if(pd.getName().equalsIgnoreCase(name)) return pd;
		}
		return null;
		
	}
	
	public Party getPartyById(int id) {
		for(Party pd : partys) {
			if(pd.getId() == id) return pd;
		}
		return null;
		
	}
	
	public boolean hasName(String name) {
		for(Party pa : partys) {
			if(pa.getName().equalsIgnoreCase(name)) return true;
		}
		return false;
	}
	
	public void createParty(String name, Player p) {
		if(hasName(name)) {
			p.sendMessage(Main.getInstance().prefix + "Die party naam bestaat al!");
			return;
		}
		int id = partys.size() + 1;
		Party pa = new Party(id, name);
		partys.add(pa);
	}
	
	public void removeParty(Party pa) {
		if(pa == null) {
			return;
		}
		for(UUID pd : pa.getPlayers()) {
			Player pl = Bukkit.getPlayer(pd);
			pl.sendMessage(Main.getInstance().prefix + "De party: " + pa.getName() + " is verwijderd!");
		}
		partys.remove(pa);
	}

}

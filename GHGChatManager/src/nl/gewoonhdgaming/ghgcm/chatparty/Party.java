package nl.gewoonhdgaming.ghgcm.chatparty;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.entity.Player;

public class Party {
	
	private int id;
	private String name;
	private ArrayList<UUID> players = new ArrayList<UUID>();
	
	public Party(int id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public int getId() {
		return id;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public ArrayList<UUID> getPlayers() {
		return players;
	}
	
	public UUID getPlayer(Player p) {
		for(UUID pd : players) {
			if(pd.equals(p.getUniqueId())) return pd;
		}
		return null;
	}

}

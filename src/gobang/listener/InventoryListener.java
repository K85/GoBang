package gobang.listener;

import gobang.chess.ChessGamePlayer;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;


public class InventoryListener implements Listener{
	
	//参与游戏的玩家不能与GUI界面进行交互
	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent event){
		Player player = (Player) event.getWhoClicked();
		if (ChessGamePlayer.isGamePlayer(player) == true) {
			event.setCancelled(true);
		}
		
	}
}

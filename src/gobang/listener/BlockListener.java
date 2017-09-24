package gobang.listener;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.timer.FaceTimer;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class BlockListener implements Listener{
	
	//游戏中的玩家不能破坏方块
	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = (Player) event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player)) {
			event.setCancelled(true);
		}
	}

	//游戏中的玩家不能放置方块
	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		
		//注意: 如果一个位置已经放置了棋子, 那么头颅是无法再次放置在棋盘方块上的
		//并且, 也不会触发onBlockPlace事件
		
		Player player = (Player) event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player)) {
			
			//注意: 如果放置的方块是头颅，则允许放置
			
			if (event.getBlock().getType() == Material.SKULL) {
				
				//落子特效
				event.getBlock().getWorld().playEffect(
						event.getBlock().getLocation(), Effect.MOBSPAWNER_FLAMES, 1);
				
				//更新插件的内存并检查输赢
				ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
				chessGame.playChessMan(event.getBlock().getLocation());
				
				
				//把放置的头颅调整一个朝向
				new FaceTimer(event.getBlock());
				
				//切换落子玩家
				event.getPlayer().setLevel(0);
				chessGame.changeChessPlayer();
				
				//给游戏步数+1
				chessGame.setStepNumber(chessGame.getStepNumber() + 1);

				return;
			}
			
			event.setCancelled(true);
		}
	}
	
}

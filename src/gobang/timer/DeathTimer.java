package gobang.timer;

import gobang.GoBang;
import gobang.chess.ChessGame;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/** 该类用于玩家死亡时, 延时给玩家恢复物品 **/
public class DeathTimer implements Runnable {

	private Player player = null;
	
	public DeathTimer (Player player){
		this.player = player;
		Bukkit.getScheduler().scheduleSyncDelayedTask(GoBang.getPlugin(), this, 20L);
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void run() {
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		chessGame.leavePlayer(player);
	}
}

package gobang.timer;

import org.bukkit.Bukkit;

import gobang.chess.ChessGame;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;

public class EndTimer extends ChessGameTimer{

	public EndTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {
		
		if (getChessGame().getState() != ChessGameState.END) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			return false;
		}
		
		if (leftTime == 0) {
			
		} else {
		getChessGame().sendMessage("end_LeftTime", "" + leftTime);
		}
		
		return true;
	}

	@Override
	public void onEnding(int taskId) {
		
		
		if (onRunning(0) == false) {
			return;
		}
		
		Bukkit.getScheduler().cancelTask(getTaskId());
		
		//告诉玩家该游戏场地已经结束了
		//注意: 这里需要先告诉玩家信息, 再调用overGame方法!
		getChessGame().sendMessage("over");
		
		getChessGame().overGame();
		
		//重新启动该游戏场地的CheckTimer来检查玩家的加入
		getChessGame().setState(ChessGameState.FREE);
		new CheckTimer(getChessGame(), 10);
	}
}

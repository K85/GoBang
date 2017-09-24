package gobang.timer;

import org.bukkit.Bukkit;

import gobang.chess.ChessGame;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;

/** PlayTimer描述该游戏场地的总游戏时间,用于计时 **/
public class PlayTimer extends ChessGameTimer{

	public PlayTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {
		
		//判断游戏场地是否处于PLAY状态, 如果不是, 则可以取消该任务
		//注意: 该判断主要用于强制结束游戏时用的
		if (getChessGame().getState() != ChessGameState.PLAY) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			return false;
		}
		
		
		getChessGame().setLeftTime(leftTime);
		
		/** 特殊的剩余时间额外通知 
		 * 最后10秒都要进行提醒
		 * 最后5分钟提醒一次
		 * 每过10分钟提醒一次
		 * **/
		
		if (leftTime == 0) {
			
		} else if (leftTime <= 10) {
			getChessGame().sendMessage("play_LeftTime", "" + leftTime);
		} else if (leftTime == 300) {
			getChessGame().sendMessage("play_LeftTime", "" + leftTime);
		} else if (leftTime % 600 == 0) {
			getChessGame().sendMessage("play_LeftTime", "" + leftTime);
		}
		
		return true;
	}

	@Override
	public void onEnding(int taskId) {
		
		if (onRunning(0) == false) {
			return;
		}
		
		advanceToEndTimer();
	}
	
	public void advanceToEndTimer() {
		Bukkit.getScheduler().cancelTask(getTaskId());
		
		//告诉该游戏场地的所有玩家, 当前游戏场地已结束, 要进入游戏结束阶段
		getChessGame().sendMessage("play_Over");
		//将游戏剩余时间设置为0秒
		getChessGame().setLeftTime(0);
				
		getChessGame().setState(ChessGameState.END);
		new EndTimer(getChessGame()
				, 15);
		
		
	}

}

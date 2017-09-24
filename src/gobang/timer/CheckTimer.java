package gobang.timer;

import org.bukkit.Bukkit;

import gobang.chess.ChessGame;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;

/** 该类用于一个游戏场地的人数是否达到游戏开始所要求的人数 
 * 	在游戏开始后, 该时间任务会被取消
 * **/
public class CheckTimer extends ChessGameTimer {

	
	
	public CheckTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {
		
		//判断当前ChessGame游戏场地是否达到开始游戏所需人数
		if (getChessGame().getChessGamePlayers().size() >= 2) {
			advanceToLobbyTimer();
		}
		
		return true;
	}

	@Override
	public void onEnding(int taskId) {
		//通过不断重置剩余时间，使该时间任务不断存活
		setLeftTime(10);
		
	}
	
	public void advanceToLobbyTimer() {
		//达到游戏人数, 则创建LobbyTimer时间任务, 启动游戏开始倒计时
		//修改ChessGame的State
		Bukkit.getScheduler().cancelTask(getTaskId());
		
		//将该ChessGame的状态修改为LOBBY
		getChessGame().setState(ChessGameState.LOBBY);
				
		new LobbyTimer(getChessGame()
				, getChessGame().getChessManager().getLobbyTime());
		
		
	}

}

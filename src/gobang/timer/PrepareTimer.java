package gobang.timer;

import org.bukkit.Bukkit;

import gobang.chess.ChessGame;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;

/** PrepareTimer处于LobbyTimer和PlayTimer之间 **/
public class PrepareTimer extends ChessGameTimer{

	public PrepareTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {
		
		if (getChessGame().getState() != ChessGameState.PREPARE) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			return false;
		}
		
		//给所有参与游戏的玩家发送[游戏要正式开始]的提示信息
		if (leftTime == 0) {
			
		} else {
			getChessGame().sendMessage("prepare_LeftTime", "" + leftTime);
		}
		
		return true;
	}

	@Override
	public void onEnding(int taskId) {
		
		
		if (onRunning(0) == false) {
			return;
		}
		
		
		advanceToPlayTimer();
	}
	
	public void advanceToPlayTimer() {
		Bukkit.getScheduler().cancelTask(getTaskId());
		
		getChessGame().setState(ChessGameState.PLAY);
		//在LobbyTimer结束时，启动PlayTimer
		new PlayTimer(getChessGame()
				, getChessGame().getChessManager().getPlayTime());
		
		new StepTimer(getChessGame()
				, getChessGame().getChessManager().getStepTime());
		
		
		//注意: 这里已经先调用了startGame方法
		//因为, Prepare状态实际上只是为了延后游戏时间, 并没有太大的实际作用
		getChessGame().startGame();
		
		getChessGame().sendMessage("prepare_Over");
	}

}

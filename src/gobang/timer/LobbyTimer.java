package gobang.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;
import gobang.util.LanguageUtil;

/** 该时间任务用于游戏开始倒计时, 因为执行此时间任务时, 玩家正处于Lobby大厅, 
 * 	因此, 该时间任务取名为LobbyTimer
 *  **/
public class LobbyTimer extends ChessGameTimer {

	public LobbyTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {

		if (getChessGame().getState() != ChessGameState.LOBBY) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			return false;
		}
		
		//判断是否要继续该时间任务, 即继续游戏进程
		if (isContinue() == false) {
			returnToCheckTimer();
			return false;
		}

		//通过修改经验条等级的形式, 告诉游戏玩家还有多久开始游戏
		for (ChessGamePlayer chessGamePlayer : getChessGame().chessGamePlayers) {
			Player player = chessGamePlayer.getPlayer();

			//当游戏开始倒计时只剩最后5秒时, 启用广播
			if (leftTime == 0) {
				
			} else if (leftTime <= 5) {
				LanguageUtil.sendMessage(player, "lobby_CountDown", ""+getLeftTime());
			}

			player.setLevel(getLeftTime());
		}
		
		
		return true;
	}

	@Override
	public void onEnding(int taskId) {
		
		if (onRunning(0) == false) {
			return;
		}
		
		advanceToPrepareTimer();
	}

	//用于判断是否要继续计时, 用于有玩家退出时
	public boolean isContinue() {

		if (getChessGame().chessGamePlayers.size()
				>= 2) {
			return true;
		}

		return false;
	}

	//该方法用于本时间任务返回到CheckTimer时所执行
	public void returnToCheckTimer() {
		Bukkit.getScheduler().cancelTask(getTaskId());
		//设置游戏场地状态
		getChessGame().setState(ChessGameState.FREE);
		
		new CheckTimer(getChessGame(), 10);
		//设置玩家的等级
		getChessGame().setLevel(0);
	}
	
	//该方法用于本时间任务前进到PrepareTimer时所执行
	public void advanceToPrepareTimer() {
		Bukkit.getScheduler().cancelTask(getTaskId());
		
		getChessGame().setState(ChessGameState.PREPARE);
		//在LobbyTimer结束时，启动PrepareTimer
		new PrepareTimer(getChessGame(), 5);
		
		//注意: 在此时就可以调用startGame方法来给予参与的玩家身份和道具
		getChessGame().prepareGame();
		
		
	}
}

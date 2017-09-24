package gobang.timer;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.chess.ChessGameState;
import gobang.chess.ChessGameTimer;
import gobang.util.LanguageUtil;

/** 该类用于描述该游戏场地中棋手的每一次走棋时间限制 **/
public class StepTimer extends ChessGameTimer{

	public ChessGamePlayer nowChessGamePlayer = getChessGame().getNowChessPlayer();
	
	public StepTimer(ChessGame chessGame, int leftTime) {
		super(chessGame, leftTime);
	}

	@Override
	public boolean onRunning(int leftTime) {
		//判断游戏场地是否处于PLAY状态, 如果不是, 则可以取消该任务
		if (getChessGame().getState() != ChessGameState.PLAY) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			return false;
		}
		
		//判断游戏场地的落子玩家是否已经改变
		if (nowChessGamePlayer != getChessGame().getNowChessPlayer()) {
			nowChessGamePlayer = getChessGame().getNowChessPlayer();	

			//改变StepTimer的状态
			changeChessPlayer();
			
			return false;
		}
		
		Player player = getChessGame().getNowChessPlayer().getPlayer();
		
		//通过等级的形式展示剩余时间
		player.setLevel(leftTime);
		
		//当秒数为0时,可以不进行提示
		//当最后3秒时, 提示正在落子的棋手
		if (leftTime == 0){
			
		} else if (leftTime <= 3) {
			LanguageUtil.sendMessage(player, "play_StepTime", "" + leftTime);
		} 
		
		return true;
	}


	
	@Override
	public void onEnding(int taskId) {
		
		//最后提醒一次该玩家
		if (onRunning(0) == false) {
			return;
		}
		
		
		//给现在落子的玩家提示
		Player player = getChessGame().getNowChessPlayer().getPlayer();
		LanguageUtil.sendMessage(player, "play_ChessGamePlayer_OverTime");
		
		//给游戏场地中所有玩家发送信息
		getChessGame().sendMessage("play_OverTime", player.getName());
		
		//给游戏步数+1
		getChessGame().setStepNumber(getChessGame().getStepNumber() + 1);
		
		//强制改变游戏场地的落子玩家
		getChessGame().changeChessPlayer();
						
		//改变StepTimer的状态
		changeChessPlayer();
		
	}
	
	public void changeChessPlayer() {
		nowChessGamePlayer = getChessGame().getNowChessPlayer();	
		setLeftTime(getChessGame().getChessManager().getStepTime());
	}

}

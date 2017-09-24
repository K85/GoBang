package gobang.chess;

import gobang.GoBang;

import org.bukkit.Bukkit;

public abstract class ChessGameTimer implements Runnable {
	
	private int leftTime;
	private int taskId;
	private ChessGame chessGame = null;
	
	public ChessGameTimer(ChessGame chessGame, int leftTime) {
		this.chessGame = chessGame;
		this.leftTime = leftTime;
		this.taskId = 
		Bukkit.getScheduler().scheduleSyncRepeatingTask(GoBang.getPlugin(), this, 0L, 20L);
	}
	
	public void run() {
		/*//结束的判断必须比执行其他内容优先
		if (getChessGame().isForceShut() == true) {
			Bukkit.getScheduler().cancelTask(getTaskId());
			
			//注意: 这里更改了标记
			getChessGame().setForceShut(false);
			return;
		}*/
		
		
		if (leftTime != 0) {
			onRunning(leftTime);
			leftTime--;
		} else {
			onEnding(taskId);
		}
		
	}
	
	//时间任务在运行时执行的内容
	//返回值boolean用于在onEnding中调用onRuning方法时判断是否要retrun
	//返回true表示继续, 返回false表示不继续, 而是直接return掉
	public abstract boolean onRunning(int leftTime);
	
	//时间任务在结束时执行的内容
	public abstract void onEnding(int taskId);

	public ChessGame getChessGame() {
		return chessGame;
	}

	public void setChessGame(ChessGame chessGame) {
		this.chessGame = chessGame;
	}

	public int getLeftTime() {
		return leftTime;
	}

	public void setLeftTime(int leftTime) {
		this.leftTime = leftTime;
	}

	public int getTaskId() {
		return taskId;
	}

	public void setTaskId(int taskId) {
		this.taskId = taskId;
	}
	
}

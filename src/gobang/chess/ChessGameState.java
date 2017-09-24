package gobang.chess;

/** 该枚举类用于描述游戏场地的活动状态 **/
public enum ChessGameState {
	//Lobby表示游戏场地还未开始，玩家们此时正在等待大厅等待
	//ALIVE表示游戏场地已经开始，玩家们此时已经被传送到了出生点/观战点
	//END表示游戏场地已经进入游戏结束阶段（15秒），玩家们等读秒结束会被传送到End结束点
	//并且游戏场地状态重置为lobby
	//FREE表示该游戏场地正处于CheckTimer任务中, 还没有启用LobbyTimer
	FREE("§a空闲中"), LOBBY("§a等待中"),PREPARE("§d准备中"), PLAY("§c游戏中"), END("§8结束中");
	
	String state = null;
	
	ChessGameState(String state) {
		this.state = state;
	}
	
	public String getState() {
		return state;
	}
}

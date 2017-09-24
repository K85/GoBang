package gobang.file;



public class LanguageFile extends PluginFile{

	//从Config.yml中获取，要读取哪一个语言文件
	static String language = FileHandler.configFile.getFile().getString("Config.Language");
	
	LanguageFile(){
		super("plugins/GoBang/Languages/", language);
	}
	
	//无论如何，本插件都会维护zh_cn.ym中文的语言文件，至于其他语言的文件，则不会进行维护。
	@Override
	public void update() {
		
		//如果用户选择的语言不是zh_cn, 则不进行补全功能
		if (language.equalsIgnoreCase("zh_cn.yml") == false) {
			return;
		}
		
		//语言文件是固定的，所以一定要补齐缺失部分
		/** 基础的语言部分 **/
		if(!getFile().contains("Language.errorArgs")){
			getFile().set("Language.errorArgs", "&a[五子棋] &6很抱歉，指令格式输入有误。请输入/GoBang Help来查看帮助");
		}
		
		if(!getFile().contains("Language.notFoundCommand")){
			getFile().set("Language.notFoundCommand", "&a[五子棋] &6很抱歉，指没有找到该指令。请输入/GoBang Help来查看帮助");
		}
		
		if(!getFile().contains("Language.notUseByConsole")){
			getFile().set("Language.notUseByConsole", "&a[五子棋] &6很抱歉，该指令不能由控制台执行");
		}
		
		if(!getFile().contains("Language.notPermissions")){
			getFile().set("Language.notPermissions", "&a[五子棋] &6很抱歉，你需要&a[OBJECT1]&6权限来执行");
		}
		
		
		
		/** 指令 **/
		if(!getFile().contains("Language.create_CreateSuccess")){
			getFile().set("Language.create_CreateSuccess", "&a[五子棋] &6创建游戏场地[OBJECT1]成功！");
		}
		
		if(!getFile().contains("Language.create_AlreadyExist")){
			getFile().set("Language.create_AlreadyExist", "&a[五子棋] &6创建游戏场地[OBJECT1]失败，该场地已存在！");
		}
		
		if(!getFile().contains("Language.setLobby_NotFoundGame")){
			getFile().set("Language.setLobby_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.setLobby_Success")){
			getFile().set("Language.setLobby_Success", "&a[五子棋] &6成功设置场地[OBJECT1]的大厅");
		}
		
		if(!getFile().contains("Language.setSpawn_NotFoundGame")){
			getFile().set("Language.setSpawn_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.setSpawn_Success")){
			getFile().set("Language.setSpawn_Success", "&a[五子棋] &6成功设置场地[OBJECT1]的出生点");
		}
		
		if(!getFile().contains("Language.setSpectator_NotFoundGame")){
			getFile().set("Language.setSpectator_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.setSpectator_Success")){
			getFile().set("Language.setSpectator_Success", "&a[五子棋] &6成功设置场地[OBJECT1]的观战点");
		}
		
		if(!getFile().contains("Language.setEnd_NotFoundGame")){
			getFile().set("Language.setEnd_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.setEnd_Success")){
			getFile().set("Language.setEnd_Success", "&a[五子棋] &6成功设置场地[OBJECT1]的结束传送点");
		}
		
		if(!getFile().contains("Language.setMaxSpectators_NotFoundGame")){
			getFile().set("Language.setMaxSpectators_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.setMaxSpectators_Success")){
			getFile().set("Language.setMaxSpectators_Success", "&a[五子棋] &6成功设置场地[OBJECT1]的最大观战人数");
		}
		
		if(!getFile().contains("Language.setMaxSpectators_IsRunning")){
			getFile().set("Language.setMaxSpectators_IsRunning", "&a[五子棋] &6很抱歉，该游戏场地正在运行，请先关闭该场地再设置!");
		}
		
		if(!getFile().contains("Language.reload_IsRunning")){
			getFile().set("Language.reload_IsRunning", "&a[五子棋] &6很抱歉，游戏场地[OBJECT1]正在运行，请先结束该游戏场地再重载配置文件");
		}
		
		if(!getFile().contains("Language.reload_Success")){
			getFile().set("Language.reload_Success", "&a[五子棋] &6重载本插件的所有配置文件成功！");
		}
		
		if(!getFile().contains("Language.join_Success")){
			getFile().set("Language.join_Success", "&a[五子棋] &6加入游戏该游戏场地成功！");
		}
		
		if(!getFile().contains("Language.join_AlreadyFull")){
			getFile().set("Language.join_AlreadyFull", "&a[五子棋] &6很抱歉，该游戏场地已经满人了，请稍后再试！");
		}
		
		if(!getFile().contains("Language.join_NotFoundGame")){
			getFile().set("Language.join_NotFoundGame", "&a[五子棋] &6加入失败！没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.join_NotLobby")){
			getFile().set("Language.join_NotLobby", "&a[五子棋] &6加入失败！该游戏场地没有设置大厅");
		}
		
		if(!getFile().contains("Language.join_NotSpawn")){
			getFile().set("Language.join_NotSpawn", "&a[五子棋] &6加入失败！该游戏场地没有设置出生点");
		}
		
		if(!getFile().contains("Language.join_NotSpectator")){
			getFile().set("Language.join_NotSpectator", "&a[五子棋] &6加入失败！该游戏场地没有设置观战点");
		}
		
		if(!getFile().contains("Language.join_NotEnd")){
			getFile().set("Language.join_NotEnd", "&a[五子棋] &6加入失败！该游戏场地没有设置结束传送点");
		}
		
		if(!getFile().contains("Language.join_AlreadyJoin")){
			getFile().set("Language.join_AlreadyJoin", "&a[五子棋] &6很抱歉，你已经加入了一场游戏，请先退出已加入的游戏场地");
		}
		
		if(!getFile().contains("Language.join_CannotJoinState")){
			getFile().set("Language.join_CannotJoinState", "&a[五子棋] &6很抱歉，该游戏场地当前无法加入，请稍后再试");
		}
		
		if(!getFile().contains("Language.leave_NotInGame")){
			getFile().set("Language.leave_NotInGame", "&a[五子棋] &6很抱歉，你不在游戏中，无法退出游戏！");
		}
		
		if(!getFile().contains("Language.leave_Success")){
			getFile().set("Language.leave_Success", "&a[五子棋] &6你已成功退出游戏场地");
		}
		
		if(!getFile().contains("Language.delete_NotFoundGame")){
			getFile().set("Language.delete_NotFoundGame", "&a[五子棋] &6游戏场地[OBJECT1]不存在，无法删除！");
		}
		
		if(!getFile().contains("Language.delete_AlreadyStartRun")){
			getFile().set("Language.delete_AlreadyStartRun", "&a[五子棋] &6游戏场地[OBJECT1]正在运行中，请输入/GoBang ForceShutGame强制结束该游戏场地后再删除！");
		}
		
		if(!getFile().contains("Language.delete_Success")){
			getFile().set("Language.delete_Success", "&a[五子棋] &6删除游戏场地[OBJECT1]成功！(游戏场地的建筑物请自行清理)");
		}
		
		if(!getFile().contains("Language.forceShutGame_NotFoundGame")){
			getFile().set("Language.forceShutGame_NotFoundGame", "&a[五子棋] &6很抱歉，没有找到游戏场地[OBJECT1]");
		}
		
		if(!getFile().contains("Language.forceShutGame_NotRunning")){
			getFile().set("Language.forceShutGame_NotRunning", "&a[五子棋] &6很抱歉，游戏场地[OBJECT1]并没有在运行");
		}
		
		if(!getFile().contains("Language.forceShutGame_Success")){
			getFile().set("Language.forceShutGame_Success", "&a[五子棋] &6强制关闭游戏场地[OBJECT1]成功！");
		}
		
		if(!getFile().contains("Language.forceShutGames_Success")){
			getFile().set("Language.forceShutGames_Success", "&a[五子棋] &6强制关闭所有的游戏场地成功！");
		}
		
		if(!getFile().contains("Language.forceShutGames_NotChessGame")){
			getFile().set("Language.forceShutGames_NotChessGame", "&a[五子棋] &6很抱歉，当前没有任何在运行的游戏场地可以关闭");
		}
		
		/** 场地 **/
		
		if(!getFile().contains("Language.lobby_CountDown")){
			getFile().set("Language.lobby_CountDown", "&a[五子棋] &6游戏将在[OBJECT1]秒后开始");
		}
		
		if(!getFile().contains("Language.lobby_GameStart")){
			getFile().set("Language.lobby_GameStart", "&a[五子棋] &6游戏开始！");
		}
		
		if(!getFile().contains("Language.death")){
			getFile().set("Language.death", "&a[五子棋] &6由于你在参与游戏的过程中死亡，你已退出该游戏场地！");
		}
		
		if(!getFile().contains("Language.leave")){
			getFile().set("Language.leave", "&a[五子棋] &6玩家[OBJECT1]退出了游戏    [[OBJECT2]/[OBJECT3]]");
		}
		
		if(!getFile().contains("Language.join")){
			getFile().set("Language.join", "&a[五子棋] &6玩家[OBJECT1]加入了游戏    [[OBJECT2]/[OBJECT3]]");
		}
		
		if(!getFile().contains("Language.prepare_LeftTime")){
			getFile().set("Language.prepare_LeftTime", "&a[五子棋] &6准备时间还有[OBJECT1]秒");
		}
		
		if(!getFile().contains("Language.prepare_Over")){
			getFile().set("Language.prepare_Over", "&a[五子棋] &6准备时间结束");
		}
		
		if(!getFile().contains("Language.play_LeftTime")){
			getFile().set("Language.play_LeftTime", "&a[五子棋] &6棋局时间还剩下[OBJECT1]秒");
		}
		
		if(!getFile().contains("Language.play_StepTime")){
			getFile().set("Language.play_StepTime", "&a[五子棋] &6落子时间还剩下[OBJECT1]秒");
		}
		
		if(!getFile().contains("Language.play_Over")){
			getFile().set("Language.play_Over", "&a[五子棋] &6时间到，期局结束，本场为和局！该游戏场地将进入结束阶段");
		}
		
		if(!getFile().contains("Language.play_Win")){
			getFile().set("Language.play_Win", "&a[五子棋] &6棋局结束，本场为胜局，[OBJECT1]&6胜利!");
		}
		
		if(!getFile().contains("Language.chessMenFinished")){
			getFile().set("Language.chessMenFinished", "&a[五子棋] &6棋子已下完，棋局结束，本场为和局！");
		}
		
		if(!getFile().contains("Language.end_LeftTime")){
			getFile().set("Language.end_LeftTime", "&a[五子棋] &6该游戏场地将在[OBJECT1]秒后重置，请输入/GoBang Leave离开");
		}
		
		if(!getFile().contains("Language.over")){
			getFile().set("Language.over", "&a[五子棋] &6游戏结束！你已被传送到结束点");
		}
		
		if(!getFile().contains("Language.escape")){
			getFile().set("Language.escape", "&a[五子棋] &6玩家[OBJECT1]逃跑啦！本场棋胜利者是[OBJECT2]");
		}
		
		if(!getFile().contains("Language.cannotPlayChess")){
			getFile().set("Language.cannotPlayChess", "&a[五子棋] &6很抱歉，你现在不能落子");
		}
		
		if(!getFile().contains("Language.play_ChessGamePlayer_OverTime")){
			getFile().set("Language.play_ChessGamePlayer_OverTime", "&a[五子棋] &6很抱歉，你超时了！系统已跳过你的落子阶段");
		}
		
		if(!getFile().contains("Language.play_OverTime")){
			getFile().set("Language.play_OverTime", "&a[五子棋] &6棋手[OBJECT1]超时！系统跳过其的落子阶段");
		}
		
		if(!getFile().contains("Language.play_NotInsideChessBoard")){
			getFile().set("Language.play_NotInsideChessBoard", "&a[五子棋] &6你不能在棋盘外落子!");
		}
		
		if(!getFile().contains("Language.play_AlreadyPlayChessMan")){
			getFile().set("Language.play_AlreadyPlayChessMan", "&a[五子棋] &6很抱歉,该位置已经有棋子了,无法落子!");
		}
		
		if(!getFile().contains("Language.forceShutGame")){
			getFile().set("Language.forceShutGame", "&a[五子棋] &6当前游戏场地被强制结束！");
		}
		saveFile();
	}

	//复写父类的reloadFile，因为这里需要根据config.yml中指定的语言文件来加载，因此要修改LanguageFile的filePath属性
	public void reloadFile() {
		FileHandler.configFile.getFile().getString("Config.Language");
		super.reloadFile();
	}
	
}

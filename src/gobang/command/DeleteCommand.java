package gobang.command;

import java.io.File;

import gobang.GoBang;
import gobang.chess.ChessGame;
import gobang.chess.ChessManager;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;

public class DeleteCommand extends CommandClass{

	public DeleteCommand() {
		super("Delete","GoBang.admin.delete", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 2) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		/** 判断指定的游戏场地是否存在 **/
		String name = args[1];
		ChessManager chessManager = ChessManager.findChessManagerByName(name);
		//如果场地已存在，则不能重复创建
		if (chessManager == null) {
			LanguageUtil.sendMessage(sender, "delete_NotFoundGame", name);
			return;
		}
		
		/** 判断指定的游戏场地是否有玩家参与 **/
		//注意：这里要判断的是是否有玩家参与，而不是游戏是否开始。
		//因为，如果有玩家参与，玩家的物品其实已经被清空了。
		ChessGame chessGame = ChessGame.findChessGameByName(name);
		//如果要删除的游戏场地已经开始运行
		//注意：只要游戏场地中有人，就算游戏场地正在运行！
		if (chessGame.isTakePartIn() == true) {
			LanguageUtil.sendMessage(sender, "delete_AlreadyStartRun", name);
			return;
		}
		
		/** 将指定的游戏场地配置文件从本地磁盘删除 **/
		File file = new File("plugins/GoBang/Games/" + name + ".yml");
		file.delete();

		/** 更新插件的内存 **/
		//更新ChessManager
		for (int index = 0; index < GoBang.chessManagers.size(); index++) {
			if (name.equals(GoBang.chessManagers.get(index).getName())) {
				GoBang.chessManagers.remove(index);
			}
		}
		
		for (int index = 0; index < GoBang.chessGames.size(); index++) {
			if (name.equals(GoBang.chessGames.get(index).getName())) {
				GoBang.chessGames.remove(index);
			}
		}
		
		LanguageUtil.sendMessage(sender, "delete_Success", name);
	}
	
}

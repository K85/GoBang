package gobang.command;

import gobang.GoBang;
import gobang.chess.ChessGame;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;



public class ReloadCommand extends CommandClass{

	public ReloadCommand() {
		super("Reload","GoBang.admin.reload", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}

		/** 判断游戏场地的情况 
		 * 	如果有游戏场地正在运行, 则不允许重载配置文件
		 * **/	
		for (ChessGame chessGame : GoBang.chessGames) {
			
			if (chessGame.isTakePartIn() == true) {
				LanguageUtil.sendMessage(sender, "reload_IsRunning", chessGame.getName());
				return;
			}
			
		}
		
		GoBang.getPlugin().reload();
		LanguageUtil.sendMessage(sender, "reload_Success");
	}
}

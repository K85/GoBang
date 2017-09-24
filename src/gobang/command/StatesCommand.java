package gobang.command;

import gobang.GoBang;
import gobang.chess.ChessGame;
import gobang.util.LanguageUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;



public class StatesCommand extends CommandClass{

	public StatesCommand() {
		super("States","GoBang.admin.States", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		sender.sendMessage(ChatColor.GREEN+"--------------游戏场地状况列表--------------");
		
		//注意：这里选择遍历ChessGame，而不是ChessManager。
		//是因为ChessGame中封装了ChessManager
		int index = 1;
		for (ChessGame chessGame : GoBang.chessGames) {
			
			sender.sendMessage("§1§l[" + index + "] §a" + chessGame.getName());
			sender.sendMessage("§d游戏状态: " + chessGame.getState().getState());
			int nowPlayers = chessGame.chessGamePlayers.size();
			int maxPlayers = chessGame.getMaxPlayers();
			sender.sendMessage("§d玩家人数: " + nowPlayers + " / " + maxPlayers);
			
			index++;
		}
		
		if (index == 1) {
			sender.sendMessage(ChatColor.GREEN+"你还没有创建任何游戏场地!");
		}
	
	}
	
}

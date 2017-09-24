package gobang.command;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class LeaveCommand extends CommandClass{

	public LeaveCommand() {
		super("Leave","GoBang.play.Leave", false);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}

		/** ChessGame的游戏状态判断 **/
		
		Player player = (Player) sender;
		if (ChessGamePlayer.isGamePlayer(player) == false) {
			LanguageUtil.sendMessage(sender, "leave_NotInGame");
			return;
		}
		
		//先获取要退出游戏玩家所在的ChessGame
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		//再调用ChessGame中的leavePlayer方法
		chessGame.leavePlayer(player);
		
		//给该玩家发送退出游戏场地的信息
		LanguageUtil.sendMessage(sender, "leave_Success");
		
		//给该玩家所退出的游戏场地中所有其他的玩家发送退出信息
		chessGame.sendMessage("leave", player.getName()
				, "" + chessGame.chessGamePlayers.size()
				, "" + chessGame.getMaxPlayers());
	}
}

package gobang.command;

import gobang.chess.ChessGame;
import gobang.chess.ChessGameState;
import gobang.timer.CheckTimer;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;

public class ForceShutGameCommand extends CommandClass{

	public ForceShutGameCommand() {
		super("ForceShutGame","GoBang.admin.forceShutGame", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 2) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		/** 判断游戏场地是否存在 **/
		String name = args[1];
		ChessGame chessGame = ChessGame.findChessGameByName(name);
		//如果场地不存在，则提示
		//注意：其实这里判断ChessManager也可以，不一定要判断ChessGame
		if (chessGame == null) {
			LanguageUtil.sendMessage(sender, "forceShutGame_NotFoundGame", name);
			return;
		}
		
		//判断游戏场地是否在运行
		if (chessGame.isTakePartIn() == false) {
			LanguageUtil.sendMessage(sender, "forceShutGame_NotRunning", name);
			return;
		}
		
		//给所有在该游戏场地中的玩家发送强制结束的提示
		chessGame.sendMessage("forceShutGame");
		
		//先更改游戏状态
		chessGame.setState(ChessGameState.FREE);
		chessGame.overGame();
		
		//重新启动该游戏场地的CheckTimer来检查玩家的加入
		new CheckTimer(chessGame, 10);
		
		LanguageUtil.sendMessage(sender, "forceShutGame_Success", name);
	}
	

}

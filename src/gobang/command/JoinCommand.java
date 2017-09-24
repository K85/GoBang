package gobang.command;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.chess.ChessGameState;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;



public class JoinCommand extends CommandClass{

	public JoinCommand() {
		super("Join","GoBang.play.join", false);
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
			LanguageUtil.sendMessage(sender, "join_NotFoundGame", name);
			return;
		}
		
		/** 判断游戏场地是否配置完整 **/
		if (chessGame.getChessManager().getLobby() == null) {
			LanguageUtil.sendMessage(sender, "join_NotLobby");
			return;
		}
		if (chessGame.getChessManager().getSpawn() == null) {
			LanguageUtil.sendMessage(sender, "join_NotSpawn");
			return;
		}
		if (chessGame.getChessManager().getSpectator() == null) {
			LanguageUtil.sendMessage(sender, "join_NotSpectator");
			return;
		}
		if (chessGame.getChessManager().getEnd() == null) {
			LanguageUtil.sendMessage(sender, "join_NotEnd");
			return;
		}
		
		Player player = (Player) sender;
		
		/** 判断玩家是否已经加入了游戏 **/
		if (ChessGamePlayer.isGamePlayer(player) == true) {
			LanguageUtil.sendMessage(sender, "join_AlreadyJoin");
			return;
		}
		
		/** ChessGame的游戏状态判断 **/
		//如果指定的游戏场地处于指定状态, 则不能加入游戏
		//说明: 这两个状态的时间较短, 禁止加入
		if (chessGame.getState() == ChessGameState.PREPARE
				|| chessGame.getState() == ChessGameState.END) {
			/**这里考虑观战者模式**/
			LanguageUtil.sendMessage(sender, "join_CannotJoinState");
			return;
		}
		
		//判断该游戏场地是否已经满人了
		if (chessGame.getChessGamePlayers().size() == chessGame.getMaxPlayers()) {
			LanguageUtil.sendMessage(sender, "join_AlreadyFull");
			return;
		}
		
		/** 让玩家加入该游戏场地 **/
		
		chessGame.joinPlayer(player);
		
		//给所有游戏中的玩家发送该玩家加入游戏的信息
		chessGame.sendMessage("join", player.getName()
				, "" + chessGame.chessGamePlayers.size()
				, "" + chessGame.getMaxPlayers());
		
		LanguageUtil.sendMessage(sender, "join_Success");
	}
}

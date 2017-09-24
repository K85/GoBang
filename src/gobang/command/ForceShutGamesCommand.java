package gobang.command;

import gobang.GoBang;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;

public class ForceShutGamesCommand extends CommandClass{

	public ForceShutGamesCommand() {
		super("ForceShutGames","GoBang.admin.forceShutGames", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		int count = GoBang.forceShutGames();
		
		if (count == 0) {
			LanguageUtil.sendMessage(sender, "forceShutGames_NotChessGame");
		} else {
			LanguageUtil.sendMessage(sender, "forceShutGames_Success");
		}

	}

}

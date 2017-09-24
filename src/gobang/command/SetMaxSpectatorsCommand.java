package gobang.command;

import java.io.File;
import java.io.IOException;

import gobang.GoBang;
import gobang.chess.ChessGame;
import gobang.chess.ChessManager;
import gobang.command.CommandClass;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

public class SetMaxSpectatorsCommand extends CommandClass{

	public SetMaxSpectatorsCommand() {
		super("SetMaxSpectators","GoBang.admin.SetMaxSpectators", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 3) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		String name = args[1];
		ChessManager chessManager = ChessManager.findChessManagerByName(name);
		//如果场地不存在，则提示
		if (chessManager == null) {
			LanguageUtil.sendMessage(sender, "setMaxSpectators_NotFoundGame", name);
			return;
		}

		//判断输入的是否为数字
		if (PluginCommand.isNumber(args[2]) == false) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		/** 
		 * 判断游戏场地是否有人
		 **/
		ChessGame chessGame = ChessGame.findChessGameByName(name);
		if (chessGame.isTakePartIn() == true) {
			LanguageUtil.sendMessage(sender, "setMaxSpectators_IsRunning");
			return;
		}
		
		//写入配置文件
		File file = new File("plugins/GoBang/Games/" + name + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		int amount = Integer.parseInt(args[2]);
		config.set("Game.Base.MaxSpectators", amount);
		
		
		try {
			config.save(file);
		} catch (IOException e) {
			GoBang.getPlugin().printException("在设置最大观战玩家数时保存配置文件失败");
			e.printStackTrace();
		}
		
		/** 更新插件的内存 **/
		chessManager.setMaxSpectators(Integer.parseInt(args[2]));
		
		LanguageUtil.sendMessage(sender, "setMaxSpectators_Success", name);
	}
	
}

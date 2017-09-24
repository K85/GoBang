package gobang.command;


import java.io.File;
import java.io.IOException;

import gobang.GoBang;
import gobang.chess.ChessManager;
import gobang.util.LanguageUtil;

import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;


public class SetSpectatorCommand extends CommandClass{

	public SetSpectatorCommand() {
		super("SetSpectator","GoBang.admin.SetSpectator", false);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 2) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		String name = args[1];
		ChessManager chessManager = ChessManager.findChessManagerByName(name);
		//如果场地不存在，则提示
		if (chessManager == null) {
			LanguageUtil.sendMessage(sender, "setSpectator_NotFoundGame", name);
			return;
		}

		//写入配置文件
		File file = new File("plugins/GoBang/Games/" + name + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Player player = (Player) sender;
		config.set("Game.Area.Spectator", ChessManager.getStringByLocation(player.getLocation()));
		
		
		try {
			config.save(file);
		} catch (IOException e) {
			GoBang.getPlugin().printException("在设置观战点时保存配置文件失败");
			e.printStackTrace();
		}
		
		/** 更新插件的内存 **/
		chessManager.setSpectator(player.getLocation());
		
		LanguageUtil.sendMessage(sender, "setSpectator_Success", name);
	}
	
}

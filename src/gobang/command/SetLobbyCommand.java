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


public class SetLobbyCommand extends CommandClass{

	public SetLobbyCommand() {
		super("SetLobby","GoBang.admin.SetLobby", false);
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
			LanguageUtil.sendMessage(sender, "setLobby_NotFoundGame", name);
			return;
		}
		
		/** 判断游戏场地是否有人
		 * 	注意：这些传送点的设置不需要管游戏的运行状态。
		 * 	游戏即使真的在运行中，你修改一下传送点也不会造成什么影响
		 *  **/

		//写入配置文件
		File file = new File("plugins/GoBang/Games/" + name + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		Player player = (Player) sender;
		config.set("Game.Area.Lobby", ChessManager.getStringByLocation(player.getLocation()));
		
		
		try {
			config.save(file);
		} catch (IOException e) {
			GoBang.getPlugin().printException("在设置大厅时保存配置文件失败");
			e.printStackTrace();
		}
		
		/** 更新插件的内存 **/
		chessManager.setLobby(player.getLocation());
		
		LanguageUtil.sendMessage(sender, "setLobby_Success", name);
	}
	
}

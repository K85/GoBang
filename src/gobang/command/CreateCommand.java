package gobang.command;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gobang.GoBang;
import gobang.chess.ChessManager;
import gobang.util.LanguageUtil;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class CreateCommand extends CommandClass{

	public CreateCommand() {
		super("Create","GoBang.admin.create", false);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args){
		
		if (args.length != 2) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		String name = args[1];
		ChessManager chessManager = ChessManager.findChessManagerByName(name);
		//如果场地已存在，则不能重复创建
		if (chessManager != null) {
			LanguageUtil.sendMessage(sender, "create_AlreadyExist", name);
			return;
		}
		

		
		File file = new File("plugins/GoBang/Games/" + name + ".yml");
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);
		
		/** 设置基础的游戏场地信息 **/
		config.set("Game.Base.LobbyTime", 20);
		config.set("Game.Base.PlayTime", 600);
		config.set("Game.Base.StepTime", 15);
		config.set("Game.Base.MaxSpectators", 10);
		
		/** 设置棋手的信息 **/
		config.set("Game.Player.ChessPlayer.CanFly", true);
		config.set("Game.Player.ChessPlayer.CanChatWithSpectators", true);
		
		/** 设置观战者的信息 **/
		config.set("Game.Player.Spectator.CanFly", false);
		
		/** 设置游戏场地的重要标识信息 **/
		//Location指的是棋盘的右下角标识位置, 即取执行玩家脚下位置
		Player player = (Player) sender;
		config.set("Game.Area.Location", ChessManager.getStringByLocation(player.getLocation()));
	
		try {
			config.save(file);
		} catch (IOException e) {
			GoBang.getPlugin().printException("在创建游戏场地时保存配置文件失败");
			e.printStackTrace();
		}
		
		/** 生成游戏场地 **/
		createDefaultChessBoard();
		player.performCommand("/schematic load ChessBoard.schematic");
		player.performCommand("/paste");
		
		/** 更新插件的内存 **/
		GoBang.getPlugin().loadChessManager(name + ".yml");
		GoBang.getPlugin().initChessGame(name);
		
		LanguageUtil.sendMessage(sender, "create_CreateSuccess", name);
	}
	
	public void createDefaultChessBoard() {
		//从插件的路径下获取默认的ChessBoard文件
		InputStream is = GoBang.getPlugin().getResource("ChessBoard.schematic");
		//写出ChessBoard文件到WorldEdit的目录下
		BufferedInputStream bis=new BufferedInputStream(is);
		OutputStream os = null;		
		
		//从插件目录读取文件
		try {
			//创建多级目录
			new File("plugins/WorldEdit/schematics").mkdirs();
			
			os = new FileOutputStream(new File("plugins/WorldEdit/schematics/ChessBoard.schematic"));
		} catch (FileNotFoundException e) {
			GoBang.getPlugin().printException("在生成默认的棋盘建筑文件时没有找到WorldEdit的相关目录");
			e.printStackTrace();
		}
			   
		//从内存中写出文件到本地磁盘
		int ch = 0;
		 try {
			 
			while ((ch = bis.read()) != -1) {
				   os.write(ch);
			}
			
		} catch (IOException e) {
			GoBang.getPlugin().printException("在生成默认的棋盘建筑文件时写出数据失败");
			e.printStackTrace();
		}
		 
		 
		 //关闭流
		 try {
			bis.close();
		} catch (IOException e) {
			GoBang.getPlugin().printException("在生成默认的棋盘建筑文件时关闭流资源失败");
			e.printStackTrace();
		}
		 
	}
	
}

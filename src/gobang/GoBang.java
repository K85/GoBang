package gobang;

import java.io.File;
import java.util.ArrayList;

import gobang.chess.ChessGame;
import gobang.chess.ChessGamePlayer;
import gobang.chess.ChessGameState;
import gobang.chess.ChessManager;
import gobang.command.PluginCommand;
import gobang.file.FileHandler;
import gobang.listener.BlockListener;
import gobang.listener.InventoryListener;
import gobang.listener.PlayerListener;
import gobang.timer.CheckTimer;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class GoBang extends JavaPlugin{
	private static GoBang plugin;
	
	public static ArrayList<ChessManager> chessManagers
		= new ArrayList<ChessManager>();
	public static ArrayList<ChessGame> chessGames
		= new ArrayList<ChessGame>();
	//用于存储所有参与游戏的游戏玩家
	public static ArrayList<ChessGamePlayer> chessGamePlayers
		= new ArrayList<ChessGamePlayer>();
	
	public void onEnable()
	{
		getLogger().info("插件已加载");
		
		plugin=this;
		
		/** 注册指令 **/
		PluginCommand pluginCommand=new PluginCommand();
		getCommand("GoBang").setExecutor(pluginCommand);
		
		/** 创建文件处理程序 **/
		new FileHandler();

		/** 注册监听器 **/
	    Bukkit.getPluginManager().registerEvents(new PlayerListener(), this);
	    Bukkit.getPluginManager().registerEvents(new BlockListener(), this);
	    Bukkit.getPluginManager().registerEvents(new InventoryListener(), this);
	    
	    /** 加载配置 **/
	    load();
	  }
	
	public void onDisable()
	  {
		int count = GoBang.forceShutGames(true);
		getLogger().info("强制关闭了" + count + "个正在运行的游戏场地");
		
	    getLogger().info("插件已卸载");
	  }
	
	public static GoBang getPlugin()
	{
		return plugin;
	}
	
	public void printException(String exceptionReason){
		CommandSender commandSender=getServer().getConsoleSender();
		commandSender.sendMessage("§c------------------------");
		commandSender.sendMessage("§c插件名称：GoBang");
		commandSender.sendMessage("§c版本号:v1.0");
		commandSender.sendMessage("§c联系作者：QQ526026058");
		commandSender.sendMessage("§c------------------------");
		commandSender.sendMessage("§c异常原因：" + exceptionReason);
	}
	
	public void printWarn(String warnReason){
		CommandSender commandSender=getServer().getConsoleSender();
		commandSender.sendMessage("§c------------------------");
		commandSender.sendMessage("§c插件名称：GoBang");
		commandSender.sendMessage("§c版本号:v1.0");
		commandSender.sendMessage("§c联系作者：QQ526026058");
		commandSender.sendMessage("§c------------------------");
		commandSender.sendMessage("§c警告：" + warnReason);
	}
	
	public void load() {
		getLogger().info("----------加载配置文件----------");
		loadChessManagers();
		initChessGames();
	}
	
	public void reload() {
		
		FileHandler.configFile.reloadFile();
		FileHandler.languageFile.reloadFile();
		
		load();
	}
	
	//从本地磁盘读取所有ChessManager的配置信息
	public void loadChessManagers() {
		
		//清空已存的ChessManager
		GoBang.chessManagers.clear();
		
		File file=new File("plugins/GoBang/Games/");
		File[] files=file.listFiles();
		
		//遍历Games文件夹中的所有File
		for (File f:files) {
			
			if (!f.isFile())
				continue;
			
			if (!f.getName().endsWith(".yml"))
				continue;
			
			//加载指定语言组
			getLogger().info("加载游戏场地文件: " + f.getName());
			loadChessManager(f.getName());
		}
	}
	
	//从本地磁盘读取指定的ChessManager的配置信息
	public void loadChessManager(String fileName) {
		
		File file = new File("plugins/GoBang/Games/" + fileName);
		FileConfiguration config = YamlConfiguration.loadConfiguration(file);

		ChessManager chessManager = new ChessManager(fileName.replace(".yml", ""));
		chessManager.init(config);
		
		GoBang.chessManagers.add(chessManager);
	}
	
	//依据内存中的ChessManager来创建ChessGame
	public void initChessGames() {
		
		GoBang.chessGames.clear();
		
		for (ChessManager chessManager : GoBang.chessManagers) {
			initChessGame(chessManager.getName());
		}
		
	}
	
	public void initChessGame(String name) {
		ChessGame chessGame = new ChessGame(name);	
		
		//注册该游戏场地的CheckTimer
		new CheckTimer(chessGame, 10);
		
		GoBang.chessGames.add(chessGame);
	}
	
	//通过玩家的名称, 将玩家从GoBang中的chessGamePlayers中删除
	public void removeChessGamePlayerByPlayer(Player player) {
		for (int index = 0; index < GoBang.chessGamePlayers.size(); index++) {
			if (player.getName().equals(GoBang.chessGamePlayers.get(index).getPlayer().getName())) {
				GoBang.chessGamePlayers.remove(index);
			}
		}
	}
	
	//说明: 返回的是强制关闭的游戏场地的数量
	public static int forceShutGames() {
		return GoBang.forceShutGames(false);
	}
	
	
	//说明: 返回的是强制关闭的游戏场地的数量
	public static int forceShutGames(boolean onDisable) {
		int count = 0;
		//遍历GoBang中所有的游戏场地
		for (ChessGame chessGame : GoBang.chessGames) {
			//判断游戏场地是否在运行
			if (chessGame.isTakePartIn() == false) {
				continue;
			}
			
			//给所有在该游戏场地中的玩家发送强制结束的提示
			chessGame.sendMessage("forceShutGame");
			
			//先更改游戏状态
			chessGame.setState(ChessGameState.FREE);
			chessGame.overGame();
			
			//判断是否在插件卸载时强制结束所有游戏场地, 如果是的话, 
			//则不需要重新注册CheckTimer时间任务
			if (onDisable == false) {
				new CheckTimer(chessGame, 10);
			}
			
			count++;
		}
		
		return count;
	}
}

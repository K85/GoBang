package gobang.chess;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;

import gobang.GoBang;

/** 该类用于描述一个游戏场地的配置文件信息，并描述所有游戏场地的共享配置信息 **/
public class ChessManager {
	
	/** 游戏场地名称 **/
	private String name;
	
	/** 游戏场地的基本设置 **/
	private int lobbyTime = 0;
	private int playTime = 0;
	private int stepTime = 0;
	private int maxSpectators = 0;
	
	/** 游戏场地的玩家设置 **/
	private boolean playerCanFly = false;
	private boolean playerCanChat = false;
	private boolean spectatorCanFly = false;
	
	/** 游戏场地的重要标识信息 **/
	//location表示棋盘的右下角，即玩家用Create指令时站立的位置
	private Location location = null;
	
	/** 游戏场地的传送点设置 **/
	private Location lobby = null;
	private Location spawn = null;
	private Location spectator = null;
	private Location end = null;
	
	
	//注意：ChessManager的fileName就是场地名
	public ChessManager(String name) {
		this.name = name;
	}
	
	//用于初始化ChessManager，在实例化该对象后调用以相应的加载本地配置文件
	public void init(FileConfiguration config) {
		
		lobbyTime = config.getInt("Game.Base.LobbyTime");
		playTime = config.getInt("Game.Base.PlayTime");
		stepTime = config.getInt("Game.Base.StepTime");
		maxSpectators = config.getInt("Game.Base.MaxSpectators");
		
		playerCanFly = config.getBoolean("Game.Player.ChessPlayer.CanFly");
		playerCanChat = config.getBoolean("Game.Player.ChessPlayer.CanChatWithSpectators");
		spectatorCanFly = config.getBoolean("Game.Player.Spectator.CanFly");
		
		location = ChessManager.getLocationByString(config.getString("Game.Area.Location"));
		
		//注意：如果没有设置的话，读取回来的就是null
		lobby = ChessManager.getLocationByString(config.getString("Game.Area.Lobby"));
		spawn = ChessManager.getLocationByString(config.getString("Game.Area.Spawn"));
		spectator = ChessManager.getLocationByString(config.getString("Game.Area.Spectator"));
		end = ChessManager.getLocationByString(config.getString("Game.Area.End"));
	}
	
	
	//通过游戏场地的名称来获取指定游戏场地的配置信息
	public static ChessManager findChessManagerByName(String name) {
		
		for (ChessManager chessManager : GoBang.chessManagers) {
			
			if (name.equals(chessManager.getName())) {
				return chessManager;
			}
			
		}
		
		return null;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	public static String getStringByLocation(Location loc) {
		
		String world = loc.getWorld().getName();
		String x = loc.getBlockX() + "";
		String y = loc.getBlockY() + "";
		String z = loc.getBlockZ() + "";
		
		return world + "," + x + "," + y + "," + z;
	}
	
	public static Location getLocationByString(String str) {
		
		//如果传入的str是空，则可以直接返回null
		if (str == null) {
			return null;
		}
		
		String[] temp = str.split(",");
		
		World world = GoBang.getPlugin().getServer().getWorld(temp[0]);
		double x = Double.parseDouble(temp[1]);
		double y = Double.parseDouble(temp[2]);
		double z = Double.parseDouble(temp[3]);
		
		return new Location(world, x, y, z);
	}

	public Location getLobby() {
		return lobby;
	}

	public void setLobby(Location lobby) {
		this.lobby = lobby;
	}

	public Location getSpawn() {
		return spawn;
	}

	public void setSpawn(Location spawn) {
		this.spawn = spawn;
	}

	public Location getSpectator() {
		return spectator;
	}

	public void setSpectator(Location spectator) {
		this.spectator = spectator;
	}

	public Location getEnd() {
		return end;
	}

	public void setEnd(Location end) {
		this.end = end;
	}

	public int getMaxSpectators() {
		return maxSpectators;
	}

	public void setMaxSpectators(int maxSpectators) {
		this.maxSpectators = maxSpectators;
	}

	public int getLobbyTime() {
		return lobbyTime;
	}

	public void setLobbyTime(int lobbyTime) {
		this.lobbyTime = lobbyTime;
	}

	public int getPlayTime() {
		return playTime;
	}

	public void setPlayTime(int playTime) {
		this.playTime = playTime;
	}

	public int getStepTime() {
		return stepTime;
	}

	public void setStepTime(int stepTime) {
		this.stepTime = stepTime;
	}

	public boolean isPlayerCanFly() {
		return playerCanFly;
	}

	public void setPlayerCanFly(boolean playerCanFly) {
		this.playerCanFly = playerCanFly;
	}

	public boolean isSpectatorCanFly() {
		return spectatorCanFly;
	}

	public void setSpectatorCanFly(boolean spectatorCanFly) {
		this.spectatorCanFly = spectatorCanFly;
	}

	public Location getLocation() {
		return location;
	}

	public boolean isPlayerCanChat() {
		return playerCanChat;
	}

	public void setPlayerCanChat(boolean playerCanChat) {
		this.playerCanChat = playerCanChat;
	}
	
}

package gobang.chess;

import gobang.GoBang;
import gobang.timer.CheckTimer;
import gobang.timer.EndTimer;
import gobang.util.ItemUtil;
import gobang.util.LanguageUtil;

import java.util.ArrayList;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.ItemMeta;

/** 用于描述一个游戏场地, 游戏场地是本插件的关键 **/
public class ChessGame {
	
	private String name = null;
	private ChessManager chessManager = null;
	private ChessGameState state = ChessGameState.FREE;

	/** black和white表示当前游戏场地的棋手 **/
	private ChessGamePlayer black = null;
	private ChessGamePlayer white = null;
	
	/** 表示当前游戏场地轮到谁走棋了 **/
	private boolean isBlack = true;
	
	/** 表示当前游戏场地走了几步棋 **/
	private int stepNumber = 0;
	
	/** 表示当前内存中的棋子信息 **/
	private ChessMan[][] chess = null;
	private String winner = null;
	
	/** 记录上一次落子的位置 **/
	private Location lastPlayChess = null;

	//用于存储所有参与本游戏场地的游戏玩家
	public ArrayList<ChessGamePlayer> chessGamePlayers = new ArrayList<ChessGamePlayer>();
	
	public ChessGame(String name) {
		this.name = name;
		
		//通过主类ChessManager中的方法，来获取该游戏场地对应的ChessManager配置文件对象。
		chessManager = ChessManager.findChessManagerByName(name);
		
	}

	//通过场地的名称返回游戏场地
	public static ChessGame findChessGameByName(String name) {
		for (ChessGame chessGame : GoBang.chessGames) {
			if (name.equals(chessGame.getName())) {
				return chessGame;
			}
		}
		
		return null;
	}
	
	//通过普通玩家查出该玩家所在的游戏场地，如果不在游戏中返回null
	public static ChessGame findChessGameByPlayer(Player player) {
		String name = player.getName();
		
		for (ChessGamePlayer chessGamePlayer : GoBang.chessGamePlayers) {
			if (name.equals(chessGamePlayer.getPlayer().getName())) {
				return chessGamePlayer.getChessGame();
			}
		}
		
		return null;
	}
	
	public boolean checkWin(int coorX, int coorZ) {
		
		int count_Horizontal = checkHorizontal(coorX, coorZ);
		int count_Vertical = checkVertical(coorX, coorZ);
		int count_topRightSlashAndLowerLeftSlash = checkTopRightSlashAndLowerLeftSlash(coorX, coorZ);
		int count_topLeftSlashAndLowerRightSlash = checkTopLeftSlashAndLowerRightSlash(coorX, coorZ);
		
		/** 通过判断棋子的已连接数，来判断是否胜利 **/
		if (count_Horizontal >= 5 || count_Vertical >= 5 
			|| count_topRightSlashAndLowerLeftSlash >= 5 
			|| count_topLeftSlashAndLowerRightSlash >= 5){
			//设置获胜者
			winner = getNowChessPlayer().getChessGameIdentity().getDescribe();
			return true;
		}
		
		return false;
	}
	
	//以指定棋子为基准，用于判断横向有没有获胜
		public int checkHorizontal(int coorX,int coorZ) {
			return checkCount(coorX, coorZ, 1, 0);
		}
		
		//以指定棋子为基准，用于判断纵向有没有获胜
		public int checkVertical(int coorX, int coorZ) {
			return checkCount(coorX, coorZ, 0, 1);
		}
		
		//以指定棋子为基准，用于判断右上斜线和左下斜线有没有获胜
		public int checkTopRightSlashAndLowerLeftSlash(int coorX, int coorZ) {
			return checkCount(coorX, coorZ, 1, -1);	
		}
			
		//以指定棋子为基准，用于判断左上斜线和右下斜线有没有获胜
		public int checkTopLeftSlashAndLowerRightSlash(int coorX, int coorZ) {
			return checkCount(coorX, coorZ, 1, 1);	
		}
	
		//这是算法的基本实现
		public int checkCount(int coorX, int coorZ,int xChange, int zChange) {
			
			int count = 1;
			
			int color = chess[coorX][coorZ].getColor();
			
			int clone_XChange = xChange;
			int clone_YChange = zChange;
			
			while (true) {

				//先判断数数组是否越界，以防报错
				if ((coorX + xChange) > 15 || (coorX + xChange) < 0
					|| (coorZ + zChange) > 15 || (coorZ + zChange) < 0){
					break;
				}
				
				/** 防止NPE **/
				if(chess[coorX + xChange][coorZ + zChange] == null) {
					break;
				}
				
				if (color == chess[coorX + xChange][coorZ + zChange].getColor()) {
					count++;
					
					//注意：这里如果传入的y值为负数，则需要减去而不是加上。
					//因此，这里需要注意
					if (xChange != 0) {
						if (xChange > 0) {
							xChange++;
						} else if(xChange < 0) {
							xChange--;
						}
					}
					if (zChange != 0) {
						if (zChange > 0) {
							zChange++;
						} else if(zChange < 0) {
							zChange--;
						}
					}
					
				} else {
					break;
				}
			}
			
			xChange = clone_XChange;
			zChange = clone_YChange;
			
			while (true) {
				
				//先判断数数组是否越界，以防报错
				if ((coorX - xChange) > 15 || (coorX - xChange) < 0
					|| (coorZ - zChange) > 15 || (coorZ - zChange) < 0){
					break;
				}
				
				/** 防止NPE **/
				if(chess[coorX - xChange][coorZ - zChange] == null) {
					break;
				}
				
				if (color == chess[coorX - xChange][coorZ - zChange].getColor()) {
					count++;
					
					if (xChange != 0) {
						if (xChange > 0) {
							xChange++;
						} else if(xChange < 0) {
							xChange--;
						}
					}
					if (zChange != 0) {
						if (zChange > 0) {
							zChange++;
						} else if(zChange < 0) {
							zChange--;
						}
					}
					
				} else {
					break;
				}	
			}
			
			return count;
		}
		
	//该方法用于ChessGame对象通过name来查找在该ChessGame中的游戏玩家
	public ChessGamePlayer findChessGamePlayerByName(String name) {
		
		for (ChessGamePlayer chessGamePlayer : chessGamePlayers) {
			if (name.equals(chessGamePlayer.getPlayer().getName())) {
				return chessGamePlayer;
			}
		}
		
		return null;
	}

	public ChessGamePlayer getBlackChessGamePlayer() {
		return black;
	}
	
	
	public ChessGamePlayer getWhiteChessGamePlayer() {
		return white;
	}
	
	//将一个玩家加入到该游戏场地
	public void joinPlayer(Player player) {
		
		/** 插件更新内存 **/
		//为加入的普通玩家创建专属的游戏玩家对象
		ChessGamePlayer chessGamePlayer = new ChessGamePlayer(this, player);
		
		chessGamePlayers.add(chessGamePlayer);
		GoBang.chessGamePlayers.add(chessGamePlayer);
		
		/** 对玩家的操作 **/
		player.teleport(getChessManager().getLobby());
		
	}

	//将一个玩家从该游戏场地退出
	public void leavePlayer(Player player) {
		
		//传送玩家到[大厅]
		player.teleport(getChessManager().getLobby());
	
		//恢复玩家参与游戏前的Minecraft游戏信息
		ChessGamePlayer chessGamePlayer
		= findChessGamePlayerByName(player.getName());
	
		chessGamePlayer.recoverPlayerInformation();
		
		/** 调用checkLeave方法来针对该玩家的退出作出处理方式
		 * 	注意: 这里要在删除该ChessGamePlayer数据前执行处理方案
		 *  **/
		solveLeave(chessGamePlayer);
		
		//将玩家从ChessGame中删除
		for (int index = 0; index < chessGamePlayers.size(); index++) {
			if (player.getName().equals(chessGamePlayers.get(index).getPlayer().getName())) {
				chessGamePlayers.remove(index);
				break;
			}
		}
	
		//将玩家从主类的chessGamePlayers中删除
		GoBang.getPlugin().removeChessGamePlayerByPlayer(player);
		
	}
	
	//判断一个玩家从该游戏中退出的影响, 并对其的退出作出操作
	//注意: 所作出的操作要考虑
	//1. 游戏场地状态
	//2. 玩家的身份
	public void solveLeave(ChessGamePlayer chessGamePlayer) {
		
		/** 注意: 其他的游戏状态可以直接调用leave方法, 即使临时有玩家退出了游戏
		 * 	对于游戏也没有什么影响, 因此可以不对这些退出进行特别操作
		 *  **/
		
		/** 如果游戏场地状态为PREPARE 或者 PLAY **/
		if (getState() == ChessGameState.PREPARE
				|| getState() == ChessGameState.PLAY) {
			
			//如果退出的是观战者, 则可以什么也不做
			if (chessGamePlayer.getChessGameIdentity()
					== ChessGameIdentity.SPECTAROR) {
				return;
			}
			
			
			//向所有游戏玩家发送逃跑信息
			//注意: 要在删除该游戏场地的数据前给所有玩家发送信息
			ChessGamePlayer loser = chessGamePlayer;
			ChessGamePlayer winner = null;
			
			if (chessGamePlayer.getChessGameIdentity() == ChessGameIdentity.BLACK) {
				winner = getWhiteChessGamePlayer();
			} else {
				winner = getBlackChessGamePlayer();
			}
			
			sendMessage("escape", loser.getPlayer().getName()
					, winner.getPlayer().getName());
			
			//调用overGame方法
			overGame();
			
			//重新启动该游戏场地的CheckTimer来检查玩家的加入
			setState(ChessGameState.FREE);
			new CheckTimer(this, 10);
			
			return;
		}
		
	}
	
	

	//结束游戏, 用于给EndTimer调用的方法
	public void overGame() {
		
		resetPlayers();
		resetPlayGround();
	}
	
	//重置参与该游戏场地的玩家
	public void resetPlayers() {
		
		for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
			Player player = chessGamePlayer.getPlayer();
			
			//传送玩家到[结束点]
			player.teleport(getChessManager().getEnd());
			
			//恢复玩家参与游戏前的Minecraft游戏信息
			chessGamePlayer.recoverPlayerInformation();
			
			//将玩家从主类的chessGamePlayers中删除
			GoBang.getPlugin().removeChessGamePlayerByPlayer(player);
		}
		
		//清空该游戏场地的所有玩家信息
		getChessGamePlayers().clear();
		
	}
	
	//重置游戏场地
	public void resetPlayGround() {
		
		/** 将棋盘核心建筑上的棋子清空 **/
		Location loc = getMarkLocation();
		loc.setY(loc.getBlockY() + 1);
		
		Location temp = loc.clone();
		
		for (int z = 1; z <= 16; z++) {
			
			//将x值归位
			temp.setX(loc.getBlockX());
			for (int x = 1; x <= 16; x++) {
				
				if (temp.getBlock() != null
						||temp.getBlock().getType() != Material.AIR) {
					temp.getBlock().setType(Material.AIR);
					
				}
				
				temp.setX(loc.getBlockX() + x);
			}
			
			temp.setZ(loc.getBlockZ() + z);
		}
		
		/** 清除上一次落子的标记 **/
		if (lastPlayChess != null) {
			lastPlayChess.getBlock().setType(Material.WORKBENCH);
		}
	}
	
	//开始游戏, 用于给LobbyTimer调用的方法
	public void prepareGame() {
		//注意: 这里准备游戏的物品, 就可以先告诉玩家游戏开始了.
		//因为, PrepareTimer本身只是为了延时5秒再开始游戏, 没有实际的意义
		sendMessage("lobby_GameStart");
		
		/** 给参与玩家分配角色
		 * 	注意: 参与玩家所分配的角色, 按照参与顺序来分.
		 * 	第一个和第二个加入游戏的为黑子和白子, 其余角色都为观战者。
		 * 	利用ArrayList的特性
		 *  **/
		
		int index = 0;
		for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
			
			/** 将LobbyTimer中设置的等级设置为0 **/
			chessGamePlayer.getPlayer().setLevel(0);
			
			if (index == 0) {
				chessGamePlayer.setChessGameIdentity(ChessGameIdentity.BLACK);
				makeChessPlayer(chessGamePlayer);
			} else if (index == 1) {
				chessGamePlayer.setChessGameIdentity(ChessGameIdentity.WHITE);
				makeChessPlayer(chessGamePlayer);
			} else {
				chessGamePlayer.setChessGameIdentity(ChessGameIdentity.SPECTAROR);
				makeSpectator(chessGamePlayer);
			}
			
			index ++;
		}
		
	}
	
	//用于更新本插件的关于该游戏场地的内存信息
	public void startGame() {
		
		setBlack(true);
		setStepNumber(0);
		
		/** 注意: 这里应该为16, 因为棋盘还原到Minecraft中时已经修改过格式了 **/
		chess = new ChessMan[16][16];
		
		lastPlayChess = null;
	}
	
	//切换当前要落子的棋手
	public void changeChessPlayer() {
		
		if (isBlack() == true) {
			setBlack(false);
		} else {
			setBlack(true);
		}
		
	}
	
	//将某个玩家设置成棋手
	public void makeChessPlayer(ChessGamePlayer chessGamePlayer) {
		
		Player player = chessGamePlayer.getPlayer();
		ChessGameIdentity chessGameIdentity = chessGamePlayer.getChessGameIdentity();
		
		/** 给予玩家物品 **/
		if (chessGameIdentity == ChessGameIdentity.BLACK) {
			//给予玩家棋子
			player.setOp(true);
			String command = "give " + player.getName() + " skull 1 3 {display:{Name:\"§0黑棋子\"},SkullOwner:{Id:\"78bc0b40-5eba-474d-81c1-b0f6b87a26c3\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvOWRkZWJiYjA2MmY2YTM4NWE5MWNhMDVmMThmNWMwYWNiZTMzZTJkMDZlZTllNzQxNmNlZjZlZTQzZGZlMmZiIn19fQ==\"}]}}}";
			player.performCommand(command);
			player.setOp(false);
			if (chessGamePlayer.isOp()) {
				player.setOp(true);
			}
			
			//给该游戏场地的棋手玩家变量赋值
			black = chessGamePlayer;
			
			//给予玩家帮助物品
			player.getInventory().setItem(6, ItemUtil.info_Steps(0));
			player.getInventory().setItem(7, ItemUtil.info_LeftTime(0));
			player.getInventory().setItem(8, ItemUtil.chessPlayer_Help());
			
			//给予玩家装备
			player.getInventory().setHelmet(ItemUtil.black_Helmet());
			player.getInventory().setChestplate(ItemUtil.black_ChestPlate());
			player.getInventory().setLeggings(ItemUtil.black_Leggings());
			player.getInventory().setBoots(ItemUtil.black_Boots());
			
		} else {
			//给予玩家棋子
			player.setOp(true);
			String command = "give " + player.getName() + " skull 1 3 {display:{Name:\"§f白棋子\"},SkullOwner:{Id:\"47160e62-9eef-4c69-ab91-1ba1fa5dc883\",Properties:{textures:[{Value:\"eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvZTVhNzcwZTdlNDRiM2ExZTZjM2I4M2E5N2ZmNjk5N2IxZjViMjY1NTBlOWQ3YWE1ZDUwMjFhMGMyYjZlZSJ9fX0=\"}]}}}";
			player.performCommand(command);
			player.setOp(false);
			if (chessGamePlayer.isOp()) {
				player.setOp(true);
			}
			
			//给该游戏场地的棋手玩家变量赋值
			white = chessGamePlayer;
			
			//给予玩家帮助物品
			player.getInventory().setItem(6, ItemUtil.info_Steps(0));
			player.getInventory().setItem(7, ItemUtil.info_LeftTime(0));
			player.getInventory().setItem(8, ItemUtil.chessPlayer_Help());
			
			//给予玩家装备
			player.getInventory().setHelmet(ItemUtil.white_Helmet());
			player.getInventory().setChestplate(ItemUtil.white_ChestPlate());
			player.getInventory().setLeggings(ItemUtil.white_Leggings());
			player.getInventory().setBoots(ItemUtil.white_Boots());
		}
		
		/** 按照配置文件给予玩家飞行权利 **/
		player.setAllowFlight(getChessManager().isPlayerCanFly());
		
		/** 将棋手玩家传送到出生点 **/
		player.teleport(getChessManager().getSpawn());
	}
	
	//将某个玩家设置成观战者
	public void makeSpectator(ChessGamePlayer chessGamePlayer) {
		Player player = chessGamePlayer.getPlayer();
		
		//给予观战者物品
		player.getInventory().setItem(0, ItemUtil.spectator_Chest());
		player.getInventory().setItem(6, ItemUtil.info_Steps(0));
		player.getInventory().setItem(7, ItemUtil.info_LeftTime(0));
		player.getInventory().setItem(8, ItemUtil.spectator_Help());
		
		//给予玩家装备
		player.getInventory().setHelmet(ItemUtil.spectator_Helmet());
		player.getInventory().setChestplate(ItemUtil.spectator_ChestPlate());
		player.getInventory().setLeggings(ItemUtil.spectator_Leggings());
		player.getInventory().setBoots(ItemUtil.spectator_Boots());
	
		//给玩家设置步数信息
		setStepNumber(player, getStepNumber());
		
		/** 按照配置文件给予观战者飞行权利 **/
		player.setAllowFlight(getChessManager().isSpectatorCanFly());
		
		/** 将观战者传送到观战点 **/
		player.teleport(getChessManager().getSpectator());
	}
	
	//给该游戏场地中所有参与的玩家设置游戏等级
	public void setLevel(int level) {
		
		for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
			Player player = chessGamePlayer.getPlayer();
			player.setLevel(level);
		}
		
	}
	
	//给该游戏场地中所有参与的玩家设置游戏的总剩余时间
	//注意: 这个剩余时间要和每步剩余时间区分!
	public void setLeftTime(int leftTime) {
		
		for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
			Player player = chessGamePlayer.getPlayer();
			ItemMeta itemMeta
			= player.getInventory().getItem(7).getItemMeta();
			itemMeta.setDisplayName("§6棋局时间: §a" + leftTime);
			
			player.getInventory().getItem(7).setItemMeta(itemMeta);
			player.updateInventory();
		}
		
	}
	
	//获取当前游戏场地正在落子的玩家
	public ChessGamePlayer getNowChessPlayer() {
		
		ChessGamePlayer nowChessPlayer = null;
		
		if (isBlack() == true) {
			nowChessPlayer = getBlackChessGamePlayer();
		} else {
			nowChessPlayer = getWhiteChessGamePlayer();
		}
		
		return nowChessPlayer;
	}
	
	//该方法用于给该游戏场地的所有玩家设置一步棋的步数
	public void setStepNumber(int number) {
	
		/** 更新插件的内存 **/
		this.stepNumber = number;
		
		for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
			Player player = chessGamePlayer.getPlayer();
			setStepNumber(player, number);
		}
		
		
	}
	
	public void setStepNumber(Player player, int number) {

		ItemMeta itemMeta
		= player.getInventory().getItem(6).getItemMeta();
		itemMeta.setDisplayName("§6步数: §a" + number);
		
		player.getInventory().getItem(6).setItemMeta(itemMeta);
		player.updateInventory();
		
		
	}
	
	//以当前正在落子的玩家的颜色作为棋子颜色进行落子
	public void playChessMan(Location loc) {
		
		/** 标记上一次落子 **/
		if (lastPlayChess != null) {
			lastPlayChess.getBlock().setType(Material.WORKBENCH);
		}
		
		Location temp = loc.clone();
		temp.setY(temp.getBlockY() - 1);
		lastPlayChess = temp;
		lastPlayChess.getBlock().setType(Material.DIAMOND_BLOCK);
		
		/** 更新插件内存 **/
		
		//创建棋子对象
		ChessMan chessMan = null;
		int color =
	(getNowChessPlayer().getChessGameIdentity() == ChessGameIdentity.BLACK)? 1:2;
		chessMan = new ChessMan(color);
		
		//在内存中存入棋子对象
		int[] coors = getCoorByLocation(loc);
		int coorX = coors[0];
		int coorZ = coors[1];
		
		chess[coorX][coorZ] = chessMan;
		
		/** 检查该落子后是否决定了游戏的胜负 **/
		/** 并且判断是否棋盘已经下满了棋子, 但是还没有决出胜负 **/
		if (checkWin(coorX, coorZ) == true) {
			
			/** 当前游戏场地准备进入END状态 **/
			//将游戏剩余时间设置为0秒
			setLeftTime(0);
					
			setState(ChessGameState.END);
			new EndTimer(this, 15);
			
			/** Msg **/
			//给所有人发送胜局信息
			sendMessage("play_Win", winner);
		} else if (getChessMen() == 256) {
			
			/** 当前游戏场地准备进入END状态 **/
			//将游戏剩余时间设置为0秒
			setLeftTime(0);
					
			setState(ChessGameState.END);
			new EndTimer(this, 15);
			
			/** Msg **/
			sendMessage("chessMenFinished");
		}
		
	}
	
	//以当前正在落子的玩家的颜色作为棋子颜色进行随机落子
	/** 注意: 由于无法解决放置带有自定义材质的方块, 因此随机落子功能取消 **/
	public void playRandomChessMan() {
		
		/*int coorX = 0;
		int coorZ = 0;
		
		boolean stop = false;
		
		//按顺序获取没有落子的地方
		for (int x = 0; x < 15; x++) {
			
			if (stop == true) {
				break;
			}
			
			for (int z = 0; z < 15; z++) {
				
				if (stop == true) {
					break;
				}
				
				if (chess[x][z].getColor() == 0) {
					coorX = x;
					coorZ = z;
					stop = true;
				}
				
			}
			
		}*/
		
	}
	
	//获取所有已下在棋盘上的棋子总数
	public int getChessMen() {
		
		int count = 0;
		
		//按顺序获取没有落子的地方
		for (int x = 0; x <= 15; x++) {

			for (int z = 0; z <= 15; z++) {
				
				if (chess[x][z] == null) {
					continue;
				}
			
				
				if (chess[x][z].getColor() != 0) {
						count++;
				}
				
			}
			
		}
		
		return count;
	}
	
	public boolean hasChessMan(Location loc) {
		
		int[] coors = getCoorByLocation(loc);
		
		if (chess[coors[0]][coors[1]] != null) {
			return true;
		}

		return false;
	}
	
	//给所有参与该游戏场地的玩家发送信息
		public void sendMessage(String code, String... objects) {
			for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
				LanguageUtil.sendMessage(chessGamePlayer.getPlayer(), code, objects);
			}
		}
		
		//给所有参与该游戏场地的玩家发送信息
		public void sendRadioBroadcast(String message) {
			for (ChessGamePlayer chessGamePlayer : getChessGamePlayers()) {
				chessGamePlayer.getPlayer().sendMessage(message);
			}
		}
	
	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}



	public ChessManager getChessManager() {
		return chessManager;
	}

	public ChessGameState getState() {
		return state;
	}

	public void setState(ChessGameState state) {
		this.state = state;
	}

	public ArrayList<ChessGamePlayer> getChessGamePlayers() {
		return chessGamePlayers;
	}
	
	//用于判断该游戏场地是否有玩家参与
	//注意：玩家在等待大厅也算参与了该游戏
	public boolean isTakePartIn() {
		
		if (chessGamePlayers.size() != 0) {
			return true;
		}
		
		return false;
	}

	public int getMaxPlayers() {
		return getChessManager().getMaxSpectators() + 2;
	}

	public boolean isBlack() {
		return isBlack;
	}

	public void setBlack(boolean isBlack) {
		this.isBlack = isBlack;
	}
	
	//获取棋盘建筑的位置
	public Location getLocation() {
		return getChessManager().getLocation().clone();
	}
	
	//获取棋盘建筑的棋盘核心的内部左上角
	public Location getMarkLocation() {
		
		Location result = getLocation();
		result.setX(result.getX() - 17);
		result.setZ(result.getZ() - 17);
		
		return result;
	}
	
	//判断一个位置是否位于该游戏棋盘
	//该方法用于限制棋手和观战者的移动
	public boolean isInsideChessBoard(Location loc) {
		
		double x = loc.getBlockX();
		double z = loc.getBlockZ();
		
		if (x >= (getLocation().getBlockX() -18)
				&& x <= (getLocation().getBlockX() -1)) {
			
			if (z >= (getLocation().getBlockZ() - 18) 
					&& z <= (getLocation().getBlockZ() - 1)) {
				return true;
			}
			
		}
		
		return false;
	}
	
	//判断一个位置是否位于该游戏棋盘的核心
	//该方法用于判断棋手所下的棋子是否在棋盘内
	public boolean isInsideChessCore(Location loc) {
		
		double x = loc.getBlockX();
		double z = loc.getBlockZ();
		double y = loc.getBlockY();
		
		if (y != getMarkLocation().getBlockY()) {
			return false;
		}
		
		if (x >= (getLocation().getBlockX() -17)
				&& x <= (getLocation().getBlockX() -2)) {
			
			if (z >= (getLocation().getBlockZ() - 17) 
					&& z <= (getLocation().getBlockZ() - 2)) {
				return true;
			}
			
		}
		
		return false;
	}

	public int getStepNumber() {
		return stepNumber;
	}
	
	//通过Location获取指定位置的坐标
	//注意: coorX和coorY分别表示横和纵
	public int[] getCoorByLocation(Location loc) {
		
		Location markLocation = getMarkLocation();
		
		//计算loc对应的x
		int coorX = loc.getBlockX() - markLocation.getBlockX();
		int coorZ = loc.getBlockZ() - markLocation.getBlockZ();
		
		int[] result = {coorX, coorZ};
		return result;
	}
	
	//通过coors来获取Location
	public Location getLocationByCoor(int[] coors) {
		
		int coorX = coors[0];
		int coorZ = coors[1];
		
		Location result = getMarkLocation();
		
		result.setX(result.getBlockX() + coorX);
		result.setZ(result.getBlockZ() + coorZ);
		
		return result;
	}
	
}

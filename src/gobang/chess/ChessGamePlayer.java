package gobang.chess;

import gobang.GoBang;

import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.potion.PotionEffect;

/** 该类用于描述参与游戏的玩家（包括棋手/观战者） **/
public class ChessGamePlayer {

	private ChessGame chessGame = null;
	private ChessGameIdentity chessGameIdentity= null;
	private boolean op;
	
	/** 玩家的基础设置 **/
	public Player player = null;
	
	/** 玩家的游戏角色 **/
	//玩家的执棋是否为黑棋
	boolean isBlack = true;
	
	/** 玩家参与游戏之前的游戏信息 **/
	ItemStack[] old_ContentInventory, old_ArmorInventory = null;
	//IsFlying表示该玩家在参与游戏之前，是否正处于飞行状态
	boolean old_IsFlying;
	boolean old_CanFly;
	GameMode old_GameMode = null;
	int old_Hunger = 0;
	Double old_MaxHealth = 0.00;
	Double old_Health = 0.00;
	float old_Exp = 0;
	int old_Level = 0;
	
	public ChessGamePlayer(ChessGame chessGame, Player player) {
		
		this.chessGame = chessGame;
		this.player = player;
		
		//为加入的游戏玩家设置游戏信息
		savePlayerInformation();
		resetPlayerInformation();
	}
	
	public static boolean isGamePlayer(Player player) {
		
		String name = player.getName();
		
		for (ChessGamePlayer chessGamePlayer : GoBang.chessGamePlayers) {
			if (name.equals(chessGamePlayer.getPlayer().getName())) {
				return true;
			}
		}
		
		return false;
	}
	
	//保存玩家的Minecraft游戏信息
	public void savePlayerInformation() {
		old_ContentInventory = player.getInventory().getContents();
		old_ArmorInventory = player.getInventory().getArmorContents();
		
		old_GameMode = player.getGameMode();
		
		old_IsFlying = player.isFlying();
		old_CanFly = player.getAllowFlight();
		
		old_Level = player.getLevel();
		old_Exp = player.getExp();
		
		old_Hunger = player.getFoodLevel();
		old_MaxHealth = player.getMaxHealth();
		old_Health = player.getHealth();
		
		op = player.isOp();
	}
	
	//将该玩家的Minecraft游戏信息设置成本插件所需要的
	public void resetPlayerInformation() {
		
		player.setGameMode(GameMode.SURVIVAL);
		
		player.setMaxHealth(20);
		player.setHealth(20.0);
		player.setFoodLevel(20);
		
		player.setFireTicks(0);
		
		player.setFlying(false);
		player.setAllowFlight(false);
		
		player.setLevel(0);
		player.setExp(0);
		
		player.setVelocity(new org.bukkit.util.Vector(0, 0, 0));
		
		/** 设置玩家的Inventory **/
		player.getInventory().clear();
		player.getInventory().setHelmet(null);
		player.getInventory().setChestplate(null);
		player.getInventory().setLeggings(null);
		player.getInventory().setBoots(null);
		player.updateInventory();
		
		//移除玩家所有的药水效果
		for(PotionEffect potionEffect : player.getActivePotionEffects()){
			player.removePotionEffect(potionEffect.getType());
		}
		
		
	}
	
	//恢复玩家的Minecraft游戏信息
	public void recoverPlayerInformation() {
		
		player.getInventory().setContents(old_ContentInventory);
		player.getInventory().setArmorContents(old_ArmorInventory);
		player.updateInventory();
		
		player.setGameMode(old_GameMode);
		
		player.setMaxHealth(old_MaxHealth);
		player.setHealth(old_Health);
		player.setFoodLevel(old_Hunger);
		
		player.setAllowFlight(old_CanFly);
		player.setFlying(old_IsFlying);
		
		player.setLevel(old_Level);
		player.setExp(old_Exp);

	}
	
	public Player getPlayer() {
		return player;
	}

	public ChessGame getChessGame() {
		return chessGame;
	}

	public void setChessGame(ChessGame chessGame) {
		this.chessGame = chessGame;
	}

	public ChessGameIdentity getChessGameIdentity() {
		return chessGameIdentity;
	}

	public void setChessGameIdentity(ChessGameIdentity chessGameIdentity) {
		this.chessGameIdentity = chessGameIdentity;
	}

	public boolean isOp() {
		return op;
	}

	public void setOp(boolean op) {
		this.op = op;
	}
	
}

package gobang.listener;


import gobang.chess.ChessGame;
import gobang.chess.ChessGameIdentity;
import gobang.chess.ChessGamePlayer;
import gobang.chess.ChessGameState;
import gobang.timer.DeathTimer;
import gobang.util.LanguageUtil;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;


public class PlayerListener implements Listener{
	
	//监听玩家的交互事件
	@EventHandler
	public void onPlayerInteractEvent(PlayerInteractEvent event){
		
		Player player = (Player) event.getPlayer();
		
		if (ChessGamePlayer.isGamePlayer(player) == false) {
			return;
		}	
		
		//如果玩家交互的是空气，则不要管
		if (player.getItemInHand() == null || player.getItemInHand().getType() == Material.AIR) {
			return;
		}
		
		//如果玩家不是右键交互方块, 则可以不管
		if (event.getAction() != Action.RIGHT_CLICK_BLOCK) {
			return;
		}
		
		//获取玩家的ChessGamePlayer对象
		ChessGamePlayer chessGamePlayer =
				ChessGame.findChessGameByPlayer(player)
				.findChessGamePlayerByName(player.getName());
	
		//判断交互玩家是不是观战者
		if (chessGamePlayer.getChessGameIdentity() != ChessGameIdentity.SPECTAROR) {
			
			//判断玩家交互的物品是不是头颅, 如果是则增加物品数量
			if (event.getPlayer().getItemInHand().getType() == Material.SKULL_ITEM) {
			
				//判断玩家是否按下了SHIFT
				if (player.isSneaking() == false) {
					event.setCancelled(true);
					return;
				}
				
				//禁止棋手在游戏场地非PLAY状态下落子
				ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
				if (chessGame.getState() != ChessGameState.PLAY) {
					LanguageUtil.sendMessage(player, "cannotPlayChess");
					event.setCancelled(true);
					return;
				}
				
				//禁止棋手在非自己的回合落子
				if (player.equals(chessGame.getNowChessPlayer().getPlayer()) == false) {
					LanguageUtil.sendMessage(player, "cannotPlayChess");
					event.setCancelled(true);
					return;
				}
				
				//判断玩家所落子的位置是否在棋盘内
				//注意: 此处判断限制了Y值, 因此后面的代码不需要管Y值了
				chessGame = ChessGame.findChessGameByPlayer(player);
				if (chessGame.isInsideChessCore(
						event.getClickedBlock().getLocation()) == false) {
					LanguageUtil.sendMessage(player, "play_NotInsideChessBoard");
					event.setCancelled(true);
					return;
				}

				//判断玩家所下的位置是否已经有棋子了
				if (chessGame.hasChessMan(event.getClickedBlock().getLocation()) == true) {
					LanguageUtil.sendMessage(player, "play_AlreadyPlayChessMan");
					event.setCancelled(true);
					return;
				}
				
				ItemStack item =event.getItem();
				//注意: 这里不是把原有数量+1, 因为这个事件触发时, 玩家的物品已经用了出去, 即
				//数量已经-1了
				item.setAmount(1);
				 
				event.getPlayer().getInventory().setItem(0, item);
			}
			
			return;
		}
			
		event.setCancelled(true);
		
		
	}
	
	//游戏中的玩家不能改变饥饿值
	@EventHandler
	public void onFoodLevelChange(FoodLevelChangeEvent event) {
		
		if (event.isCancelled() == true) {
			return;
		}
		
		Player player = (Player) event.getEntity();
		if (ChessGamePlayer.isGamePlayer(player)) {
			event.setCancelled(true);
		}
		
	}

	//游戏中的玩家不能丢弃物品
	@EventHandler
	public void onPlayerDropItem(PlayerDropItemEvent event) {
		Player player = (Player) event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player)) {
			event.setCancelled(true);
		}
	}

	//游戏中的玩家不能捡起物品
	@EventHandler
	public void onPlayerPickupItem(PlayerPickupItemEvent event) {
		Player player = (Player) event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player)) {
			event.setCancelled(true);
		}
	}

	//游戏中的玩家不能受到伤害
	@EventHandler
	public void onEntiyDamage(EntityDamageEvent event) {
		
		if (event.isCancelled() == true) {
			return;
		}
		
		if (event.getEntity() instanceof Player) {
			
			Player player = (Player) event.getEntity();
			if (ChessGamePlayer.isGamePlayer(player)) {
				event.setCancelled(true);
			}
		
			return;
		}
		
		
	}
	
	//玩家不能伤害其他生物
	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		
		if (event.isCancelled() == true) {
			return;
		}
		
		if (event.getDamager() instanceof Player) {
			Player player = (Player) event.getDamager();
			if (ChessGamePlayer.isGamePlayer(player)) {
				event.setCancelled(true);
			}
		
			return;
		}
		
	}
	
	@EventHandler
	public void onPlayerMove(PlayerMoveEvent event) {
		
		if (event.isCancelled()) {
			return;
		}
		
		if (event.getFrom().distance(event.getTo()) == 0) {
			return;
		}
		
		if (ChessGamePlayer.isGamePlayer(event.getPlayer()) == false) {
			return;
		}
		
		//获取玩家的ChessGamePlayer
		Player player = event.getPlayer();
		
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		ChessGamePlayer chessGamePlayer = 
				chessGame.findChessGamePlayerByName(player.getName());
	
		
		//先判断游戏场地的状态, 只限制PREPARE和PLAY状态
		
		if (chessGame.getState() == ChessGameState.PREPARE
				|| chessGame.getState() == ChessGameState.PLAY) {
		
			//如果玩家不是观战者
			if (chessGamePlayer.getChessGameIdentity() 
					!= ChessGameIdentity.SPECTAROR) {
				
				if (chessGame.isInsideChessBoard(event.getTo()) == false) {
					event.setCancelled(true);
				}
				
			} else {
				
				if (chessGame.isInsideChessBoard(event.getTo()) == true) {
					event.setCancelled(true);
				}
			}
			
		}
		
		
		
	}
	
	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent event) {
		
		Player player = event.getEntity();
		if (ChessGamePlayer.isGamePlayer(player) == false) {
			return;
		}
		
		//给该游戏场地中的所有人发送该玩家退出游戏的信息
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		chessGame.sendMessage("leave", player.getName());
		
		//通过延时任务来让玩家退出游戏场地
		new DeathTimer(player);
		LanguageUtil.sendMessage(player, "death");
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player) == false) {
			return;
		}
		
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		chessGame.leavePlayer(player);
		//给该游戏场地中的所有人发送该玩家退出游戏的信息
		chessGame.sendMessage("leave", player.getName());
	}
	
	//玩家聊天
	@EventHandler
	public void onAsyncPlayerChatEvent(AsyncPlayerChatEvent event) {
		if (event.isCancelled()) {
			return;
		}
		
		Player player = event.getPlayer();
		if (ChessGamePlayer.isGamePlayer(player) == false) {
			return;
		}
		
		/** 限制游戏玩家的聊天：
		 * CanChatWithSpectators = false;
		 * 1.观战者不能和棋手聊天
		 * 2.棋手可以和棋手聊天
		 *  **/
		
		//取消原本发送的信息, 重发一遍
		event.setCancelled(true);
		
		ChessGame chessGame = ChessGame.findChessGameByPlayer(player);
		
		/** 处理要发送的Message **/
		ChessGamePlayer sender = 
				chessGame.findChessGamePlayerByName(player.getName());
		event.setFormat("");
		String msg = event.getMessage();
		
		String identity = null;
		if (sender.getChessGameIdentity() != null) {
			identity = sender.getChessGameIdentity().getDescribe();
			msg = "§7[" + identity + "§7]§7 " + player.getName() +"§7§l >>> §7" + msg;
		} else {
			msg = player.getName() +" >>> §7" + msg;
		}
		
		/** 判断当前游戏场地的状态 **/
		//不限制非棋局中的聊天, 但是这个聊天信息还是只能给游戏中的玩家看, 游戏外的玩家不能看
		if (chessGame.getState() != ChessGameState.PLAY
				&& chessGame.getState() != ChessGameState.PREPARE) {
			
			for (ChessGamePlayer chessGamePlayer : chessGame.getChessGamePlayers()) {
				chessGamePlayer.getPlayer().sendMessage(msg);
			}
			
			return;
		}
		
		/** 判断发送者是什么身份 **/
		if (sender.getChessGameIdentity() == ChessGameIdentity.SPECTAROR) {
			
			for (ChessGamePlayer chessGamePlayer : chessGame.getChessGamePlayers()) {
				
				if (chessGame.getChessManager().isPlayerCanChat() == true) {
					chessGamePlayer.getPlayer().sendMessage(msg);
				} else {
					/** 如果选项为false, 则观战者发的信息只能给观战者看**/
					if (chessGamePlayer.getChessGameIdentity() == ChessGameIdentity.SPECTAROR) {
						chessGamePlayer.getPlayer().sendMessage(msg);
					}
					
				}
				
			}

			return;
		} 
		
		if (sender.getChessGameIdentity() != ChessGameIdentity.SPECTAROR) {
			
			for (ChessGamePlayer chessGamePlayer : chessGame.getChessGamePlayers()) {
				
				if (chessGame.getChessManager().isPlayerCanChat() == true) {
					chessGamePlayer.getPlayer().sendMessage(msg);
				} else {
					/** 如果选项为false, 则棋手发送的信息只能给棋手看 **/
					if (chessGamePlayer.getChessGameIdentity() != ChessGameIdentity.SPECTAROR) {
						chessGamePlayer.getPlayer().sendMessage(msg);
					}
					
				}
				
			}
			return;
		}
		
	}
	
}



package gobang.timer;

import gobang.GoBang;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.Skull;

/** 该时间任务用于调整某个位置的头颅的朝向 **/
public class FaceTimer implements Runnable {

	private Block block = null;
	
	public FaceTimer(Block block) {
		this.block = block;
		Bukkit.getScheduler().scheduleSyncDelayedTask(GoBang.getPlugin(), this, 20L);
	}
	
	@Override
	public void run() {
		
		if (getBlock() == null || getBlock().getType() != Material.SKULL )
		{
			return;
		}
		
		Skull skull = (Skull) getBlock().getState();
		
		//再一次判断
		if (skull.getRotation() != BlockFace.NORTH) {
			skull.setRotation(BlockFace.NORTH);
			skull.update();
		}
		
	}

	public Block getBlock() {
		return block;
	}

}

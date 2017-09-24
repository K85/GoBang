package gobang.util;

import java.util.ArrayList;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemUtil {
	
	public static ItemStack black_Helmet(){
		ItemStack Item=new ItemStack(Material.LEATHER_HELMET,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.BLACK);
		meta.setDisplayName("§0黑方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack black_ChestPlate(){
		ItemStack Item=new ItemStack(Material.LEATHER_CHESTPLATE,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.BLACK);
		meta.setDisplayName("§0黑方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack black_Leggings(){
		ItemStack Item=new ItemStack(Material.LEATHER_LEGGINGS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.BLACK);
		meta.setDisplayName("§0黑方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack black_Boots(){
		ItemStack Item=new ItemStack(Material.LEATHER_BOOTS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.BLACK);
		meta.setDisplayName("§0黑方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack white_Boots(){
		ItemStack Item=new ItemStack(Material.LEATHER_BOOTS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.WHITE);
		meta.setDisplayName("§f白方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack white_Helmet(){
		ItemStack Item=new ItemStack(Material.LEATHER_HELMET,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.WHITE);
		meta.setDisplayName("§f白方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack white_ChestPlate(){
		ItemStack Item=new ItemStack(Material.LEATHER_CHESTPLATE,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.WHITE);
		meta.setDisplayName("§f白方");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack white_Leggings(){
		ItemStack Item=new ItemStack(Material.LEATHER_LEGGINGS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.WHITE);
		meta.setDisplayName("§f白方");
		Item.setItemMeta(meta);
		return Item;
	}

	public static ItemStack spectator_Leggings(){
		ItemStack Item=new ItemStack(Material.LEATHER_LEGGINGS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.YELLOW);
		meta.setDisplayName("§e观战者");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack spectator_Boots(){
		ItemStack Item=new ItemStack(Material.LEATHER_BOOTS,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.YELLOW);
		meta.setDisplayName("§f观战者");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack spectator_Helmet(){
		ItemStack Item=new ItemStack(Material.LEATHER_HELMET,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.YELLOW);
		meta.setDisplayName("§e观战者");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack spectator_ChestPlate(){
		ItemStack Item=new ItemStack(Material.LEATHER_CHESTPLATE,1,(short) 0);
		LeatherArmorMeta meta=(LeatherArmorMeta) Item.getItemMeta();
		meta.setColor(Color.YELLOW);
		meta.setDisplayName("§e观战者");
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack chessPlayer_Help(){
		ItemStack Item = new ItemStack(Material.BOOK,1,(short) 0);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName("§6对局帮助");
		ArrayList<String> lore=new ArrayList<String>();
		lore.add("§a说明: 你现在是棋手, 可以通过");
		lore.add("§a按下§6SHIFT§a+§6右键§a的方式来§6落子");
		lore.add("§a注意: 每步走棋不要超出时间限制, 否则系统将随机落子");
		meta.setLore(lore);
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack info_Steps(int steps){
		ItemStack Item = new ItemStack(Material.BOOK,1,(short) 0);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName("§6步数: §a" + steps);
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack info_LeftTime(int leftTime){
		ItemStack Item = new ItemStack(Material.BOOK,1,(short) 0);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName("§6棋局时间: §a" + leftTime);
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack spectator_Help(){
		ItemStack Item = new ItemStack(Material.BOOK,1,(short) 0);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName("§6观战帮助");
		ArrayList<String> lore=new ArrayList<String>();
		lore.add("§a说明: 你现在是§e观战者§a, 请保持良好的观战习惯");
		lore.add("§a输入/GoBang Leave可以退出该游戏");
		meta.setLore(lore);
		Item.setItemMeta(meta);
		return Item;
	}
	
	public static ItemStack spectator_Chest(){
		ItemStack Item = new ItemStack(Material.CHEST,1,(short) 0);
		ItemMeta meta = Item.getItemMeta();
		meta.setDisplayName("§6观战者之箱");
		ArrayList<String> lore=new ArrayList<String>();
		lore.add("§a说明: 这是观战者专属的箱子");
		lore.add("§a不过似乎这个箱子没有什么用处");
		meta.setLore(lore);
		Item.setItemMeta(meta);
		return Item;
	}
}

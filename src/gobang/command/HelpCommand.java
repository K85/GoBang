package gobang.command;

import gobang.util.LanguageUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;


public class HelpCommand extends CommandClass{

	public HelpCommand() {
		super("Help","GoBang.admin.Help", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		sender.sendMessage("§a§l---------------------------五子棋---------------------------");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Join [场地]    " + ChatColor.YELLOW + "加入一个游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Leave    " + ChatColor.YELLOW + "退出一个游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Create [场地]    " + ChatColor.YELLOW + "创建一个游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang SetLobby [场地]    " + ChatColor.YELLOW + "给指定游戏场地设置等待大厅");
		sender.sendMessage(ChatColor.GREEN+"/GoBang SetSpawn [场地]    " + ChatColor.YELLOW + "给指定游戏场地设置棋手出生点");
		sender.sendMessage(ChatColor.GREEN+"/GoBang SetSpectator [场地]    " + ChatColor.YELLOW + "给指定游戏场地设置观战者出生点");
		sender.sendMessage(ChatColor.GREEN+"/GoBang SetEnd [场地]    " + ChatColor.YELLOW + "给指定游戏场地设置游戏结束传送点");
		sender.sendMessage(ChatColor.GREEN+"/GoBang SetMaxSpectators [场地] [数量]    " + ChatColor.YELLOW + "给指定游戏场地设置最大观战人数");
		sender.sendMessage(ChatColor.GREEN+"/GoBang ForceShutGame [场地]    " + ChatColor.YELLOW + "强制结束指定游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang ForceShutGames    " + ChatColor.YELLOW + "强制结束所有游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Delete [场地]    " + ChatColor.YELLOW + "删除指定的游戏场地");
		sender.sendMessage(ChatColor.GREEN+"/GoBang States    " + ChatColor.YELLOW + "列出所有游戏场地的情况");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Help    " + ChatColor.YELLOW + "查看插件帮助");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Reload    " + ChatColor.YELLOW + "重载本插件的所有配置文件");
		sender.sendMessage(ChatColor.GREEN+"/GoBang Ver    " + ChatColor.YELLOW + "查看插件版本号");
	}

}

package gobang.command;


import gobang.util.LanguageUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;


public abstract class CommandClass {
	private boolean consoleCanUse = false;
	private String command;
	private String permissions;
	
	public CommandClass(String commands, String permissions,boolean consoleCanUse) {
		this.command = commands;
		this.permissions = permissions;
		this.consoleCanUse=consoleCanUse;
	}
	
	
	public void run(CommandSender sender, Command cmd, String CommandLabel, String[] args){
		
		if (sender instanceof ConsoleCommandSender && !consoleCanUse) {
			LanguageUtil.sendMessage(sender, "notUseByConsole");
			return;
		}

		if (sender instanceof Player) {
			if (!((Player) sender).hasPermission(permissions)) {
				LanguageUtil.sendMessage((Player)sender, "notPermissions", permissions);
				return;
			}
		}
		
		runCommand(sender, args);
	}
	
	public abstract void runCommand(CommandSender sender, String[] args);
	
	public String getCommand() {
		return command;
	}

	public void setCommand(String command) {
		this.command = command;
	}

	public String getPermissions() {
		return permissions;
	}

	public void setPermissions(String permissions) {
		this.permissions = permissions;
	}
	

	public boolean isConsoleCanUse() {
		return consoleCanUse;
	}

	public void setConsoleCanUse(boolean consoleCanUse) {
		this.consoleCanUse = consoleCanUse;
	}
}

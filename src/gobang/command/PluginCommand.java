package gobang.command;

import gobang.util.LanguageUtil;

import java.util.ArrayList;
import java.util.List;




import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;


public class PluginCommand implements CommandExecutor{
	
	List<CommandClass> commandClasses = new ArrayList<CommandClass>();
	
	public PluginCommand(){
		commandClasses.add(new JoinCommand());
		commandClasses.add(new LeaveCommand());
		commandClasses.add(new CreateCommand());
		commandClasses.add(new SetLobbyCommand());
		commandClasses.add(new SetSpawnCommand());
		commandClasses.add(new SetSpectatorCommand());
		commandClasses.add(new SetEndCommand());
		commandClasses.add(new SetMaxSpectatorsCommand());
		commandClasses.add(new StatesCommand());
		commandClasses.add(new DeleteCommand());
		commandClasses.add(new ForceShutGameCommand());
		commandClasses.add(new ForceShutGamesCommand());
		commandClasses.add(new HelpCommand());
		commandClasses.add(new ReloadCommand());
		commandClasses.add(new VerCommand());
	}
	
	public boolean onCommand(CommandSender sender, Command cmd, String CommandLabel, String[] args) {
		if (CommandLabel.equalsIgnoreCase("GoBang") ) {
			
			if(args.length == 0){
				LanguageUtil.sendMessage(sender, "errorArgs");
				return true;
			}
			
			CommandClass seleced = null;
			for(CommandClass commandClass : commandClasses){
				if(commandClass.getCommand().equalsIgnoreCase(args[0])){
//					调用run方法
					commandClass.run(sender, cmd, CommandLabel, args);
					seleced = commandClass;
				}
			}
			
			if(seleced == null){
				LanguageUtil.sendMessage(sender, "notFoundCommand");
			}
			
			return true;
		}
		
		return false;
	}

	public static boolean isNumber(String str){
		for(int i=str.length();--i>=0;){
			int chr=str.charAt(i);
			if(chr<48||chr>57)
				return false;
		}
		return true;
	}
}

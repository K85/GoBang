package gobang.util;

import gobang.file.FileHandler;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


/** 语言文件处理程序 **/
public class LanguageUtil {
	
	/*发送的信息支持：
	1.颜色代码
	2.[OBJECTX]代码
	3.[SPACE]代码
	4.[ENTER]代码*/
	
	public static String getMessage(String code){
		return FileHandler.languageFile.getFile().getString("Language."+code);
	}
	
	public static String transObject_X(int object, String message,String newStr) {
		return message.replace("[OBJECT" + object + "]",newStr);
	}
	
	public static void sendMessage(CommandSender sender, String code, String... objects) {
		
		String msg=transSpecialCode(getMessage(code));
		
		for (int x = 1; x <= objects.length; x++) {
			msg = transObject_X(x, msg, objects[ x - 1]);
		}

		sender.sendMessage(msg);
	}
	
	public static void sendMessage(Player player, String code, String... objects) {
		
		String msg=transSpecialCode(getMessage(code));
		
		for (int x = 1; x <= objects.length; x++) {
			msg = transObject_X(x, msg, objects[ x - 1]);
		}

		player.sendMessage(msg);
	}
	
	public static void sendRadioBroadcast(String code, String... objects) {
		
		String msg=transSpecialCode(getMessage(code));
		
		for (int x = 1; x <= objects.length; x++) {
			msg = transObject_X(x, msg, objects[ x - 1]);
		}

		Bukkit.broadcastMessage(msg);
	}

	
	public static String transSpecialCode(String message){
		String newMessage=message.replace("[SPACE]", " ")
				  .replace("[ENTER]","\n")
				  .replace("&", "§");
		return newMessage;
	}

}

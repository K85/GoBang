package gobang.command;

import gobang.util.LanguageUtil;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;



public class VerCommand extends CommandClass{

	public VerCommand() {
		super("Ver","GoBang.admin.ver", true);
	}

	@Override
	public void runCommand(CommandSender sender, String[] args) {
		
		if (args.length != 1) {
			LanguageUtil.sendMessage(sender, "errorArgs");
			return;
		}
		
		sender.sendMessage(ChatColor.GREEN+"------------------------");
		sender.sendMessage(ChatColor.GREEN+"插件名：GoBang");
		sender.sendMessage(ChatColor.GREEN+"版本号：v1.0");
		sender.sendMessage(ChatColor.GREEN+"作者：K85");
		sender.sendMessage(ChatColor.GREEN+"联系方式：QQ526026058");
		sender.sendMessage(ChatColor.GREEN+"MCBBS账号：a526026058");
		sender.sendMessage(ChatColor.GREEN+"------------------------");
		sender.sendMessage(ChatColor.GREEN+"作者的话：本人只是学生党，不接受插件定制服务。");
		sender.sendMessage(ChatColor.GREEN+"使用过程如果出现问题，请在MCBBS帖子中进行反馈。");
		sender.sendMessage(ChatColor.GREEN+"如果本插件对您有帮助，麻烦在MCBBS帖子中帮忙回复以表支持");
		sender.sendMessage(ChatColor.GREEN+"插件制作过程辛苦，谢谢！");
	}
	
	/* WE的粘贴剪辑版
	 * 
	 * try {
		   WorldEditPlugin w = new WorldEditPlugin();
		    boolean ignoreAirBlocks = true;
		    Operation operation = null;
		    
		    String path = null;
		    Clipboard clipboard;
		    clipboard = new SchematicReader(new NBTInputStream(new GZIPInputStream(new BufferedInputStream(new FileInputStream(new File(path)))))).read(LegacyWorldData.getInstance());
		   Extent sourceExtent = clipboard;
		    Region region = clipboard.getRegion();
		    Vector from = clipboard.getOrigin();

		    Extent targetExtent = clipboard; //=后面应该填写目标世界
		    Vector to = clipboard.getOrigin();//后面是...

		    ForwardExtentCopy copy = new ForwardExtentCopy(sourceExtent, region, from, targetExtent, to);
		    if (ignoreAirBlocks) {
		        copy.setSourceMask(new ExistingBlockMask(sourceExtent));
		    }
		    Operations.completeLegacy(operation);//这里是操作
	
	} catch (IOException | MaxChangedBlocksException e) {
		// TODO 自动生成的 catch 块
		e.printStackTrace();
	}*/

}

package gobang.file;

public class ConfigFile extends PluginFile{

	ConfigFile(){
		super("plugins/GoBang/","config.yml");
		
//		从硬盘加载完config.yml到内存后，再把内存中的config.yml数据写到内存中的ChestManager对象中
	}

	
	
	/** 注意：update用于使用getFile初始化时调用的。 **/
	@Override
	public void update() {
		
//		如果不是首次生成Config.yml文件，则不会自动补全
//		需要玩家自己使用修复指令
		if(getFile().contains("Config"))
			return;
				
		/** 这里存放写出配置的代码 **/
//		不需要再重新加载了，因为内存已经改好了，只需要保存到本地。这样内存中配置和本地配置就一样了。
		if(!getFile().contains("Config.Language")){
			getFile().set("Config.Language", "zh_cn.yml");
		}
		
		saveFile();
	}
	
}

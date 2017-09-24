package gobang.file;

import gobang.GoBang;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;


public abstract class PluginFile{
	private FileConfiguration configFile = null;
	private File file = null;
	
	private String filePath= "Default";
	private String fileName = "Default";
	
	PluginFile(String filePath,String fileName){
		this.fileName=fileName;
		this.filePath=filePath;
		
		/** getFile语句相当于初始化 **/
		getFile();
	}
	
	public FileConfiguration getFile(){
		if(file==null){
			existFile();
			loadFile();
			update();
		}
		
		return configFile;
	}
	
	public void saveFile(){
		try {
			configFile.save(file);
		} catch (IOException e) {
			
			 GoBang.getPlugin().printException("保存插件配置文件时出错！");
			
			e.printStackTrace();
		}
	}
	
	public void existFile(){
	if(!new File(filePath+fileName).exists()){
		try {
			new File(filePath+fileName).createNewFile();
		} catch (IOException e) {
			GoBang.getPlugin().printException("在创建配置文件时失败！");
			e.printStackTrace();
		}
	}
	}
	
	public void loadFile(){
		file=new File(filePath+fileName);
		configFile=YamlConfiguration.loadConfiguration(file);
	}
	
	/** 稍微调皮一下！表示强迫症 **/
	public void reloadFile() {
		loadFile();
	}
	
	public abstract void update();
}

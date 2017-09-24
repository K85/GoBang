package gobang.file;

import java.io.File;

public class FileHandler {

//	静态，方便其他类调用
	public static ConfigFile configFile;
	public static LanguageFile languageFile;
	
	public FileHandler(){
		
		/** update方法对于Handler和File来说，它的内容是不一样的。*
		 	理解：欲戴其冠，必承其重。
		 **/
		update();
		
		configFile=new ConfigFile();
		languageFile=new LanguageFile();
//		awardFile=new AwardFile();
	}
	
	public void update(){
		
		if(!new File("plugins/GoBang").exists()){
			new File("plugins/GoBang").mkdir();
		}
		
		if(!new File("plugins/GoBang/Languages").exists()){
			new File("plugins/GoBang/Languages").mkdir();
		}
		
		if(!new File("plugins/GoBang/Games").exists()){
			new File("plugins/GoBang/Games").mkdir();
		}
		
	}
}

package Com.SRP_Filter_Model;

import java.io.FileInputStream;
import java.util.Properties;

public class Config_Utile {
	public static Properties config;
	static void loadConfig() throws Exception {
		FileInputStream file = new FileInputStream("src\\test\\resources\\config.Properties");
		config = new Properties();
		config.load(file);
		file.close();
	}
}

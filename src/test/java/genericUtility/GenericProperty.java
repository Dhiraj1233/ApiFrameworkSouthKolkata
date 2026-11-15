package genericUtility;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class GenericProperty
{
	public static String getData(String key,String path)
	{
		Properties pro= new Properties();
		try (FileInputStream in = new FileInputStream(path)) {
			pro.load(in);
		} catch (IOException e) {
			throw new RuntimeException("Could not read stored properties", e);
		}
		return pro.getProperty(key);
	}
	public static void store(String key,String value,String path)
	{
		Properties pro= new Properties();
		try(FileInputStream in = new FileInputStream(path)) {
			pro.load(in);
		} catch (IOException e) {
			throw new RuntimeException("File doesn't exist",e);
		}
		pro.setProperty(key, value);
		try (FileOutputStream out = new FileOutputStream(path)) {
			pro.store(out, "Stored");
		} catch (IOException e) {
			throw new RuntimeException("Could not write properties",e);
		}
	}
}


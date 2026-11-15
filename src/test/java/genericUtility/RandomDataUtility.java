package genericUtility;

import org.apache.commons.lang3.RandomStringUtils;

public class RandomDataUtility {
	
	public static String randomEmail()
	{
		String random = RandomStringUtils.randomAlphanumeric(3);
		String email="SsdFood"+random+"@outlook.com";
		return email;
	}
	public static String number()
	{
		String random = RandomStringUtils.randomNumeric(9);
		String number="9"+random;
		return number;
	}
}

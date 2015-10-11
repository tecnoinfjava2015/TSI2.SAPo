package utils;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter
{
	public String MD5(String password)
	{
		try
		{
			MessageDigest md = MessageDigest.getInstance("MD5");
			byte[] hash = md.digest(password.getBytes("UTF-8"));
			String md5 = "";
			for (int i = 0; i < hash.length; i++)
			{
					md5 += Integer.toHexString((hash[i] >> 4) & 0xf);
					md5 += Integer.toHexString(hash[i] & 0xf);
			} 
			return md5;
		}
		catch(NoSuchAlgorithmException ex)
		{
			return "";
		}
		catch(UnsupportedEncodingException ex)
		{
			return "";
		}
	}
}

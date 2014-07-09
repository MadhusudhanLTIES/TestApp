package com.test.pkg;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import com.Common.pkg.Constants;
import com.Communication.pkg.Arrayent;

public class Entry {

	
	/**
	 * @param args
	 */
	public static void main(String[] args)
	{
		// TODO Auto-generated method stub
		Constants obj = new Constants();
		Arrayent testObj = new Arrayent();
		System.out.println("Server Ip" + obj.SERVER_IP_ADDRESS);
		testObj.Connect("");
		System.out.println("Test");
	
		try
		{
			 System.in.read();
			 
			 byte[] data = new byte[]{00,01,(byte) 0xED,05,(byte) 0xE6,0x22,0x1E,0x24,01};
			 System.out.println("PublishMessage");
			 testObj.SendMessage(data);
		} 
		catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}

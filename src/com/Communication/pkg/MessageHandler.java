package com.Communication.pkg;

import java.nio.ByteBuffer;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import sun.security.krb5.internal.APOptions;

import com.Common.pkg.GlobalStructures;
import com.test.pkg.RevealationMessage;

public class MessageHandler
{
	public MessageHandler() {
		// TODO Auto-generated constructor stub
	}
	
	public byte[] HandleRevealMessage(byte[] data)
	{
		RevealationMessage message = new RevealationMessage(ByteBuffer.wrap(data));
		switch(message._revealationApi)
		{
			case (byte) 0xE3: 
				// ECM/Appliance Info
				return HandleDeviceInformation(message._transactionId,message._revealationOpcode,message._revealationApi , "WPR33336L6L");				
			case (byte) 0xE4: // Reveal Subscription
				return HandleRevealSubscriptionCommands(message._transactionId,message._revealationApi,message._revealationOpcode);
			case (byte) 0xE5: // ECM Cache
				break;
			case (byte) 0xE6: // Reveal Interface
				break;
			case (byte) 0xE7: // Energy Measurements
				break;
			case (byte) 0xE8: //Date/Time Api
				return HandleDateTimeCommands(message._transactionId,message._revealationApi,message._revealationOpcode);
			case (byte) 0xE9: //Reset ECM Api
				break;
			default: break;
		}
		return null;
	}
	
	
	private byte[] HandleRevelaCommands(short transId, byte api,byte opcode)
	{
		switch(opcode)
		{
		case 0x01: //HandleSendReveal
			break;
		case 0x22: //Publish Reveal Message
			break;
		}
		return null;
	}
	
	private byte[] HandleDateTimeCommands(short transId, byte api, byte opcode)
	{
		switch (opcode) {
		case 0x01: return SendAck(transId, (byte)0xED, api, opcode);
		case 0x02: return SendAck(transId, (byte)0xED, api, opcode);
		default:
			break;
		}
		return null;
	}
	
	private byte[] HandleRevealSubscriptionCommands(short transId, byte api, byte opcode)
	{
		switch(opcode)
		{
			case 0x01:
				break;
			case 0x02:
				break;
			case 0x03:
				break;
			case 0x04: return GetSubscribeListVersion(transId,(byte)0xED,api,opcode)
;				
			case 0x05: return SendAck(transId, (byte)0xED, api, opcode);
		}
		return null;
	}
	
	private byte[] HandleDeviceInformation(short transId, byte opcode,byte api, String said)
	{
		switch(opcode)
		{
			case 0x01:
						return PublishDeviceInformation(transId,said,(byte)0XED,api,opcode);
			case 0x02:
						return PublishCommuncationMOduleInformation(transId, (byte)0xED, api, opcode);
			case 0x03:
				//To do : Implement Publish Board Information
				break;
		}
		
		return null;
		//return deviceInfo.array();
	}

	private byte[] GetSubscribeListVersion(short transId,byte messageHeader, byte api,byte opcode)
	{
		try
		{
			List<Byte> subscribeListInfo = new ArrayList<Byte>();
			
			//Add TransId
			Collections.addAll(subscribeListInfo, GlobalStructures.ShortToByteArray(transId));
			
			//Add Message Header 
			subscribeListInfo.add(messageHeader);
			
			//Add length
			Collections.addAll(subscribeListInfo, GlobalStructures.ShortToByteArray((short)4));
			
			//Add API 
			subscribeListInfo.add(api);
			
			//Add Opcode
			subscribeListInfo.add((byte)(opcode| (1 << 5)));
			
			//TO DO : Fetch from SubscribeList Version from DB
			Collections.addAll(subscribeListInfo, GlobalStructures.ShortToByteArray((short)0));
			
			return GlobalStructures.ObjectToByteArray(subscribeListInfo);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return null;
		}
		
	}
	
	private byte[] SendAck(short transId, byte messageHeader, byte api, byte opcode)
	{
		List<Byte> subscribeListAck = new ArrayList<Byte>();
		
		//Add Transid
		Collections.addAll(subscribeListAck, GlobalStructures.ShortToByteArray(transId));
		
		//Add Message Header
		subscribeListAck.add(messageHeader);
		
		//Add Length 
		Collections.addAll(subscribeListAck,GlobalStructures.ShortToByteArray((short)3));
		
		//Add Api
		subscribeListAck.add(api);
		
		//Add Opcode
		subscribeListAck.add((byte)(opcode| (1 << 5)));
		
		//Add payload
		subscribeListAck.add((byte)0);		
		
		return GlobalStructures.ObjectToByteArray(subscribeListAck);
	}
	
	
	private byte[] GetDeviceInformation(String said)
	{
		try
		{
			List<Byte> deviceInfo = new ArrayList<Byte>();
			
			// TO DO:
			// Get Device Information from DB/ Data store.
			
			//Add Brand
			deviceInfo.add((byte)01);
			
			//Add Category
			deviceInfo.add((byte)06);
			//Add Unique Id
			Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject(said.getBytes()));
			
			//Add Model Number
			//deviceInfo.put("MADDY".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("WEL98HEBU0".getBytes()));
			
			//Add Null character at the end
			//Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject("0".getBytes()));
			deviceInfo.add((byte)0x00);
			
			//Add Serial Number
			//deviceInfo.put("21901031".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("21901031".getBytes()));
			//Add Null character at the end
			//Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject("0".getBytes()));
			deviceInfo.add((byte)0x00);
			
			//Add Mac Address
			//deviceInfo.put("88:e7:12:00:27:7b".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("88:e7:12:00:27:7b".getBytes()));
			//Add Null character at the end
			deviceInfo.add((byte)0x00);
			
			return GlobalStructures.ObjectToByteArray(deviceInfo);
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return null;
		}
	}
	
	private byte[] GetCommunicationModuleInformation(short transId)
	{
		List<Byte> commModuleInfo = new ArrayList<Byte>();
		
		//Add Board partNumber
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("W00000001".getBytes()));
		
		//Add Board Revision Number 
		//Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("01".getBytes()));
		commModuleInfo.add((byte)01);
		//Add Project Release Number
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("W10605695".getBytes()));
			
		return GlobalStructures.ObjectToByteArray(commModuleInfo);
	}
	
	private byte[] PublishCommuncationMOduleInformation(short transId,byte messageHeader, byte api,byte opcode)
	{
		List<Byte> payLoad = new ArrayList<Byte>();
		
		//Add Transaction Id
		Collections.addAll(payLoad, GlobalStructures.ShortToByteArray(transId));
		
		//Add Message Header
		payLoad.add(messageHeader);
		
		byte[] deviceData = GetCommunicationModuleInformation(transId);
		
		//Add Length
		 Collections.addAll(payLoad, GlobalStructures.ShortToByteArray((short)(deviceData.length + 2)));
		
		//Add API
		payLoad.add(api);
			
		//Add opcode
		payLoad.add((byte)(opcode| (1 << 5)));
			
		//add deviceData
	    Collections.addAll(payLoad, GlobalStructures.ByteArrayToObject(deviceData));
		
		return GlobalStructures.ObjectToByteArray(payLoad);
		
	}
	
	private byte[] PublishDeviceInformation(short transId, String said,byte messageHeader,byte api,byte opcode)
	{
		
		List<Byte> payLoad = new ArrayList<Byte>();
		//ByteBuffer payLoad = ByteBuffer.wrap(GlobalStructures.ShortToByteArray(transId));
		
		//Add Transaction Id
		 Collections.addAll(payLoad, GlobalStructures.ShortToByteArray(transId));
		
		//Add Message Header 
		payLoad.add(messageHeader);
		
		byte[] deviceData=GetDeviceInformation(said);
		
		//Add Length
		 Collections.addAll(payLoad, GlobalStructures.ShortToByteArray((short)(deviceData.length + 2)));
		
		//Add API
		//payLoad.put(api);
		payLoad.add(api);
		
		//Add opcode
		payLoad.add((byte)(opcode| (1 << 5)));
		
		//add deviceData
		// payLoad.put(deviceData);
		
	    Collections.addAll(payLoad, GlobalStructures.ByteArrayToObject(deviceData));
		
		//payLoad.flip();
		
		return GlobalStructures.ObjectToByteArray(payLoad);
	}
}

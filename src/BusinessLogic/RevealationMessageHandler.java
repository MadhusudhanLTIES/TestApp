package BusinessLogic;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import Common.GlobalStructures;
import DTO.RevealationMessage;
import DTO.RevealationMessageEventObject;
import Interface.IRevealMessageHandler;
import Interface.IRevealationMessageHandler;

public class RevealationMessageHandler implements IRevealationMessageHandler
{
	IRevealMessageHandler _revealMessageHandler;
	
	 public synchronized void SubscribeMessageHandler( IRevealMessageHandler msgHandler ) 
	 {
		 _revealMessageHandler = msgHandler;
	 }
	    
	 public synchronized void UnSubscribeMessageHandler()
	 {
		 _revealMessageHandler = null;
	 }
	
	
	public RevealationMessageHandler() {
		// TODO Auto-generated constructor stub
	}
	
	//public byte[] HandleRevealMessage(byte[] data)
	public byte[] HandleRevealationMessage(RevealationMessageEventObject result)
	{
		//RevealationMessage message = new RevealationMessage(ByteBuffer.wrap(data));
		switch(result.get_revealMessage()._revealationApi)
		{
			case (byte) 0xE3: 
				// ECM/Appliance Info
				return HandleDeviceInformation(result.get_revealMessage()._transactionId,result.get_revealMessage()._revealationOpcode,result.get_revealMessage()._revealationApi , "WPR33336L6L");				
			case (byte) 0xE4: // Reveal Subscription
				return HandleRevealSubscriptionCommands(result.get_revealMessage()._transactionId,result.get_revealMessage()._revealationApi,result.get_revealMessage()._revealationOpcode);
			case (byte) 0xE5: // ECM Cache
				break;
			case (byte) 0xE6: // Reveal Interface
				return HandleRevealationCommands(result.get_revealMessage());
			case (byte) 0xE7: // Energy Measurements
				break;
			case (byte) 0xE8: //Date/Time Api
				break;
			case (byte) 0xE9: //Reset ECM Api
				break;
			default: break;
		}
		return null;
	}
	
	private byte[] HandleRevealationCommands(RevealationMessage revealtionMsg )
	{
		switch(revealtionMsg._revealationOpcode)
		{
			case 0x01: // Reveal Command 
				break;
			case 0x21: // Reveal Command Ack
				break;
			case 0x22: // Publish Reveal
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
			case 0x04:
				break;
			case 0x05:
				break;
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
	
	private byte[] SubscribeListAck(short transId, byte messageHeader, byte api, byte opcode)
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
	
	private byte[] RaiseEvent()
	{
		return null;
	}
	
	private byte[] GetDeviceInformation(String said)
	{
		try
		{
			List<Byte> deviceInfo = new ArrayList<Byte>();
			
			// TO DO:
			// Get Device Information from DB/ Data store.
			
			//Add Category
			deviceInfo.add((byte)06);
			//Add Unique Id
			Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject(said.getBytes()));
			
			//Add Model Number
			//deviceInfo.put("MADDY".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("MADDY".getBytes()));
			//Add Null character at the end
			Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
			
			//Add Serial Number
			//deviceInfo.put("21901031".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("21901031".getBytes()));
			//Add Null character at the end
			Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
			
			//Add Mac Address
			//deviceInfo.put("88:e7:12:00:27:7b".getBytes());
			Collections.addAll(deviceInfo,GlobalStructures.ByteArrayToObject("88:e7:12:00:27:7b".getBytes()));
			//Add Null character at the end
			Collections.addAll(deviceInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
			//deviceInfo.flip();
			
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
		//Add Null character at the end
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
		
		//Add Board Revision Number 
		commModuleInfo.add((byte)01);
		//Add Null character at the end
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
		
		//Add Project Release Number
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("W10605695".getBytes()));
		//Add Null character at the end
		Collections.addAll(commModuleInfo, GlobalStructures.ByteArrayToObject("00".getBytes()));
		
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

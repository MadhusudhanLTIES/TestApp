package DTO;

import java.nio.ByteBuffer;

public class RevealCommand
{
	public byte NumberOfCommands;
	public byte[] WidePacketData;
	
	public RevealCommand(ByteBuffer data)
	{
		NumberOfCommands = data.get();
		WidePacketData = data.array();
		
	}
}

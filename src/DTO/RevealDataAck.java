package DTO;

import java.nio.ByteBuffer;


public class RevealDataAck 
{
	public byte ReasonCode;
	public byte Source;
	public byte Destination;
	public byte Api;
	public byte Opcode;
	
	public RevealDataAck(ByteBuffer data)
	{
		ReasonCode=data.get();
		Source=data.get();
		Destination=data.get();
		Api=data.get();
		Opcode=data.get();
	}
}

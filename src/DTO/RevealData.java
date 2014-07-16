package DTO;

import java.nio.ByteBuffer;

public class RevealData
{
	public short SeqId;
	public byte Source;
	public byte Destination;
	public byte Api;
	public byte Opcode;
	public byte PayloadLength;
	public byte[] Payload;
	
	public RevealData(ByteBuffer data)
	{
		SeqId = data.getShort();
		Source=data.get();
		Destination=data.get();
		Api=data.get();
		Opcode= data.get();
		PayloadLength = data.get();
		Payload=data.array();
	}
}

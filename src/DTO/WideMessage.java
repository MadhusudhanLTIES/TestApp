package DTO;

import java.util.Arrays;

public class WideMessage 
{
	public byte _source;
	public byte _destination;
	public byte _header;
	public byte _sap;
	public byte _payloadLength;
	public byte _api;
	public byte _opcode;
	public byte[] _payLoad;
	
	public WideMessage(byte[] packet) 
	{
		// TODO Auto-generated constructor stub
		_header = packet[0];
		_source = (byte)(packet[1]>>4);
		_destination = (byte)(packet[1]&0x0F);
		_sap = (byte) (packet[2]>>4);
		_payloadLength = (byte) (packet[2] & 0x0F);
		_api = packet[3];
		_opcode = packet[4];
		
		_payLoad = Arrays.copyOfRange(packet, 5, _payloadLength-2);
	}
}

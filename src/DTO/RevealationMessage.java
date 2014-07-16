package DTO;

import java.nio.ByteBuffer;

public class RevealationMessage
{
	public byte _header;
	public Short _transactionId;
	public Short _messageLength;
	public byte _revealationApi;
	public byte _revealationOpcode;
	public byte[] _revealationPayload;
	
	public RevealationMessage(ByteBuffer data)
	{
		// TODO Auto-generated constructor stub
		_transactionId = data.getShort();
		_header = data.get();
		_messageLength = data.getShort();
		_revealationApi = data.get();
		_revealationOpcode = data.get();
		
		if(_messageLength>2)
			_revealationPayload= data.array();
	}
}

package Interface;

import DTO.RevealationMessageEventObject;

public interface IRevealMessageHandler
{
	//Handles Reveal Messages
	public byte[] HandleRevealMessage(RevealationMessageEventObject e, short transId, byte opcode, byte mesageHeader);
}

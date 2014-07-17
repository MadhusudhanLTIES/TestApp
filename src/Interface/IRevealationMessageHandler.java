package Interface;

import DTO.RevealationMessageEventObject;

public interface IRevealationMessageHandler 
{
	//Handles Revelation Messages
	public byte[] HandleRevealationMessage(RevealationMessageEventObject e);
	public byte[] ConstructRevealationMessage(byte[] revealData,byte revealationApi,byte revealtionOpcode);
}

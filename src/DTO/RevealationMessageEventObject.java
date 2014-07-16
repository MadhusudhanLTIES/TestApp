package DTO;

import java.nio.ByteBuffer;
import java.util.EventObject;

public class RevealationMessageEventObject extends EventObject
{
	private RevealationMessage _revealMessage;

	public RevealationMessageEventObject(Object source ,byte[] data) {
		super(source);
		// TODO Auto-generated constructor stub
		set_revealMessage(new RevealationMessage(ByteBuffer.wrap(data)));
	}

	/**
	 * @return the _revealMessage
	 */
	public RevealationMessage get_revealMessage() {
		return _revealMessage;
	}

	/**
	 * @param _revealMessage the _revealMessage to set
	 */
	public void set_revealMessage(RevealationMessage _revealMessage) {
		this._revealMessage = _revealMessage;
	}

}

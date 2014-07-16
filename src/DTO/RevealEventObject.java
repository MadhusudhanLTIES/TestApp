package DTO;

import java.nio.ByteBuffer;
import java.util.EventObject;

public class RevealEventObject extends EventObject
{

	private RevealData _reveal;
	
	public RevealEventObject(Object source, byte[] data) {
		super(source);
		// TODO Auto-generated constructor stub
		_reveal = new RevealData(ByteBuffer.wrap(data));
	}

	/**
	 * @return the _reveal
	 */
	public RevealData get_reveal() {
		return _reveal;
	}

	/**
	 * @param _reveal the _reveal to set
	 */
	public void set_reveal(RevealData _reveal) {
		this._reveal = _reveal;
	}

}

package Common;

import java.util.ArrayList;
import java.util.List;

public class GlobalStructures
{
	public enum REVEAL_API
	{
		ECM_INFO( (byte)0xE3),
		REVEAL_SUBSCRIPTION((byte)0xE4);
		
		public byte value;
		
		private REVEAL_API(byte value)
		{
			this.value = value;
		}
	}

	public enum REVEAL_OPCODE
	{
		GET_DEVICE_INFO((byte)0x01),
		GET_DEVICE_COMM_MODULEINFO((byte)0x02),
		GET_BOARD_INFO((byte)0x03);
		
		public byte value;
		
		private REVEAL_OPCODE(byte value)
		{
			this.value = value;
		}
	}
	
	public static Byte[] ShortToByteArray(short s) {
		return new Byte[] {(byte) ((s & 0xFF00) >> 8), (byte) (s & 0x00FF) };
	}
	
	public static Byte[] ByteArrayToObject(byte[] data)
	{
		Byte[] resultData = new Byte[data.length];
	    int i = 0;
		for (byte b : data) resultData[i++] = b; //Autoboxing
		
		return resultData;
	}
	
	public static byte[] ObjectToByteArray(List<Byte> data)
	{
		byte[] resultData = new byte[data.size()];
		int j=0;
		// Unboxing byte values. (Byte[] to byte[])
		for(Byte b: data)
			resultData[j++] = b.byteValue();
		
		return resultData;
	}
}

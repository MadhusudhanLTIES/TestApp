package Common;

public class Constants 
{
	public String ARRAYENT_DAEMON_PATH ="aicd";
	public String ARRAYENT_CLIENT_PATH="aicclient";
	
	public final int SERVER_PORT=7055;
	//public final String SERVER_IP_ADDRESS="192.168.15.255";
	//public final String SERVER_IP_ADDRESS="192.168.1.6";
	public final String SERVER_IP_ADDRESS="127.0.0.1";
	public final byte REVEAL_FIRMWARE = (byte)0xE1;
	public final byte REVEAL_SAID_AES_DNS = (byte)0xE2;
	public final byte REVEAL_APPL_ECM_INFO = (byte)0xE3;
	public final byte REVEAL_SUBSCRIPTION = (byte)0xE4;
	public final byte REVEAL_ECM_CACHE = (byte)0xE5;
	public final byte REVEAL_RV_INTERFACE = (byte)0xE6;
	public final byte REVEAL_ENERGY_MEASUREMENTS = (byte)0xE7;
	public final byte REVEAL_DATETIME = (byte)0xE8;
	public final byte REVEAL_RESET_ECM = (byte)0xE9;
	
	public final byte SOURCE_NODE=0x06;
	public final byte DESTINATION_NODE=0x0F;
	
	public final int HEARTBEAT_INTERVAL= 10000;
}

package Interface;

public interface IArrayent
{
	public Boolean Connect(String IpAddress,int portNumber);
	public Boolean Disconnect();
	public Boolean SendData(byte[] data);
	public byte[] ReceivedData();
}

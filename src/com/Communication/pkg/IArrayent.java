package com.Communication.pkg;

public interface IArrayent
{
	public Boolean Connect(String IpAddress);
	public Boolean Disconnect();
	public Boolean SendData(byte[] data);
	public byte[] ReceivedData();
}

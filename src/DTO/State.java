package DTO;

public class State 
{
	private int _duration;
	private String _stateName;
	private int _id;
	private byte[] _wideData;
	
	
	public State(int duration,String stateName,int id, byte[]wideData)
	{
		this._duration = duration;
		this._stateName = stateName;
		this._id = id;
		this._wideData = wideData;
	}
}

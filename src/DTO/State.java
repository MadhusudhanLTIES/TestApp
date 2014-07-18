package DTO;

public class State 
{
	private int _duration;
	private String _stateName;
	private int _id;
	private byte[] _wideData;
	private byte _revealationApi;
	private byte _revealationOpcode;
	
	
	/**
	 * @return the _duration
	 */
	public int get_duration() {
		return _duration;
	}


	/**
	 * @param _duration the _duration to set
	 */
	public void set_duration(int _duration) {
		this._duration = _duration;
	}


	/**
	 * @return the _stateName
	 */
	public String get_stateName() {
		return _stateName;
	}


	/**
	 * @param _stateName the _stateName to set
	 */
	public void set_stateName(String _stateName) {
		this._stateName = _stateName;
	}


	/**
	 * @return the _id
	 */
	public int get_id() {
		return _id;
	}


	/**
	 * @param _id the _id to set
	 */
	public void set_id(int _id) {
		this._id = _id;
	}


	/**
	 * @return the _wideData
	 */
	public byte[] get_wideData() {
		return _wideData;
	}


	/**
	 * @param _wideData the _wideData to set
	 */
	public void set_wideData(byte[] _wideData) {
		this._wideData = _wideData;
	}


	/**
	 * @return the _revealationApi
	 */
	public byte get_revealationApi() {
		return _revealationApi;
	}


	/**
	 * @param _revealationApi the _revealationApi to set
	 */
	public void set_revealationApi(byte _revealationApi) {
		this._revealationApi = _revealationApi;
	}


	/**
	 * @return the _revealationOpcode
	 */
	public byte get_revealationOpcode() {
		return _revealationOpcode;
	}


	/**
	 * @param _revealationOpcode the _revealationOpcode to set
	 */
	public void set_revealationOpcode(byte _revealationOpcode) {
		this._revealationOpcode = _revealationOpcode;
	}


	public State(byte api,byte opcode, byte[]wideData, int duration,String stateName,int id)
	{
		this.set_revealationApi(api);
		this.set_revealationOpcode(opcode);
		this.set_duration(duration);
		this.set_stateName(stateName);
		this.set_id(id);
		this.set_wideData(wideData);
	}
}

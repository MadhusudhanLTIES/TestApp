package DTO;

public class Appliance
{
	private String _said;
	
	private String _aesKey;
	
	private String _password;
	
	private String _connectionId;
	
	private int _tcpListenPort;
	
	private Boolean _isApplianceInitialized;
	
	private Boolean _isCycleStarted;
	
	private Long _duration;
	
	/**
	 * @param _isApplianceInitialized the _isApplianceInitialized to set
	 */
	public void set_isApplianceInitialized(Boolean _isApplianceInitialized) {
		this._isApplianceInitialized = _isApplianceInitialized;
	}

	/**
	 * @return the _isApplianceInitialized
	 */
	public Boolean get_isApplianceInitialized() {
		return _isApplianceInitialized;
	}
	
	/**
	 * @param _password the _password to set
	 */
	public void set_password(String _password) {
		this._password = _password;
	}

	/**
	 * @return the _password
	 */
	public String get_password() {
		return _password;
	}
	
	/**
	 * @param _said the _said to set
	 */
	public void set_said(String _said) {
		this._said = _said;
	}

	/**
	 * @return the _said
	 */
	public String get_said() {
		return _said;
	}

	/**
	 * @param _aesKey the _aesKey to set
	 */
	public void set_aesKey(String _aesKey) {
		this._aesKey = _aesKey;
	}

	/**
	 * @return the _aesKey
	 */
	public String get_aesKey() {
		return _aesKey;
	}
	
	/**
	 * @param _isCycleStarted the _isCycleStarted to set
	 */
	public void set_isCycleStarted(Boolean _isCycleStarted) {
		this._isCycleStarted = _isCycleStarted;
	}

	/**
	 * @return the _isCycleStarted
	 */
	public Boolean get_isCycleStarted() {
		return _isCycleStarted;
	}
	
	/**
	 * @param _connectionId the _connectionId to set
	 */
	public void set_connectionId(String _connectionId) {
		this._connectionId = _connectionId;
	}

	/**
	 * @return the _connectionId
	 */
	public String get_connectionId() {
		return _connectionId;
	}

	/**
	 * @param _tcpListenPort the _tcpListenPort to set
	 */
	public void set_tcpListenPort(int _tcpListenPort) {
		this._tcpListenPort = _tcpListenPort;
	}

	
	/**
	 * @return the _tcpListenPort
	 */
	public int get_tcpListenPort() {
		return _tcpListenPort;
	}

	public Long get_duration() {
		return _duration;
	}

	public void set_duration(Long _duration) {
		this._duration = _duration;
	}
	
	public Appliance(String said, String aesKey, String password ,String id,int portNumber)
	{
			this.set_said(said);
			this.set_aesKey(aesKey);
			this.set_password(password);
			this.set_connectionId(id);
			this.set_isApplianceInitialized(false);
			this.set_isCycleStarted(false);
			this.set_tcpListenPort(portNumber);
	}

}
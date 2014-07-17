package Communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import BusinessLogic.*;
import Common.Constants;
import DTO.Appliance;
import DTO.State;
import Interface.IArrayentBackEndCommunication;

public class ArrayentBackEndCommunication  extends TimerTask implements IArrayentBackEndCommunication
{
	public Appliance _applInstance;
	
	Process _client, _daemon;
	Constants _constantobj;
	Arrayent _arrayentclientConnector;
	int _duration;
	
	TimerTask _timerTask;
	Timer _timer;
	Date _startTime;
	
	List<State> _states;

	//Default Constructor
	public ArrayentBackEndCommunication()
	{
		
	}
	
	public ArrayentBackEndCommunication(Appliance app)
	{
		this._states = new ArrayList<State>();
		this._applInstance=app;
		_constantobj = new Constants();
		_arrayentclientConnector= new Arrayent();
		_arrayentclientConnector.SubscribeMessageHandler(new RevealationMessageHandler());
		
		_timerTask = new ArrayentBackEndCommunication();
		_timer = new Timer(true);
		
	}
	
	public Boolean CreateArrayentChannel(int portNumber)
	{
		try
		{
			if(CreateDaemonProcess(portNumber))
			{
				Thread.sleep(8000);
				_arrayentclientConnector.Connect(_constantobj.SERVER_IP_ADDRESS,portNumber);
				return true;
			}
			else
			{
				KillProcess();
				return false;
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
			return false;
		}
	}
	
	public Boolean KillProcess()
	{
		if(_client!=null)
			_client.destroy();
		
		if(_daemon!=null)
			_daemon.destroy();
		
		if(_arrayentclientConnector!=null)
			_arrayentclientConnector.Disconnect();
		return true;
	}
	
	private Boolean CreateDaemonProcess(int portNumber)
	{
		try
		{
			_daemon = new ProcessBuilder(System.getProperty("user.dir")+"/"+_constantobj.ARRAYENT_DAEMON_PATH,"-I",_applInstance.get_said(),"--no-login").start();
			Thread.sleep(2000);
			_client = new ProcessBuilder(System.getProperty("user.dir")+"/"+_constantobj.ARRAYENT_CLIENT_PATH,_applInstance.get_said(),_applInstance.get_password(),_applInstance.get_aesKey(),"11",Integer.toString(portNumber)).start();
			return true;
		}
		catch(Exception exp)
		{
			return false;
		}
	}

	@Override
	public Boolean StartCycle()
	{
		// TODO Auto-generated method stub
		_startTime = new Date();
		
		_timer.scheduleAtFixedRate(_timerTask, new Date(),_constantobj.HEARTBEAT_INTERVAL);
		
		return null;
	}

	@Override
	public Boolean StopCycle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Boolean PauseCycle() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void run() 
	{
		// TODO Auto-generated method stub
		if(this._states.size()>0)
		{
			if(new Date().getTime()-_startTime.getTime()<= _duration *60 *1000)
			{
				//_arrayentclientConnector.SendData();
			}
			else
				_timer.cancel();
		}
	}

	@Override
	public Boolean AddStates(List<State> states) {
		// TODO Auto-generated method stub
		this._states.addAll(states);
		return true;
	}
}

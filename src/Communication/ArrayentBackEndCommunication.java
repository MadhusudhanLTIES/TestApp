package Communication;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import BusinessLogic.*;
import Common.Constants;
import DTO.Appliance;
import DTO.State;
import Interface.IArrayentBackEndCommunication;
import Interface.IRevealationMessageHandler;

public class ArrayentBackEndCommunication implements IArrayentBackEndCommunication
{
	public Appliance _applInstance;
	
	Process _client, _daemon;
	Constants _constantobj;
	Arrayent _arrayentclientConnector;
	int _duration;
	
	
	Date _startTime;
	
	List<State> _states;
	
	State _currentState;
	
	IRevealationMessageHandler _revealationMessageHandler;
	
	ScheduledExecutorService _executor;
	
	Runnable _task;
	
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
		_revealationMessageHandler= new RevealationMessageHandler();
		_arrayentclientConnector.SubscribeMessageHandler(_revealationMessageHandler);
		_executor= Executors.newSingleThreadScheduledExecutor();
		_task = new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				RunTasks();
			}
		};
		
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
		
		
		_executor.scheduleAtFixedRate(_task, 0, _constantobj.HEARTBEAT_INTERVAL,TimeUnit.SECONDS);
		
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

	
	private void RunTasks() 
	{
		// TODO Auto-generated method stub
		try
		{
			//System.out.println("In Run Tasks " + new Date().toString());
			if(this._states!=null && this._states.size()>0)
			{
				//System.out.println("Total no of states " + this._states.size());
				if(_currentState==null)
				{
					_currentState = this._states.remove(0);
					_startTime = new Date();
				}
				if(new Date().getTime()-_startTime.getTime()<= _duration *5 *1000)
					{
						//System.out.println("Start Time If + " + _startTime.toString());
						System.out.println(_currentState.get_stateName() +"Running");
						_arrayentclientConnector.SendMessage(_revealationMessageHandler.ConstructRevealationMessage(_currentState.get_wideData(), _currentState.get_revealationApi(), _currentState.get_revealationOpcode()));
					}
					else
					{
						_currentState = this._states.remove(0);
						_duration=_currentState.get_duration();
						_startTime= new Date();
						//System.out.println("Start Time Else + " + _startTime.toString());
					}
			}
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}

	@Override
	public Boolean AddStates(List<State> states) {
		// TODO Auto-generated method stub
		this._states.addAll(states);
		return true;
	}
}

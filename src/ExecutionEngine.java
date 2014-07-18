
/* $Author: Madhusudhan.G $
   $Revision: #1.0 $
   $DateTime: 2014/06/27  $
*/

import java.io.BufferedReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import javax.jws.WebService;

import Communication.ArrayentBackEndCommunication;
import DTO.Appliance;
import DTO.State;
import DataAccessLayer.QueryHandler;
import Interface.IExecutionEngine;

@WebService
public class ExecutionEngine implements IExecutionEngine
{
	List<ArrayentBackEndCommunication> _applainces;
	int portNumber=8055;
	QueryHandler _queryHandler;
	
	List<State> _states;
	
	public Boolean CreateAnAppliance(String said)
	{
		
		Appliance app = new Appliance(said, _queryHandler.GetAesKey(said), _queryHandler.GetPassword(said),"11",portNumber++);
		_applainces.add(new ArrayentBackEndCommunication(app));
		return true;
	}

	@Override
	public Boolean InstantiateAppliance(String said) 
	{
		// TODO Auto-generated method stub
		
		for (ArrayentBackEndCommunication app : _applainces) 
		{
			if(app._applInstance.get_said()== said && !app._applInstance.get_isApplianceInitialized())
			{
				app._applInstance.set_isApplianceInitialized(true);
				app.CreateArrayentChannel(app._applInstance.get_tcpListenPort());
				break;
			}
		}
		
		return true;
	}

	@Override
	public Boolean StartApplainceCycle(String said) 
	{
		//TODO Auto-generated method stub
		
		for (ArrayentBackEndCommunication app : _applainces) 
		{
			if(app._applInstance.get_said()== said)
			{
				try
				{
				if(app._applInstance.get_isApplianceInitialized() && !app._applInstance.get_isCycleStarted())
				{
					app._applInstance.set_isCycleStarted(true);
					
					app.AddStates(_states);
					app.StartCycle();
					return true;
				}
				}
				catch(Exception exp)
				{
					exp.printStackTrace();
				}
			}
		}
		
		return false;
	}

	@Override
	public Boolean StopApplianceCycle(String said) {
		// TODO Auto-generated method stub
		for (ArrayentBackEndCommunication app : _applainces) 
		{
			if(app._applInstance.get_said()== said)
			{
				if(app._applInstance.get_isApplianceInitialized()&& app._applInstance.get_isCycleStarted())
				{
					app._applInstance.set_isCycleStarted(false);
					
					_states.clear();
					
					app.StopCycle();
					return true;
				}
			}
		}
		
		return false;
	}
	
	@Override
	public Boolean RemoveAppliance(String said) 
	{
		// TODO Auto-generated method stub
		for (ArrayentBackEndCommunication app : _applainces) 
		{
			if(app._applInstance.get_said()== said && app._applInstance.get_isApplianceInitialized())
			{
				app.KillProcess();
				_applainces.remove(app);
				break;
			}
		}
		return true;
	}
	
	
	@Override
	public String[] GetSAIDs()
	{
		return (String[])_queryHandler.GetSAIDS().toArray();
	}

	//@Override
	public Boolean UpLoadSAIDs(BufferedReader br)
	{
		// TODO Auto-generated method stub
		try 
		{
			String line;
			_queryHandler.Create_SAID_Table();
			while((line=br.readLine())!=null)
			{
				String[] data =  line.split(" ");
				if(data.length>2)
				{
					_queryHandler.InsertSAIDData(data[0], data[2], data[1]);
				}
			}
			return true;
		} 
		catch (Exception e) 
		{
			// TODO: handle exception
			return false;
		}
	}
	
	@Override
	public Boolean AddState(int id, String stateName, byte api, byte opcode,
			byte[] payLoad, int duration) {
		_states.add(new State(api, opcode, payLoad, duration, stateName, id));
		return true;
	}
	
	
	public ExecutionEngine()
	{
		_applainces= new ArrayList<ArrayentBackEndCommunication>();
		_queryHandler= QueryHandler.GetQueryHandlerObject();
		_queryHandler.InitializeConnection("test");
		_states= new ArrayList<State>();
	}

	



}

import java.io.BufferedReader;
import java.io.FileReader;


public class EntryClass
{

	static ExecutionEngine execObj= new ExecutionEngine();
	public static void main(String[] args) 
	{
		try
		{
			// TODO Auto-generated method stub
			
			
			//Read File for list of Said's
			BufferedReader br = new BufferedReader(new FileReader("SAID"));
			
			//Upload SAIDs to db
			execObj.UpLoadSAIDs(br);
			
			//Create an appliance
			execObj.CreateAnAppliance("WPR33336L6L");
			
			System.out.println("Appliance Created");
			
			System.in.read();
			
			//Instantiate an appliance 
			execObj.InstantiateAppliance("WPR33336L6L");
			System.out.println("Appliance Instantiated");
			System.in.read();
			
			System.out.println("Adding Sample States");
			//Add Sample States
			AddSampleStates();
			
		
			//Start an Appliance 
			execObj.StartApplainceCycle("WPR33336L6L");
			
			System.in.read();
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}
	
	private static void AddSampleStates()
	{
		byte[] data ={0x17,0x06,0x02,0x02,0x00};
		byte api = (byte)0xE6;byte opcode=(byte)0x22;
		execObj.AddState(1, "Initial",api ,opcode , data, 2);
		
		byte[] smartgridData={0x08,0x04,0x02,0x00,0x00};
		execObj.AddState(2, "SmartGridData", api, opcode, smartgridData, 2);
		
		byte[] startCycleData={0x1E,0x01,0x00};
		execObj.AddState(3, "startCycleData", api, opcode, startCycleData, 2);
		
		byte[] data1={0x1E,0x01,0x04,0x00,0x00,0x05,00};
		execObj.AddState(4, "Data", api, opcode, data1, 2);
		
		int i=0;
		int  id=4;
		while(i<10)
		{
			if(i<5)
			{
				byte[] statusData={0x1E,0x05,0x01,0x02};
				execObj.AddState(id++, "Data", api, opcode, statusData, 2);
			}
			else
			{
				byte[] statusData1={0x1E,0x05,0x01,0x01};
				execObj.AddState(id++, "Data", api, opcode, statusData1, 2);
			}
			i++;
		}
		
	}

}

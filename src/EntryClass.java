import java.io.BufferedReader;
import java.io.FileReader;


public class EntryClass
{

	public static void main(String[] args) 
	{
		try
		{
			// TODO Auto-generated method stub
			ExecutionEngine execObj= new ExecutionEngine();
			
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
			
			//Start an Appliance 
			//execObj.StartApplainceCycle("WPR33336L6L");
		}
		catch(Exception exp)
		{
			exp.printStackTrace();
		}
	}

}

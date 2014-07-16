package Interface;

import java.io.InputStream;

import javax.jws.WebMethod;

public interface IExecutionEngine
{
	@WebMethod(operationName="CreateAnAppliance")
	public Boolean CreateAnAppliance(String said);
	
	@WebMethod(operationName="InstantiateAppliance")
	public Boolean InstantiateAppliance(String said);
	
	@WebMethod(operationName="StartApplainceCycle")
	public Boolean StartApplainceCycle(String said);
	
	@WebMethod(operationName="StopApplianceCycle")
	public Boolean StopApplianceCycle(String said);
	
	//@WebMethod(operationName="UpLoadSAIDs")
	//public Boolean UpLoadSAIDs(InputStream br);
	
	@WebMethod(operationName="GetSAIDs")
	public String[] GetSAIDs();
	
	@WebMethod(operationName="RemoveAppliance")
	public Boolean RemoveAppliance(String said);
}

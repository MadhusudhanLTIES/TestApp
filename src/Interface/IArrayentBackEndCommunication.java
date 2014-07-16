package Interface;

public interface IArrayentBackEndCommunication
{
	public Boolean CreateArrayentChannel(int portNumber);
	public Boolean KillProcess();
	public Boolean StartCycle(int periodInMinutes);
	public Boolean StopCycle();
	public Boolean PauseCycle();
}

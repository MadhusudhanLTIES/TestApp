package Interface;

import java.util.List;

import DTO.State;

public interface IArrayentBackEndCommunication
{
	public Boolean CreateArrayentChannel(int portNumber);
	public Boolean KillProcess();
	public Boolean AddStates(List<State> states);
	public Boolean StartCycle();
	public Boolean StopCycle();
	public Boolean PauseCycle();
}

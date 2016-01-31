package nxt;

import communication.Command;

public interface NXT {

	public void sendCommand(Command command);
	
	public void disconnect();
	
	public void waitForDone();
}

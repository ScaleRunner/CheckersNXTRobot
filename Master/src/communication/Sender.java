package communication;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;

public class Sender {
	
	private static DataOutputStream dataOut;
	
	public Sender(Connection connection){
		dataOut = connection.getDataOutputStream();
	}
	
	public void sendCommand(Command command){
		sendInt(command.getCommand());
		sendInt(command.getPos().x);
		sendInt(command.getPos().y);
		System.out.println("Command sent");
		try {
			dataOut.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	private void sendInt(int i){
		try{
			dataOut.writeInt(i);
		}catch(IOException ioE){
			ioE.getMessage();
		}
	}
}

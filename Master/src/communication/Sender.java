package communication;

import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;

public class Sender {
	
	private DataOutputStream dataOut;
	
	public Sender(DataOutputStream dataOut){
		this.dataOut = dataOut;
	}
	
	public Sender(Connection connection){
		dataOut = connection.getDataOutputStream();
	}
	
	public void sendCommand(Command command){
		sendInt(command.getCommand());
		sendInt(command.getPos().x+2);
		sendInt(command.getPos().y+2);
//		System.out.println("Sending command: " + command.toString());
		try {
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
//		System.out.println("Sending Done!");
	}
	
	private void sendInt(int i){
		try{
			dataOut.writeInt(i);
		}catch(IOException ioE){
			ioE.getMessage();
		}
	}
}

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
		sendInt(command.getPos().x);
		sendInt(command.getPos().y);
		System.out.println("Sending...");
		try {
			dataOut.flush();
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Flushed");
	}
	
	private void sendInt(int i){
		try{
			System.out.println("sent i = " + i);
			dataOut.writeInt(i);
		}catch(IOException ioE){
			ioE.getMessage();
		}
	}
}

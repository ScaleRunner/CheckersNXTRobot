package connection;

import java.io.DataOutputStream;
import java.io.IOException;

public class Sender {
	
	private DataOutputStream dataOut;
	
	public Sender(Connection connection){
		this.dataOut = connection.getDataOutputStream();
	}
	
	public void sendDone(){
		try{
			dataOut.write(1);
			dataOut.flush();
		}catch(IOException ioE){
			ioE.getMessage();
		}
	}
}
package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

public class Listener extends Thread{
	private boolean io = false;
	private static DataInputStream dataIn;
	private static DataOutputStream dataOut;
	private static int lastCommand;
	
	public Listener(Connection connection){
		this.dataIn = connection.getDataInputStream();
		this.dataOut = connection.getDataOutputStream();
	}
	
	public void listen(){
		io = true;
	}
	
	public int getCommand(){
		return lastCommand;
	}
	
	public void run(){
		while(true){
			if(io){
				try{
					lastCommand = dataIn.readInt();
					if (lastCommand>0)
						io = false;
				} catch(IOException ioException){
					System.out.println("Could not read Int");
				}
			}
		}
	}
}

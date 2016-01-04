package communication;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Connection {
	public static NXTConnector link;
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;

	public Connection(NXTInfo nxtInfo){
		link = new NXTConnector();
		
		if(!link.connectTo(nxtInfo.name, nxtInfo.deviceAddress, nxtInfo.protocol)){
			System.out.println("No NXT found using USB");
		}
		else{
			dataOut = new DataOutputStream(link.getOutputStream());
			dataIn = new DataInputStream(link.getInputStream());
			System.out.println("Connection established");
		}
	}
	
	public DataOutputStream getOutputStream(){
		return dataOut;
	}
	
	public DataInputStream getInputStream(){
		return dataIn;
	}
	
	public void disconnect(){
		try{
			dataOut.close();
			dataIn.close();
			link.close();
		}catch(Exception exception){
			exception.getCause();
		}
		System.out.println("Connection closed");
	}
}

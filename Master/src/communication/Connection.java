package communication;

import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;

public class Connection {
	public NXTConnector link;
	private DataOutputStream dataOut;
	private DataInputStream dataIn;

	public Connection(DataInputStream dataIn, DataOutputStream dataOut){
		this.dataIn = dataIn;
		this.dataOut = dataOut;
	}
	
	public Connection(NXTInfo nxtInfo){
		link = new NXTConnector();
		
		if(!link.connectTo(nxtInfo.name, nxtInfo.deviceAddress, nxtInfo.protocol)){
			System.out.println("No NXT found using protocol " + nxtInfo.protocol);
		}
		else{
			dataOut = new DataOutputStream(new BufferedOutputStream(link.getOutputStream(),1024));
			dataIn = new DataInputStream(link.getInputStream());
			System.out.println("Connection with " + nxtInfo.name + " made.");
		}
	}
	
	public DataOutputStream getDataOutputStream(){
		return dataOut;
	}
	
	public DataInputStream getDataInputStream(){
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

package connection;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Connection {
	private BTConnection BTLink = Bluetooth.waitForConnection();
//	private USBConnection USBLink = USB.waitForConnection();
	
	private DataOutputStream dataOut = BTLink.openDataOutputStream();
	private DataInputStream dataIn = new DataInputStream(new BufferedInputStream(BTLink.openDataInputStream(),1024));
	
	public Connection(){
		System.out.println("Connected!");
	}
	
	public DataInputStream getDataInputStream(){
		return(dataIn);
	}
	
	public DataOutputStream getDataOutputStream(){
		return(dataOut);
	}
	
	public void disconnect(){
		try{
			dataOut.close();
			dataIn.close();
			BTLink.close();
//			USBLink.close();
		}
		catch (IOException IOe){
			System.out.println("Error closing connection");
		}
		System.out.println("Conection Closed");
	}
}
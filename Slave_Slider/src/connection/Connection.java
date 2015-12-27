package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;
	private static USBConnection USBLink;
	private static BTConnection BTLink;
	
	public Connection(){
		System.out.println("Waiting...");
	    //BTLink = Bluetooth.waitForConnection();    
	    //dataOut = BTLink.openDataOutputStream();
	    //dataIn = BTLink.openDataInputStream();
		USBLink = USB.waitForConnection();
		dataOut = USBLink.openDataOutputStream();
		dataIn = USBLink.openDataInputStream();
	}
	
	public static DataInputStream getDataInputStream(){
		return(dataIn);
	}
	
	public static DataOutputStream getDataOutputStream(){
		return(dataOut);
	}
	
	public static void disconnect(){
		try{
			dataOut.close();
			USBLink.close();
		}
		catch (IOException IOe){
			System.out.println("Error closing connection");
		}
		System.out.println("Conection Closed");
	}
}

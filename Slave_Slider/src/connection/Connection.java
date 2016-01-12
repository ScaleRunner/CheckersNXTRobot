package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.NXTConnection;
import lejos.nxt.comm.USB;
import lejos.nxt.comm.USBConnection;

public class Connection {
	private static DataOutputStream dataOut;
	private static DataInputStream dataIn;
	private static DataInputStream BTdataIn;
	private static DataOutputStream BTdataOut;
	private static USBConnection USBLink;
	private static NXTConnection BTLink;
	
	public Connection(){
		LCD.drawString("Waiting...", 0, 0);
	    
		// Wait for USB connection
		USBLink = USB.waitForConnection();
		dataOut = USBLink.openDataOutputStream();
		dataIn = USBLink.openDataInputStream();
		
		LCD.drawString("USB connected", 0, 0);
		
//		// Make connection with the other NXJ
//		RemoteDevice remoteDevice = Bluetooth.getKnownDevice("Gripper");
//		BTLink = Bluetooth.connect(remoteDevice);
//	    BTdataOut = BTLink.openDataOutputStream();
//	    BTdataIn = BTLink.openDataInputStream();
//	    
//	    LCD.drawString("BT connected", 0, 1);
	}
	
	public DataInputStream getBTDataInputStream(){
		return BTdataIn;
	}
	
	public DataOutputStream getBTDataOutputStream(){
		return BTdataOut;
	}
	
	public DataInputStream getDataInputStream(){
		return(dataIn);
	}
	
	public DataOutputStream getDataOutputStream(){
		return(dataOut);
	}
	
	public void disconnect(){
		try{
			BTdataOut.close();
			BTLink.close();
			
			dataOut.close();
			USBLink.close();
		}
		catch (IOException IOe){
			System.out.println("Error closing connection");
		}
		System.out.println("Conection Closed");
	}
}

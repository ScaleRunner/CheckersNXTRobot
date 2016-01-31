package connection;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import lejos.nxt.LCD;
import lejos.nxt.comm.BTConnection;
import lejos.nxt.comm.Bluetooth;

public class Connection {
//	private static USBConnection USBLink = USB.waitForConnection();
	private static BTConnection BTLink = Bluetooth.waitForConnection();
	
//	private static DataOutputStream dataOut = USBLink.openDataOutputStream();
//	private static DataInputStream dataIn = new DataInputStream(new BufferedInputStream(USBLink.openDataInputStream(),1024));
	private static DataInputStream dataIn = BTLink.openDataInputStream();
	private static DataOutputStream dataOut = BTLink.openDataOutputStream();
	
	public Connection(){
		LCD.drawString("USB connected", 0, 0);
	}
	
	public DataInputStream getDataInputStream(){
		return(dataIn);
	}
	
	public DataOutputStream getDataOutputStream(){
		return(dataOut);
	}
	
	public void disconnect(){
		try{
			BTLink.close();
			dataIn.close();
			dataOut.close();
//			USBLink.close();
		}
		catch (IOException IOe){
			System.out.println("Error closing connection");
		}
		System.out.println("Conection Closed");
	}
}

/**
 * 
 */
package main;

import connection.Connection;
import connection.Listener;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;
import movement.Gripper;

/**
 * @author Dennis Verheijden
 *
 */
public class NXT_Gripper {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connection = new Connection();
		
		//Making the listener thread
		Listener listener = new Listener(connection);
		System.out.println("Listener created");
		
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
		
		NXTRegulatedMotor motor = new NXTRegulatedMotor(MotorPort.A);
		Gripper gripper = new Gripper(motor);
		
		gripper.pickUp();
		gripper.putDown();
		
//		while(!Button.ESCAPE.isDown()){
//			Command command = listener.getCommand();
//			if(!(command == null)){
//				System.out.println(command.toString());
//			}
//			Delay.msDelay(2000);
//			listener.listen();
//		}
		Delay.msDelay(1000);
		connection.disconnect();
	}

}

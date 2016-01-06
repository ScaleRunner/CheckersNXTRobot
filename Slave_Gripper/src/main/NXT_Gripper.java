/**
 * 
 */
package main;

import connection.Command;
import connection.Connection;
import connection.Listener;
import lejos.nxt.Button;
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
		
		while(!Button.ESCAPE.isDown()){
			Command command = listener.getCommand();
			if(!(command == null)){
				System.out.println(command.toString());
				gripper.pickUp();
				gripper.putDown();
			}
			listener.listen();
		}
		Delay.msDelay(1000);
		connection.disconnect();
	}

}

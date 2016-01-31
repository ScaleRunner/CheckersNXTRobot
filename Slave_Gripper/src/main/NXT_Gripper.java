/**
 * 
 */
package main;

import connection.Command;
import connection.Connection;
import connection.Listener;
import connection.Sender;
import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import movement.Gripper;

/**
 * @author Dennis Verheijden
 *
 */
public class NXT_Gripper {
	public static Connection connection = new Connection();
	public static Listener listener = new Listener(connection);
	public static Sender sender = new Sender(connection);
	
	public static NXTRegulatedMotor motor = new NXTRegulatedMotor(MotorPort.A);
	public static Gripper gripper = new Gripper(motor);
	
	public static void main(String[] args) {
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
		
		while(!Button.ESCAPE.isDown()){
			Command command = listener.getCommand();
			if(!(command == null)){
				System.out.println(command.toString());
				if(command.getCommand()==2){
					gripper.pickUp();
				}
				else{
					gripper.putDown();
				}
				System.out.println("Sending...");
				sender.sendDone();
				System.out.println("Sent");
			}
			listener.listen();
		}
		connection.disconnect();
	}
}

/**
 * 
 */
package main;

import java.awt.Point;

import connection.Command;
import connection.Connection;
import connection.Listener;
import connection.Sender;
import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import movement.Motor;

/**
 * @author Dennis Verheijden
 *
 */
public class NXT_Slider {
	private static Connection connection = new Connection();
	private static Listener listener = new Listener(connection);
	private static Sender sender = new Sender(connection);
	
	private static NXTRegulatedMotor xMotor1 = new NXTRegulatedMotor(MotorPort.A);
	private static NXTRegulatedMotor xMotor2 = new NXTRegulatedMotor(MotorPort.B);
	private static NXTRegulatedMotor yMotor = new NXTRegulatedMotor(MotorPort.C);
	private static Motor motors = new Motor(xMotor1,xMotor2,yMotor);
	
	public static void main(String[] args) {		
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
		
//		Boolean firstCommand = true;
		while(!Button.ESCAPE.isDown()){
			Command command = listener.getCommand();
			if(!(command == null)){
				System.out.println(command.toString());
				motors.moveTo(new Point(command.getPos().x-1, command.getPos().y-1));
				System.out.println("Sending...");
				sender.sendDone();
				System.out.println("Sent");
			}
			listener.listen();
		}
		motors.moveTo(new Point(6,6));
		connection.disconnect();
	}
}

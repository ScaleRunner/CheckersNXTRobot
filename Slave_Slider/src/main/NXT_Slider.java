/**
 * 
 */
package main;

import connection.Command;
import connection.Connection;
import connection.Listener;
import connection.Sender;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import movement.Motor;

/**
 * @author Dennis Verheijden
 *
 */
public class NXT_Slider {
	private static NXTRegulatedMotor xMotor1 = new NXTRegulatedMotor(MotorPort.A);
	private static NXTRegulatedMotor xMotor2 = new NXTRegulatedMotor(MotorPort.B);
	private static NXTRegulatedMotor yMotor = new NXTRegulatedMotor(MotorPort.C);
	private static Connection connection = new Connection();
	private static Listener listener = new Listener(connection);
	private static Sender sender = new Sender(connection);
	private static Motor motors = new Motor(xMotor1,xMotor2,yMotor);
	
	public static void main(String[] args) {		
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
		LCD.clear();
		
		Boolean firstCommand = true;
		while(!Button.ESCAPE.isDown()){
			Command command = listener.getCommand();
			if(!(command == null)){
				if(firstCommand){
					motors.setStartPos(command.getPos());
					firstCommand = false;
					System.out.println("Starpositie = " + command.getPos().toString());
				} 
				else {
					System.out.println(command.toString());
					motors.moveTo(command.getPos());
				}
				sender.sendDone();
				System.out.println("Done");
			}
			listener.listen();
		}
		connection.disconnect();
	}
}

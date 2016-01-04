/**
 * 
 */
package main;

import java.awt.Point;

import connection.Command;
import connection.Connection;
import connection.Listener;
import lejos.nxt.Button;
import lejos.nxt.MotorPort;
import lejos.nxt.NXTRegulatedMotor;
import lejos.util.Delay;
import movement.Motor;

/**
 * @author Dennis Verheijden
 *
 */
public class NXT_Slider {
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Connection connection = new Connection();
		
		//Making the listener thread
		Listener listener = new Listener(connection);
		System.out.println("Listener created");
		NXTRegulatedMotor xMotor1 = new NXTRegulatedMotor(MotorPort.A);
		NXTRegulatedMotor xMotor2 = new NXTRegulatedMotor(MotorPort.B);
		NXTRegulatedMotor yMotor = new NXTRegulatedMotor(MotorPort.C);
		
		Motor motors = new Motor(xMotor1,xMotor2,yMotor);
		
		Point point = new Point(0,0);
		motors.moveTo(point);
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
		
		while(!Button.ESCAPE.isDown()){
			Command command = listener.getCommand();
			if(!(command == null)){
				System.out.println(command.toString());
			}
			Delay.msDelay(2000);
			listener.listen();
		}
		Delay.msDelay(1000);
		connection.disconnect();
	}

}

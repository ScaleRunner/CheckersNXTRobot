/**
 * 
 */
package main;

import connection.Connection;
import connection.Listener;
import lejos.nxt.Button;
import lejos.nxt.LCD;
import lejos.util.Delay;

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
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		
		
		while(!Button.ESCAPE.isDown()){
			int command = listener.getCommand();
			System.out.println("command = " + command);
			Delay.msDelay(1000);
		}
		connection.disconnect();
	}

}

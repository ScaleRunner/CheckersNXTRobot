/**
 * 
 */
package main;

import connection.Command;
import connection.Connection;
import connection.Listener;
import lejos.nxt.Button;
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
		System.out.println("Listener created");
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

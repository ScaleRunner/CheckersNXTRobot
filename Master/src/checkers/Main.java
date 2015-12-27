package checkers;

import java.awt.Point;

import communication.Command;
import communication.Connection;
import communication.Sender;
import lejos.util.Delay;

public class Main {

	public static void main(String[] args) {
		Connection connection = new Connection();
		Sender sender = new Sender(connection);
		
		Command command1 = new Command(1, new Point(5,5));
		Command command2 = new Command(2, new Point(1,2));
		sender.sendCommand(command1);
		sender.sendCommand(command2);
		
		Delay.msDelay(1000);
		connection.disconnect();
		//Game g = new Game();
		//g.play();	
	}
}

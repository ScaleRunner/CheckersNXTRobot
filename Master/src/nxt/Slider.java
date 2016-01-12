package nxt;

import java.io.IOException;

import communication.Command;
import communication.Connection;
import communication.Listener;
import communication.Sender;
import lejos.pc.comm.NXTInfo;

public class Slider implements NXT{
	private Connection connection;
	private Sender sender;
	private Listener listener;
	
	public Slider(NXTInfo slider){
		connection = new Connection(slider);
		sender = new Sender(connection);
		listener = new Listener(connection);
		//Make the thread a "slave"
		listener.setDaemon(true);
		listener.start();
		listener.listen();
	}

	@Override
	public void sendCommand(Command command) {
		sender.sendCommand(command);
		waitForDone();
	}

	@Override
	public void disconnect() {
		connection.disconnect();
	}

	@Override
	public void waitForDone() {
		Boolean done = false;
		System.out.println("Checking for done");
		while(!done){
			done = listener.done();
		}
		System.out.println("Done");
	}	
}

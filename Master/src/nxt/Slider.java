package nxt;

import java.util.concurrent.TimeUnit;

import communication.Command;
import communication.Connection;
import communication.Listener;
import communication.Sender;
import communication.Timer;
import lejos.pc.comm.NXTInfo;

public class Slider implements NXT{
	private Connection connection;
	private Sender sender;
	private Listener listener;
	private Command command;
	private Timer timer = Timer.start();
	
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
		this.command = command;
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
		timer.reset();
		while(!done){
			done = listener.done();
			if(!done && (timer.time() > 10)){
				done = true;
				System.out.println("RESEND: " + this.command.toString());
				sendCommand(this.command);
			}
		}
//		System.out.println("Done");
	}
}

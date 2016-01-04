package checkers;

import java.awt.Point;
import java.util.Scanner;

import javax.swing.plaf.synth.SynthSpinnerUI;

import communication.Command;
import communication.Connection;
import communication.Sender;
import lejos.pc.comm.NXTInfo;
import lejos.util.Delay;

public class Main {

	public static void main(String[] args) {
		NXTInfo slider = new NXTInfo(1, "Slider", "0016531AFE2A");
		NXTInfo gripper = new NXTInfo(1, "Gripper", "00165305B61A");
		
//		Connection connectionSlider = new Connection(slider);
//		Sender senderSlider = new Sender(connectionSlider);
		Connection connectionGripper = new Connection(gripper);
		Sender senderGriper = new Sender(connectionGripper);
		
//		Scanner scanner = new Scanner(System.in);
//		
//		int i = 0;
//		while(i <= 5){
//			int action = scanner.nextInt();
//			int x = scanner.nextInt();
//			int y = scanner.nextInt();
//			
//			Command command = new Command(action, new Point(x,y));
//			senderSlider.sendCommand(command);
//			i++;
//			System.out.println("i = " + i);
//		}
//		scanner.close();
//		System.out.println("scanner closed");
//		
//		connectionSlider.disconnect();
		connectionGripper.disconnect();
		//Game g = new Game();
		//g.play();	
	}
}

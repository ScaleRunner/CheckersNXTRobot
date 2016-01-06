package checkers;

import java.awt.Point;
import java.util.Scanner;

import communication.Command;
import lejos.pc.comm.NXTInfo;
import nxt.Gripper;
import nxt.Slider;

public class Main {

	public static void main(String[] args) {
		NXTInfo slider = new NXTInfo(1, "Slider", "0016531AFE2A");
		NXTInfo gripper = new NXTInfo(1, "Gripper", "00165305B61A");

		Slider sliderNXT = new Slider(slider);
		Gripper gripperNXT = new Gripper(gripper);
		
		Scanner scanner = new Scanner(System.in);
		
		int i = 0;
		while(i <= 5){
			int action = scanner.nextInt();
			int x = scanner.nextInt();
			int y = scanner.nextInt();
			
			Command command = new Command(action, new Point(x,y));
			System.out.println("Action = " + action);
			if(action == 1){
				sliderNXT.sendCommand(command);
			}
			if(action == 2){
				gripperNXT.sendCommand(command);
			}
			i++;
			System.out.println("i = " + i);
			Boolean done = false;
//			while(!done){
//				
//				listenerSlider.listen();
//				done = listenerSlider.done();
//			}
			System.out.println("Finished");
		}
		scanner.close();
		System.out.println("scanner closed");
		
		sliderNXT.disconnect();
		gripperNXT.disconnect();
		
		//Game g = new Game();
		//g.play();	
	}
}

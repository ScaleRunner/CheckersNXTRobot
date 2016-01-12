package checkers;

import java.awt.Point;
import java.io.DataOutputStream;
import java.util.Scanner;

import communication.Command;
import communication.Sender;
import lejos.nxt.remote.NXTCommand;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTCommandConnector;
import lejos.pc.comm.NXTConnector;
import lejos.pc.comm.NXTInfo;
import lejos.util.Delay;
import nxt.Gripper;
import nxt.Slider;

/*
 * 1 moveto
 * 2 omhoog
 * 3 omlaag
 * 4 terminate
 */

public class Main {
	private static NXTInfo slider = new NXTInfo(NXTCommFactory.USB, "Slider", "0016531AFE2A");
	private static NXTInfo gripper = new NXTInfo(NXTCommFactory.BLUETOOTH, "Gripper", "00165305B61A");
	private static Slider sliderNXT = new Slider(slider);
	private static Gripper gripperNXT = new Gripper(gripper);
	
	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		int i = 0;
		int action = 0;
		int x;
		int y;
		
		while(!(action == 4)){
			System.out.println("Enter Command:");
			action = scanner.nextInt();
			x = scanner.nextInt();
			y = scanner.nextInt();
			
//			Command command = new Command(action, new Point(x,y));
			Point pos = new Point(x,y);
			if(action == 0){
				//Set pos
				sliderNXT.sendCommand(new Command(1, pos));
			}
			if(action == 1){
				pickUp(pos);
			}
			if(action == 2){
				putDown(pos);
			}
		}
		scanner.close();
		System.out.println("scanner closed");
		
		sliderNXT.disconnect();
		gripperNXT.disconnect();
		
//		Game g = new Game();
//		g.play();	
	}
	
	public static void pickUp(Point pos){
		sliderNXT.sendCommand(new Command(1, new Point(pos.x-1,pos.y)));
		gripperNXT.sendCommand(new Command(3, pos));
		sliderNXT.sendCommand(new Command(1, pos));
		gripperNXT.sendCommand(new Command(2, pos));
	}
	
	public static void putDown(Point pos){
		sliderNXT.sendCommand(new Command(1, pos));
		gripperNXT.sendCommand(new Command(3, pos));
		sliderNXT.sendCommand(new Command(1, new Point(pos.x-1,pos.y)));
		gripperNXT.sendCommand(new Command(2, pos));
	}
}

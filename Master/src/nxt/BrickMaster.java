package nxt;

import java.awt.Point;
import java.util.Scanner;

import checkers.Move;
import communication.Command;
import lejos.pc.comm.NXTCommFactory;
import lejos.pc.comm.NXTInfo;
import lejos.util.Delay;

public class BrickMaster {
	private static NXTInfo slider = new NXTInfo(NXTCommFactory.BLUETOOTH, "Slider", "0016531AFE2A");
	private static NXTInfo gripper = new NXTInfo(NXTCommFactory.BLUETOOTH, "Gripper", "00165305B61A");
	private static Slider sliderNXT = new Slider(slider);
	private static Gripper gripperNXT = new Gripper(gripper);
	
	public BrickMaster(){
		
	}
	
	public static void manualCommands(){
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
	}
	
	public static void doMove(Move move){
		Point pos1 = new Point(move.r1,move.c1);
		pickUp(pos1);
		Point pos2 = new Point(move.r2,move.c2);
		putDown(pos2);
		if(Math.abs(move.r1 - move.r2) > 1){
			int newR = (move.r1 + move.r2)/2;
			int newC = (move.c1 + move.c2)/2;
			Point hitStone = new Point(newR,newC);
			pickUp(hitStone);
			putDown(new Point(newR,-1));
		}
	}
	
	private static void pickUp(Point pos){
		sliderNXT.sendCommand(new Command(1, new Point(pos.x-1,pos.y)));
		gripperNXT.sendCommand(new Command(3, pos));
		sliderNXT.sendCommand(new Command(1, pos));
		gripperNXT.sendCommand(new Command(2, pos));
	}
	
	private static void putDown(Point pos){
		sliderNXT.sendCommand(new Command(1, pos));
		gripperNXT.sendCommand(new Command(3, pos));
		sliderNXT.sendCommand(new Command(1, new Point(pos.x-1,pos.y)));
		gripperNXT.sendCommand(new Command(2, pos));
	}
	
	public static void sync(){
		gripperNXT.sendCommand(new Command(3, new Point(0,0)));
		for(int i = 5; i>=0; i--){
			sliderNXT.sendCommand(new Command(1, new Point(3,i)));
			Delay.msDelay(5000);
		}
		gripperNXT.sendCommand(new Command(2, new Point(0,0)));
		sliderNXT.sendCommand(new Command(1, new Point(5,5)));
	}
	
	private static void disconnect(){
		sliderNXT.disconnect();
		gripperNXT.disconnect();
	}
	
}

package movement;

import lejos.nxt.NXTRegulatedMotor;

public class Gripper {
	
	private NXTRegulatedMotor motor;
	
	public Gripper(NXTRegulatedMotor motor){
		this.motor = motor;
		this.motor.setSpeed(50);
	}
	
	public void pickUp(){
		motor.rotate(360);
	}
	
	public void putDown(){
		motor.rotate(-360);
	}
}

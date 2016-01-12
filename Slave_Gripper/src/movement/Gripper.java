package movement;

import lejos.nxt.NXTRegulatedMotor;

public class Gripper {
	
	private NXTRegulatedMotor motor;
	
	public Gripper(NXTRegulatedMotor motor){
		this.motor = motor;
		this.motor.setSpeed(200);
	}
	
	public void pickUp(){
		motor.rotate(660);
	}
	
	public void putDown(){
		motor.rotate(-660);
	}
}

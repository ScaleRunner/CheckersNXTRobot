package movement;

import lejos.nxt.NXTRegulatedMotor;

public class Gripper {
	
	private NXTRegulatedMotor motor;
	
	public Gripper(NXTRegulatedMotor motor){
		this.motor = motor;
		this.motor.setSpeed(400);
	}
	
	public void pickUp(){
		motor.rotate(725);
	}
	
	public void putDown(){
		motor.rotate(-725);
	}
}

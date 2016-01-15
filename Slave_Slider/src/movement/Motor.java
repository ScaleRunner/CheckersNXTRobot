package movement;

import java.awt.Point;

import lejos.nxt.NXTRegulatedMotor;

public class Motor {
	
	/**
	 * 
	 */
	
	private NXTRegulatedMotor xMotor1;
	private NXTRegulatedMotor xMotor2;
	private NXTRegulatedMotor yMotor;
	
	protected int squareSize = 4;
	private static Point currentPos = new Point(6,6);
	private int motorSpeed = 250;
	
	public Motor (NXTRegulatedMotor x1, NXTRegulatedMotor x2, NXTRegulatedMotor y){
		this.xMotor1 = x1;
		this.xMotor2 = x2;
		this.yMotor = y;
		setSpeed(motorSpeed);
	}
	
	public void setSpeed(int speed){
		this.xMotor1.setSpeed(speed);
		this.xMotor2.setSpeed(speed);
		this.yMotor.setSpeed(speed);
	}
	
	public void moveTo(Point pos){
		int xDistance = currentPos.x - pos.x;
		int yDistance = currentPos.y - pos.y;
		
		int xAngle = 465 * xDistance;
		int yAngle = 712 * yDistance;
		xMotor1.rotate(xAngle, true);
		if(Math.abs(yDistance) >= Math.abs(xDistance) && pos.x>0 && pos.y>0)
			xMotor2.rotate(xAngle, true); else
				xMotor2.rotate(xAngle);
		yMotor.rotate(yAngle);
		
		currentPos = pos;
	}
	
	public void setStartPos(Point pos){
		currentPos = pos;
	}
	
	public Point getStartPos(){
		return currentPos;
	}
}

package communication;

import java.awt.Point;

public class Command {
	private int command;
	private Point pos;
	
	public Command(){
		
	}
	
	public Command(int i, Point point){
		this.command = i;
		this.pos = point;
	}
	
	public void setCommand(int i){
		command = i;
	}
	
	public int getCommand(){
		return command;
	}
	
	public void setPos(Point newPos){
		pos = newPos;
	}
	
	public Point getPos(){
		return pos;
	}

	@Override
	public String toString() {
		return "Command [command=" + command + ", pos=" + pos + "]";
	}
}

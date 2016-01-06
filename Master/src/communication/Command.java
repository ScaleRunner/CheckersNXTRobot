package communication;

import java.awt.Point;

/**
 * Command List:
 * 1: Go To Position
 * 2: Prepare To Pick Up Block
 * 3: Pick Up Block
 * 4: Cleanup After Picking Up Block
 * 5: Put Down Block
 * 6: Disconnect and shut down
 */

public class Command {	
	
	private int command;
	private Point pos;
	
	public Command(int action){
		this.command = action;
	}
	
	public Command(int action, Point point){
		this.command = action;
		this.pos = point;
	}
	
	public void setCommand(int action){
		command = action;
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

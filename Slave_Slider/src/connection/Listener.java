package connection;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Listener extends Thread{
	private boolean io = false;
	private static DataInputStream dataIn;
	private static ArrayList<Command> commandList;
	
	public Listener(Connection connection){
		dataIn = connection.getDataInputStream();
		commandList = new ArrayList<Command>();
	}
	
	public void listen(){
		io = true;
	}
	
	public int getInt(){
		try{
			if(dataIn.available()>0){
				int i = dataIn.readInt();
				return i;
			}
		} catch(IOException ioException){
			System.out.println("Could not read Int");
		}
		return -1;
	}
	
	public Command getCommand(){
		if(commandList.size()>0){
			Command command = commandList.get(0);
			commandList.remove(0);
			return(command);
		} else{
			System.out.println("No commands available");
			return null;
		}
	}
	
	public void run(){
		while(true){
			if(io){
				int toDo = getInt();
				Point point = new Point(getInt(),getInt());
				if(toDo > 0 && point.x > 0 && point.y > 0){
					Command command = new Command(toDo,point);
					commandList.add(command);
				}
				else{
					io = false;
				}
			}
		}
	}
}

package communication;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

public class Listener extends Thread{
	private boolean io = false;
	private DataInputStream dataIn;
	private ArrayList<Integer> commandList;
	
	public Listener(Connection connection){
		dataIn = connection.getDataInputStream();
		commandList = new ArrayList<Integer>();
	}
	
	public void listen(){
		io = true;
	}
	
	public boolean done(){
		if (commandList.size() > 0){
			int i = commandList.get(0);
			commandList.remove(0);
			System.out.println("Ik heb iets binnen gekregen");
			return true;
		}
		return false;
	}
	
	private int getInt(){
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
	
	public void run(){
		while(true){
			if(io){
				int i = getInt();
				if(i > 0){
					System.out.println(i);
					commandList.add(i);
				}
				else{
					io = false;
				}
			}
		}
	}
}
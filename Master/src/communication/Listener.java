package communication;

import java.awt.Point;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.ArrayList;

import lejos.util.Delay;

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
			commandList.remove(0);
			return true;
		}
		return false;
	}
	
	private int getInt(){
		int i = -1;
		try{
			i = dataIn.read();
			return i;
		} catch(IOException ioException){
			System.out.println("Could not read Int");
		}
		return i;
	}
	
	public void run(){
		while(true){
			if(io){
				int i = getInt();
				if(i > 0){
					System.out.println(i);
					commandList.add(i);
				}
			}
		}
	}
}
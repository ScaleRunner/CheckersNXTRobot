package checkers;

import gui.ShowBoard;
import lejos.util.Delay;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Game extends Thread{
	private boolean running = true;
	//TODO p1 is now Human and white, p2 computer and black
	private Player p1;
	private int moveNr = 0;
	private Player p2;
	private Board board;
	private ShowBoard showBoard;
	private ArrayList<Move> toDoMoves = new ArrayList<Move>();

	public Game (){
		this (new PlayerHuman(), new PlayerAI(8));
	}
	
	public Game (Player p1, Player p2){
		this (new Board (6), p1, p2);
	}
	
	public Game (Board b, Player p1, Player p2){
		//Player 1 = White
		this.p1 = p1;
		//Player 2 = Black
		this.p2 = p2;
		this.board = b;
		//give both players the board to play with.
		p1.setBoard(b);
		p2.setBoard(b);
		
		showBoard = new ShowBoard (board); //init jpanel of board. Stil needs implementation in GUI possible push back to main and add in Main Window there.
		
		//cast back to Human player and add mouse listener and showBoard. MouseListener is used to see where the user is clicking. 
		// ShowBoard is used to get information on how big the window is.
		if (p1.getClass().getName() == PlayerHuman.class.getName()){
			showBoard.addMouseListener((PlayerHuman) p1);
			((PlayerHuman) p1).setShowBoard(showBoard);
		}
		if (p2.getClass().getName() == PlayerHuman.class.getName()){
			showBoard.addMouseListener((PlayerHuman) p2);
			((PlayerHuman) p2).setShowBoard(showBoard);
		}
		
		//temp:
		JFrame test = new JFrame ("test");
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		test.add(showBoard);
		test.setSize(500, 500);
		test.setVisible(true);
	}
	
//	//start a new thread.
//	public void play(){
//		Thread t = new Thread(this);
//		t.run();
//	}
	
	public Move getMove(){
		Move move = null;
		if(toDoMoves.size()>0){
			move = toDoMoves.get(0);
			toDoMoves.remove(0);
//			System.out.println("GOT MOVE: " + move.toString());
			moveNr++;
		}
		return move;
	}
	
	public int availableMoves(){
		return toDoMoves.size();
	}
	
	public void pause(){
		running = false;
	}
	
	public void continueGame(){
		running = true;
	}
	
	public boolean isRunning(){
		return running;
	}
	
	//TODO: implement Draw state
	public void run() {
		Move move;
		boolean anyHits;
		while(true){
			while (!gameOver() && running){
				board.setWhitePlaying(true);
				do {
					move = p1.getMove(this.board);
					if(move == null){
						System.out.println("Game Over! Black won!");
						return;
					}
					else{
//						toDoMoves.add(move);
						board.doMove(move);
						showBoard.cleanAfterMove();
//						System.out.println("MOVE ADDED: " + move.toString());
						anyHits = board.possibleHit(board.getLegalMoves(move.r2, move.c2));
					}
				} while(move.hit && anyHits);
				board.setWhitePlaying(false);
				do {
					try{
						move = p2.getMove2(this.board);
					} catch(NullPointerException nullPointer){
						System.out.println("Crashhhhhh");
						move = null;
					}
					if(move == null){
						System.out.println("Game Over! White won!");
						return;
					}
					else{
						toDoMoves.add(move);
						board.doMove(move);
						showBoard.cleanAfterMove();
						//Wait for move
						System.out.println("MOVE ADDED: " + move.toString());
						anyHits = board.possibleHit(board.getLegalMoves(move.r2, move.c2));
						if(!toDoMoves.contains(move))
							toDoMoves.add(move);
					}
				} while(move.hit && anyHits);
				pause();
			}
		}
	}
		
	public boolean gameOver(){
		return(board.isWin("white") || board.isWin("black"));
	}	
}

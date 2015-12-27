package checkers;

import gui.ShowBoard;

import java.util.ArrayList;

import javax.swing.JFrame;

public class Game implements Runnable{
	private boolean running = true;
	//TODO p1 is now Human and white, p2 computer and black
	private Player p1;
	private Player p2;
	private Board board;
	private ShowBoard showBoard;

	public Game (){
		this (new PlayerHuman(), new PlayerAI(3));
	}
	
	public Game (Player p1, Player p2){
		this (new Board (10), p1, p2);
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
	
	//start a new thread.
	public void play(){
		Thread t = new Thread(this);
		t.run();
	}
	
	public void stopPlaying(){
		running = false;
	}
	
	//TODO: implement Draw state
	@Override
	public void run() {
		Move move;
		boolean anyHits;
		while (!gameOver(board) && running){
			System.out.println("Black pieces :" + board.blackPieces());
			board.setWhitePlaying(true);
			do {
				move = p1.getMove(this.board);
				board.doMove(move);
				showBoard.cleanAfterMove();
				anyHits = board.possibleHit(board.getLegalMoves(move.r2, move.c2));
			} while(move.hit && anyHits);
			System.out.println("Black pieces: " + board.blackPieces());
			board.setWhitePlaying(false);
			do {
				move = p2.getMove(this.board);
				board.doMove(move);
				showBoard.cleanAfterMove();
				anyHits = board.possibleHit(board.getLegalMoves(move.r2, move.c2));
			} while(move.hit && anyHits);
		}
	}
		
	private boolean gameOver(Board board){
		return(board.isWin("white") || board.isWin("black"));
	}	
}

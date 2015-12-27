package checkers;

import java.awt.Cursor;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;

import gui.ShowBoard;

public class PlayerHuman implements Player, MouseListener{
	private ShowBoard sb;
	private Board b;
	private int selectX;
	private int selectY;
	private boolean collect = false; //flag for collecting the move by getMove()
	private Move currentMove;
	private boolean isWhite = false;
	
	
	public PlayerHuman (){
		
	}
	
	public boolean isWin(Board board){
		if(this.isWhite)
			return(board.blackPieces() == 0);
		else
			return(board.whitePieces() == 0);
	}

	public boolean isLose(Board board){
		if(this.isWhite)
			return(board.whitePieces() == 0);
		else
			return(board.blackPieces() == 0);
	}
	
	// waits for collect Flag == true. then returns currentMove. (see mouseReleased())
	@Override
	public Move getMove(Board b) {
		isWhite = b.isWhitePlaying();
		System.out.println("Waiting for player " + (b.isWhitePlaying()? "1" : "2"));
		while (!collect){
			try {
				Thread.sleep(300);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("Player " + (b.isWhitePlaying()? "1" : "2") + " has selected " + currentMove);
		collect = false;
		return currentMove;
	}

	@Override
	public void mouseClicked(MouseEvent arg0) {}

	@Override
	public void mouseEntered(MouseEvent arg0) {
		sb.setCursor(new Cursor (Cursor.HAND_CURSOR));
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		sb.setCursor(new Cursor (Cursor.DEFAULT_CURSOR));
	}

	@Override
	public void mousePressed(MouseEvent arg0) {
		if (isWhite == b.isWhitePlaying()){
			sb.setCursor(new Cursor (Cursor.MOVE_CURSOR));
			selectX = (arg0.getX()-sb.getBorderSize())/sb.getSquareSize();
			selectY = (arg0.getY()-sb.getBorderSize())/sb.getSquareSize();
		}
	}

	
	// checks if mouse release is in same field as press. if so, send selected value back to ShowBoard in order to display red sides on field.
	// if not, check if this is a valid move. if so: Save move to currentMove and set Collect flag true. The game thread will collect the value and return it. 
	@Override
	public void mouseReleased(MouseEvent arg0) {
		if (isWhite == b.isWhitePlaying()){ // prevent two human players listening to board.
			sb.setCursor(new Cursor (Cursor.HAND_CURSOR));
			int releaseSelectX = (arg0.getX()-sb.getBorderSize())/sb.getSquareSize();
			int releaseSelectY = (arg0.getY()-sb.getBorderSize())/sb.getSquareSize();

			if (releaseSelectX == selectX && releaseSelectY == selectY){	// player wants to check the valid moves of this piece
				ArrayList<Move> legalMoves = b.getAllLegalMoves();
				legalMoves.retainAll(b.getLegalMoves(selectY, selectX));
				sb.setSelectList(legalMoves);
			} else {
				ArrayList<Move> legalMoves = b.getAllLegalMoves();
				boolean found = false;
				int i=0;
				for(; i<legalMoves.size() && !found; i++){
					Move m = legalMoves.get(i);
					found = m.r1 == selectY && m.c1 == selectX && m.r2 == releaseSelectY && m.c2 == releaseSelectX;
				}
				if (found){
					currentMove = legalMoves.get(--i);
					collect = true;
				}
			}
		}
	}

	public void setShowBoard(ShowBoard showBoard) {
		sb = showBoard;
	}

	public void setBoard (Board b){
		this.b = b;
	}
}

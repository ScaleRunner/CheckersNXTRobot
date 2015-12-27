/**
 * 
 */
package checkers;
import java.util.ArrayList;

/**
 * @author Dennis
 *
 */
public class Board{	
	private Cell[][] board;
	private final int size;
	private boolean whitePlaying;
	private int whitePieces = 0;
	private int blackPieces = 0;
	
	public Board(final int squareSize){
		this.size = squareSize;
		InitializeBoard();
	}
	
	public Board deepCopy(){
		Board newBoard = new Board(this.getSize());
		
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				newBoard.setValue(i, j, this.getValue(i, j));
			}
		}
		return newBoard;
	}
	
	public void setWhitePlaying(boolean bool){
		whitePlaying = bool;
	}
	
	private void InitializeBoard(){
		whitePlaying = true;
		board = new Cell[this.size][this.size];
		
		for(int r = 0; r < this.size; r++){
			if(r < this.size/2-1 || this.size/2 < r){ // for dynamic board sizes: middle two lanes are kept open.
				for(int c = r%2; c < this.size; c += 2){
					board[r][c] = (r < this.size/2 ? Cell.WHITE : Cell.BLACK );
					this.whitePieces += 1; this.blackPieces += 1;
				}
				for(int c = (r+1)%2; c < this.size; c += 2){
					board[r][c] = Cell.EMPTY;
				}
			}
			else{
				for (int c= 0; c < this.size; c++){
					board[r][c] = Cell.EMPTY;
				}	
			}
		}
		this.blackPieces = this.blackPieces/2;
		this.whitePieces = this.whitePieces/2;
	}

	public int getSize(){
		return this.size;
	}
	
	public boolean isWhitePlaying() {
		return whitePlaying;
	}
	
	public int whitePieces(){
		return this.whitePieces;
	}
	
	public int blackPieces(){		
		return this.blackPieces;
	}
	
	private ArrayList<Move> filterCaptures (ArrayList<Move> moves){
		if (moves.isEmpty())
			return moves;
		
		int index = 0;
		
		//get all captures to front of list.
		for(int i=0; i<moves.size(); i++){
			if (moves.get(i).hit){
				if(index != i){
					Move temp = moves.get(index);
					moves.set(index, moves.get(i));
					moves.set(i, temp);
				}
				index++;
			}
		}
		
		// remove non capture if at least one capture move present
		if (moves.get(0).hit){
			moves.subList(index, moves.size()).clear();
		} 
		
		return moves;
	}
	

	public boolean possibleHit(ArrayList<Move> moves){
		for (int i = 0; i < moves.size(); i++){
			if (moves.get(i).hit){ return true;}
		}
		return false;
	}
	
	public ArrayList<Move> getAllLegalMoves() {
		ArrayList<Move> validMoves = new ArrayList<Move>();
		for(int i=0; i<this.size; i++){
			for(int j=0; j<this.size; j++){
				validMoves.addAll(this.getLegalMoves(i, j));
			}
		}
		validMoves = filterCaptures(validMoves);		
		return validMoves;
	}
	
	public ArrayList<Move> getLegalMoves(int r, int c){
		ArrayList<Move> moves = new ArrayList<Move>();
		int sign = (isWhitePlaying() ? 1 : -1); // calculate if player can move up or down in a normal move (not a capture).
		
		// check if this player can play this piece (if there is a piece in this place). otherwise return empty list.
		if (isWhitePlaying() &&getValue(r, c).isBlack() || !isWhitePlaying() && getValue(r, c).isWhite() || !getValue(r, c).isPiece()){	
			return moves;
		}
		
		// I could merge the King and the not-King situation. However, i found this easier to read.
		if (getValue(r, c).isKing()){
			for (int dRow=-1; dRow<=1; dRow+=2){
				for (int dCol=-1; dCol<=1; dCol+=2){
					// check as long you can actually move. Then check if capture is possible.
					int i=1;
					while (i<this.size*this.size && getValue(r+dRow*i, c+dCol*i) == Cell.EMPTY){
							moves.add(new Move(r, c, r+dRow*i, c+dCol*i, false));
							i++;
					}
					if (getValue(r, c).isOtherPiece(getValue(r+dRow*i, c+dCol*i)) && getValue(r+dRow*i +dRow, c+dCol*i +dCol) == Cell.EMPTY)
						moves.add(new Move(r, c, r+dRow*i +dRow, c+dCol*i +dCol, true));
				}
			}
		} else {
			// note that out of bounds is caught in the this.getValue(r, c) function.
			for (int dRow=-1; dRow<=1; dRow+=2){
				for (int dCol=-1; dCol<=1; dCol+=2){
					// check for capture opportunities.
					if (getValue(r, c).isOtherPiece(getValue(r+dRow, c+dCol)) && getValue(r+dRow*2, c+dCol*2) == Cell.EMPTY)
						moves.add(new Move(r, c, r+dRow*2, c+dCol*2, true));
					// check for normal moves. (only if dRow == sign
					else if(dRow == sign && getValue(r+dRow, c+dCol) == Cell.EMPTY){
						moves.add(new Move(r, c, r+dRow, c+dCol, false));
					}				
				}
			}
		}
		return moves;
	}

	//TODO: promotion should only occur when piece lands on Kings Row, not traveling through (this is now the case).
	public void doMove (Move m){
		if(m.hit){
			//Remove a stone from the game
			if(getValue(m.r1,m.c1) == Cell.BLACK || getValue(m.r1,m.c1) == Cell.BLACKC){
				this.whitePieces-=1;
			}
			else{
				this.blackPieces-=1;
			}
			
			if (m.r2 > m.r1) {
				if (m.c2 > m.c1)
					setValue(m.r2-1, m.c2-1, Cell.EMPTY);
				else
					setValue(m.r2-1, m.c2+1, Cell.EMPTY);
			} else {
				if (m.c2 > m.c1)
					setValue(m.r2+1, m.c2-1, Cell.EMPTY);
				else
					setValue(m.r2+1, m.c2+1, Cell.EMPTY);
			}
		}
		
		int kingsRow = (getValue(m.r1, m.c1).isBlack() ? 0 : this.size-1);
		if (m.r2 == kingsRow)
			setValue(m.r2, m.c2, Cell.promote(getValue(m.r1, m.c1)));
		else 
			setValue(m.r2, m.c2, getValue(m.r1, m.c1));
		setValue(m.r1, m.c1, Cell.EMPTY);
	}
	
	public Cell getValue(int r, int c){
		if (r < 0 || c < 0 || r >= this.size || c >= this.size){
			return Cell.ERR;
		}
		return board[r][c];
	}
	
	//
	private void setValue(int r, int c, Cell cell){
		if(r < 0 || c < 0 || r >= this.size || c >= this.size)
			System.out.println("setValue wrong coordinates: (" + r + "," + c + ")");
		else
			board[r][c] = cell;
	}
		
	public boolean isWin(String player){
		if(player == "white"){
			return (this.blackPieces() == 0);
		}
		else{
			return (this.whitePieces() == 0);
		}
	}
	
	public boolean isLose(String player){
		if(player == "white"){
			return (this.whitePieces() == 0);
		}
		else{
			return(this.blackPieces() == 0);
		}
	}
	
	@Override
	public String toString(){
		String temp = "";
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				temp += board[i][j] + " ";
			}
			temp += "\n";
		}
		return temp;
	}
	
}

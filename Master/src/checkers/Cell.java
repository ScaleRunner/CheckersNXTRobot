package checkers;

public enum Cell {
	ERR, EMPTY, WHITE, BLACK, WHITEC, BLACKC;
	
	public boolean isBlack (){
		return this == Cell.BLACK || this == Cell.BLACKC;
	}
	
	public boolean isWhite (){
		return this == Cell.WHITE || this == Cell.WHITEC;
	}
	
	public boolean isKing (){
		return this == Cell.BLACKC || this == Cell.WHITEC;
	}
	
	public boolean isPiece (){
		return this.isBlack() || this.isWhite();
	}
	
	// test for opposite team  (for capturing) 
	public boolean isOtherPiece (Cell c){
		if (c.isPiece() && this.isPiece()){
			return c.isBlack() != this.isBlack();
		}
		return false;
	}
	
	// nice example of static function:
	public static Cell promote (Cell cell){
		if (cell == Cell.BLACK)
			cell = Cell.BLACKC;
		if (cell == Cell.WHITE)
			cell = Cell.WHITEC;
		return cell;
	}
	
	public String toString(){
		switch(this){
		case EMPTY: return "  ";
		case WHITE: return "W ";
		case BLACK: return "B ";
		case WHITEC: return "WW";
		case BLACKC: return "BB";
		default : return "0 ";
		}
	}
}

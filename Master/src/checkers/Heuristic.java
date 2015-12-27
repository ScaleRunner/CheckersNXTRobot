/**
 * 
 */
package checkers;


/**
 * @author s4455770
 *
 */
public class Heuristic {
	private Board board;
	private int size;

	public Heuristic() {
		
	}

	private int pawns(){
		int count = 0;
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				if (board.getValue(i, j) == Cell.BLACK)
					count ++;
				if (board.getValue(i, j) == Cell.WHITE)
					count --;
			}
		}
		return count;
	}
	
	private int kings(){
		int count = 0;
		for (int i = 0; i < this.size; i++){
			for (int j = 0; j < this.size; j++){
				if (board.getValue(i, j) == Cell.BLACKC)
					count ++;
				if (board.getValue(i, j) == Cell.WHITE)
					count --;
			}
		}
		return count;
	}
	
	private int safePawns(){
		int count = 0;
		for (int i = 0; i < this.size; i ++){
			if ((board.getValue(i, 0) == Cell.BLACK) || (board.getValue(i, this.size -1) == Cell.BLACK) 
					||( board.getValue(0, i) == Cell.BLACK) ||(board.getValue(this.size - 1, i) == Cell.BLACK)){
				count ++;
			}
			if ((board.getValue(i, 0) == Cell.WHITE) || (board.getValue(i, this.size -1) == Cell.WHITE) 
					||( board.getValue(0, i) == Cell.WHITE) ||(board.getValue(this.size - 1, i) == Cell.WHITE)){
				count --;
			}
		}
		return count;
	}
	
	private int attackingPawns(){
		int count = 0;
		for (int i = 0; i < 4; i++){
			for (int j = 0; j < this.size; j++){
				if (board.getValue(i, j) == Cell.BLACK)
					count ++;
			}
		}
		return count;
	}
	
	//Returns the total heuristic value
	public int totalHeuristic(Board board){
		if(board.isWin("black")){
			return Integer.MAX_VALUE - 100;
		}
		if(board.isLose("black")){
			return Integer.MIN_VALUE + 100;
		}
		
		this.board = board;
		this.size = board.getSize();
		
		int heuristic = 0;
		heuristic = pawns() *2
					+ kings()*5
					+ safePawns()
					+ attackingPawns()
					//stoneCount()
					//+ hitCount()
					//+ crownCount() * 2	Time complexity off the damn Charts
					//+ sideStones()
					;
		return heuristic;
	}		
}

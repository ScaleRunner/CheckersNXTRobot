/**
 * 
 */
package checkers;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * @author s4455770
 *
 */
public class MoveValue {
	private int returnValue;
	private Move returnMove;
	private Heuristic heuristic = new Heuristic(); 
	private int maxDepth = 6;
	
	public MoveValue(){
		
	}
	
	public MoveValue(int maxDepth){
		this.maxDepth = maxDepth;
	}
	
	public MoveValue(int dummy, int returnValue){
		this.returnValue = returnValue;
	}
	
	public MoveValue(int returnValue, Move returnMove){
		this.returnValue = returnValue;
		this.returnMove = returnMove;
	}
	
	protected MoveValue alphabeta(Board board, int depth, int alpha, int beta, boolean maximizingPlayer){
		int value = 0;
		ArrayList<Move> moves = board.getAllLegalMoves();
		Iterator<Move> movesIterator = moves.iterator();
		if (depth == this.maxDepth || board.isLose("black") || board.isWin("black")){
			value = heuristic.totalHeuristic(board);
			return new MoveValue(0,value);
		}
		
		MoveValue returnMove = new MoveValue();
		MoveValue bestMove = new MoveValue();
		
		if(maximizingPlayer){
			while(movesIterator.hasNext()){
				Move currentMove = movesIterator.next();
				Board newBoard = board.deepCopy();
				newBoard.doMove(currentMove);
				returnMove = alphabeta(newBoard, depth + 1, alpha, beta, !maximizingPlayer);
				if(bestMove == null){
					bestMove = returnMove;
					bestMove.returnMove = currentMove;
				}
				else{
					if( bestMove.returnValue < returnMove.returnValue){
						bestMove = returnMove;
						bestMove.returnMove = currentMove;
					}
				}
				if(returnMove.returnValue > alpha){
					alpha = returnMove.returnValue;
					bestMove = returnMove;
				}
				if(beta <= alpha){
					bestMove.returnValue = beta;
					bestMove.returnMove = null;
					return bestMove; //PRUNE
				}
			}
			return bestMove;
		}
		else{
			while(movesIterator.hasNext()){
				Move currentMove = movesIterator.next();
				Board newBoard = board.deepCopy();
				newBoard.doMove(currentMove);
				returnMove = alphabeta(newBoard, depth + 1, alpha, beta, !maximizingPlayer);
				if(bestMove == null || bestMove.returnValue > returnMove.returnValue){
					bestMove = returnMove;
					bestMove.returnMove = currentMove;
				}
				if(returnMove.returnValue < beta){
					beta = returnMove.returnValue;
					bestMove = returnMove;
				}
				if(beta <= alpha){
					bestMove.returnValue = alpha;
					bestMove.returnMove = null;
					return bestMove; //PRUNE
				}
			}
			return bestMove;
		}
	}
	public Move getMove(){
		return this.returnMove;
	}
}

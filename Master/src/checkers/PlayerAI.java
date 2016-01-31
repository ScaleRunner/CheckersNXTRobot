package checkers;

import java.util.ArrayList;

public class PlayerAI implements Player{
	private int maxDepth;
	Heuristic heuristic;
	
	public PlayerAI (int maxDepth){
		this.maxDepth = maxDepth;
		this.heuristic = new Heuristic();
	}
		
	//ZWART
	private int maxValue(Board board, int currentDepth, int alpha, int beta){
		board.setWhitePlaying(false);
		ArrayList <Move> legalMoves = board.getAllLegalMoves();
		int maxScore = Integer.MIN_VALUE;
		
		currentDepth++;
		if (board.isWin("black") || board.isLose("black") || currentDepth == maxDepth)
			return heuristic.totalHeuristic(board);
		
		for(int move = 0; move < legalMoves.size(); move++){
			Board newBoard = board.deepCopy();
			newBoard.doMove(legalMoves.get(move));
			
			maxScore = Math.max(maxScore, minValue(newBoard, currentDepth, alpha, beta));
			alpha = Math.max(alpha, maxScore);
			if(beta <= alpha)
					break; //PRUNE	
		}
		return maxScore;
	}
	
	//WIT
	private int minValue(Board board, int currentDepth, int alpha, int beta){
		board.setWhitePlaying(true);
		ArrayList <Move> legalMoves = board.getAllLegalMoves();
		int minScore = Integer.MAX_VALUE;
		
		if (board.isWin("black") || board.isLose("black"))
			return heuristic.totalHeuristic(board);
		
		for(int move = 0; move < legalMoves.size(); move++){
			Board newBoard = board.deepCopy();
			
			newBoard.doMove(legalMoves.get(move));
			
			minScore = Math.min(minScore, maxValue(newBoard,currentDepth,alpha,beta));
			beta = Math.min(beta, minScore);
			if(beta <= alpha)
				break; //PRUNE
		}
		
		return minScore;
	}
	
	public Move doAnyMove(Board board){
		ArrayList <Move> legalMoves = board.getAllLegalMoves();
		return legalMoves.get(0);
	}
	
	public Move getMove2(Board board){
		ArrayList <Move> legalMoves = board.getAllLegalMoves();
		Move bestMove;
		if(legalMoves.size() > 0){
			bestMove = legalMoves.get(0);
		}
		else{
			return null;
		}
		System.out.println("Calculating....");
		int bestScore = Integer.MIN_VALUE;
		int currentBest = Integer.MIN_VALUE;
		int alpha = Integer.MIN_VALUE;
		int beta = Integer.MAX_VALUE;
		
		for(int move = 0; move < legalMoves.size(); move++){
			int currentDepth = 0;
			Board newBoard = board.deepCopy();
			newBoard.doMove(legalMoves.get(move));
			
			currentBest = minValue(newBoard, currentDepth, alpha, beta);
			System.out.println("Currentbest: " + currentBest + " " + legalMoves.get(move));
			if (currentBest > bestScore){
				bestScore = currentBest;
				Move betterMove = legalMoves.get(move);
				bestMove.copy(bestMove, betterMove);
			}
		}
		System.out.println("Player " + (board.isWhitePlaying()? "1" : "2") + " has selected " + bestScore + " " + bestMove);
		return bestMove;
	}
	
	@Override
	public Move getMove(Board board) {
		System.out.println("Waiting for player " + (board.isWhitePlaying()? "1" : "2"));
		System.out.println("Calculating...");
		MoveValue bestMove = new MoveValue(maxDepth);
		bestMove = bestMove.alphabeta(board, 0, Integer.MIN_VALUE, Integer.MAX_VALUE, true);
		return bestMove.getMove();
	}

	@Override
	public void setBoard(Board b) {
		// TODO Auto-generated method stub
		
	}

}

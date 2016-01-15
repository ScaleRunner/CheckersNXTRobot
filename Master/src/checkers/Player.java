package checkers;

public interface Player {	
	
	public Move getMove (Board b);
	
	public void setBoard(Board b);

	public Move doAnyMove(Board board);

	public Move getMove2(Board board);
}

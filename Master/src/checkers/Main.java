package checkers;

import nxt.BrickMaster;


public class Main {	
	private static BrickMaster brickMaster = new BrickMaster();
	private static Game game = new Game();
	
	public static void main(String[] args) {
		game.start();
		
//		brickMaster.sync();
		
//		//Play Without Bricks
//		while(!game.gameOver())
//			game.continueGame();
		
		while(!game.gameOver()){
			if(!game.isRunning()){
				do{
					Move move = game.getMove();
					if(!(move == null)){
						System.out.println("MOVE:" + move.toString());
						brickMaster.doMove(move);
					}
				} while(game.availableMoves()>0);				
				game.continueGame();
			}
		}
	}
}

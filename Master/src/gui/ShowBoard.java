package gui;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.geom.Ellipse2D;
import java.util.ArrayList;

import javax.swing.JPanel;

import checkers.Board;
import checkers.Move;

public class ShowBoard extends JPanel {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
//	private Color color;	
	
	ArrayList<Move> selectList = new ArrayList<Move>();
	private Board board;
	private int frameSize;
	private int borderSize;
	private int squareSize;
	
	public ShowBoard(Board board) {
		super();
		this.board = board;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		makeCheckersBoard(g);	
	}
	
	private boolean isSelected(int i, int j) {
		for(Move m : selectList){
			if (m.c1 ==j && m.r1 == i || m.c2 ==j && m.r2==i){
				return true;
			}
		}
		return false;
	}
	
	public void cleanAfterMove (){
		selectList = new ArrayList<Move>();
		this.repaint();
	}
	
	public void makeCheckersBoard(Graphics g) {
		Color black = Color.BLACK;
		Color white = Color.WHITE;
		Color brown = new Color(102, 51, 0);
		
		frameSize = (this.getHeight() > this.getWidth() ? this.getWidth() : this.getHeight()); //size of entire board (note: always square)
		borderSize = frameSize/100;	// size of brown frame
		squareSize = (frameSize - 2*borderSize)/ board.getSize(); //size of square.
		int squareRoom = 4;	// room between border of square and start piece. (note: smaller value means bigger pieces)
		
		g.setColor(brown);
		g.fillRect(0, 0, frameSize, frameSize);
		
		int y = borderSize;
		int x = borderSize;
		for(int i = 0; i < board.getSize(); i++){
			for(int j = 0; j < board.getSize(); j++){
				if(i%2 ==j%2)
					g.setColor(black);
				else
					g.setColor(white);
				
				if(isSelected(i,j)){
					Color temp = g.getColor();
					g.setColor(Color.RED);
					g.fillRect(x,y, squareSize, squareSize); // draw red square
					g.setColor(temp);
					g.fillRect(x+2,y+2, squareSize-4, squareSize-4); // draw square
				}else
					g.fillRect(x,y, squareSize, squareSize); // draw square
				
				switch (board.getValue(i, j)){	//draw piece
					case BLACK: 
						drawFilledCircle(g,x,y,squareSize,squareRoom, Color.GRAY);
						drawFilledCircle(g,x,y,squareSize,squareRoom+3, Color.BLACK);	
						break;
					case BLACKC:
						drawFilledCircle(g,x,y,squareSize,squareRoom, Color.GRAY);
						drawFilledCircle(g,x,y,squareSize,squareRoom+2, Color.BLACK);
						drawFilledCircle(g,x,y,squareSize,squareRoom+squareSize/4, Color.GRAY);					
						break;
					case EMPTY:
						break;
					case ERR:
						g.setColor(Color.RED);
						g.fillRect(x,y, squareSize, squareSize);
						break;
					case WHITE: 
						drawFilledCircle(g,x,y,squareSize,squareRoom, Color.GRAY);	
						drawFilledCircle(g,x,y,squareSize,squareRoom+3, Color.WHITE);
						break;
					case WHITEC:
						drawFilledCircle(g,x,y,squareSize,squareRoom, Color.GRAY);
						drawFilledCircle(g,x,y,squareSize,squareRoom+2, Color.WHITE);
						drawFilledCircle(g,x,y,squareSize,squareRoom+squareSize/4, Color.GRAY);
						break;
					default:
						break;
				}
				
				
				x += squareSize;
			}
			y += squareSize;
			x = borderSize;
		}
	}
		
	private void drawFilledCircle (Graphics g, int x, int y, int size, int room , Color color){
		g.setColor(color);
//		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		Ellipse2D.Double circle = new Ellipse2D.Double(x+room, y+room, size-2*room, size-2*room);
		g2d.fill(circle);

	}

	public int getFrameSize (){
		return frameSize;
	}
	
	public int getBorderSize(){
		return borderSize;
	}
	
	public int getSquareSize(){
		return squareSize;
	}
	
	public void setSelectList (ArrayList<Move> list){
		selectList = list;
		this.repaint();
	}
}

package checkers;

// we use this class like a C++ struct. Variables deliberately set public.
public class Move {
	public int r1;
	public int c1;
	public int r2;
	public int c2;
	public boolean hit = false;
	
	public Move(int r1 ,int c1, int r2, int c2, boolean hit){
		this.r1 = r1;
		this.c1 = c1;
		this.r2 = r2;
		this.c2 = c2;
		this.hit = hit;
	}
	
	public void copy(Move moveOld, Move moveNew){
			moveOld.c1 = moveNew.c1;
			moveOld.c2 = moveNew.c2;
			moveOld.r1 = moveNew.r1;
			moveOld.r2 = moveNew.r2;
			moveOld.hit = moveNew.hit;
	}
	
	@Override
	public String toString() {
		return "Move (" + r1 + "," + c1 + ") to (" + r2 + "," + c2 + ") hit=" + hit;
	}
	
	@Override
	public boolean equals (Object o){
		if(o instanceof Move){
			Move m = (Move) o;
			return m.hit == this.hit && m.c1 == this.c1 && m.r1 == this.r1 && m.c2 == this.c2 && m.r2 == this.r2;
		}
		return false;
	}
}

package gobang.chess;

/** 该类用于描述棋子 **/
public class ChessMan {

	//注意: 1代表黑棋, 2代表白棋
	private int color = 0;

	public ChessMan(int color) {
		this.color = color;
	}

	public int getColor() {
		return color;
	}

	public void setColor(int color) {
		this.color = color;
	}

}

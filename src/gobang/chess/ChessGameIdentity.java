package gobang.chess;

public enum ChessGameIdentity {

	BLACK("§0黑方"), WHITE("§f白方"), SPECTAROR("§e观战者");

	private String describe = null;
	
	ChessGameIdentity(String describe) {
		this.describe = describe;
	}

	public String getDescribe() {
		return describe;
	}
	
}

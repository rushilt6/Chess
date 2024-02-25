package chess;
import java.util.*;
public class Chess {
	
	private static Board board;
	private static Player player = Player.white;
	enum Player { white, black }
	
	/**
	 * Plays the next move for whichever player has the turn.
	 * 
	 * @param move String for next move, e.g. "a2 a3"
	 * 
	 * @return A ReturnPlay instance that contains the result of the move.
	 *         See the section "The Chess class" in the assignment description for details of
	 *         the contents of the returned ReturnPlay instance.
	 */
	public static ReturnPlay play(String move) {

		/* FILL IN THIS METHOD */
		String playerColor = Player.white == player ? "white" : "black";
		ReturnPlay result = new ReturnPlay();
		if(move.equalsIgnoreCase("resign")){
			if(player == Player.white){
				result.message = ReturnPlay.Message.RESIGN_BLACK_WINS;
			}
			else{
				result.message = ReturnPlay.Message.RESIGN_WHITE_WINS;
			}
			result.piecesOnBoard = getBoard();
			return result;
		}

		String[] split = move.split(" ");
		Position from = convertPosition(split[0]);
		Position to = convertPosition(split[1]);
		String promote = split.length == 3 ? split[2] : "";

		Piece piece = board.getPiece(from);
		if((piece instanceof VacantSquare) || !(piece.getColor().equals(playerColor)) || !piece.moveValid(to, board)){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			result.piecesOnBoard = getBoard();
			return result;
		}
		Piece pieceAtNew = board.getPiece(to);
		board.setPiece(to, piece, promote);
		board.setPiece(from, new VacantSquare(from),"");
		if(inCheck(player)){
			board.setPiece(from,piece,promote);
			board.setPiece(to,pieceAtNew,promote);
			result.message = ReturnPlay.Message.CHECK;
		}
		result.piecesOnBoard = getBoard();
		if(result.message == null){
			player = Player.black == player ? Player.white : Player.black;
		}
		if(inCheck(player)){
			if(isCheckmate(player)){
				result.message = Player.white == player ? ReturnPlay.Message.CHECKMATE_BLACK_WINS : ReturnPlay.Message.CHECKMATE_WHITE_WINS;
			}
		}
		return result;
		/* FOLLOWING LINE IS A PLACEHOLDER TO MAKE COMPILER HAPPY */
		/* WHEN YOU FILL IN THIS METHOD, YOU NEED TO RETURN A ReturnPlay OBJECT */
	}
	
	
	/**
	 * This method should reset the game, and start from scratch.
	 */
	public static void start() {
		board = new Board();
	}

	private static Position convertPosition(String pos){
		int file = pos.charAt(0) - 'a';
		int rank = Character.getNumericValue(pos.charAt(1));
		return new Position(rank,file);
	}
    public static ArrayList<ReturnPiece> getBoard(){
        ArrayList<ReturnPiece> pieces = new ArrayList<>();
        for(int r = 1; r < 9; r++){
            for(int f = 0; f < 8; f++){
                Piece piece = board.getPiece(new Position(r,f));
				if(piece != null){
					ReturnPiece returnPiece = createReturnPiece(piece);
					if(returnPiece != null){
						pieces.add(returnPiece);
					}
				}
            }
        }
		return pieces;
    }
	private static ReturnPiece createReturnPiece(Piece piece){
		ReturnPiece returnPiece = new ReturnPiece();
		returnPiece.pieceType = findPieceType(piece);
		if(returnPiece.pieceType == null){
			return null;
		}
		returnPiece.pieceFile = ReturnPiece.PieceFile.values()[piece.getPosition().getFile()];
		returnPiece.pieceRank = piece.getPosition().getRank();
		return returnPiece;
	}
	private static ReturnPiece.PieceType findPieceType(Piece piece){
		if(piece instanceof Pawn){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WP : ReturnPiece.PieceType.BP;
		}
		if(piece instanceof Rook){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WR : ReturnPiece.PieceType.BR;
		}
		if(piece instanceof Knight){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WN : ReturnPiece.PieceType.BN;
		}
		if(piece instanceof Bishop){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WB : ReturnPiece.PieceType.BB;
		}
		if(piece instanceof Queen){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WQ : ReturnPiece.PieceType.BQ;
		}
		if(piece instanceof King){
			return piece.getColor().equals("white") ? ReturnPiece.PieceType.WK : ReturnPiece.PieceType.BK;
		}
		return null;
	}
	public static boolean inCheck(Player player){
		String playerColor = Player.white == player ? "white" : "black";
		Position kingPos = getKingPos(playerColor);
		for(int r = 1; r < 9; r++){
			for(int f = 0; f < 8; f++){
				Piece piece = board.getPiece(new Position(r,f));
				if(!(piece instanceof VacantSquare) && !piece.getColor().equals(playerColor) && piece.moveValid(kingPos, board)){
					return true;
				}
			}
		}
		return false;
	}
	private static Position getKingPos(String playerColor){
		for(int r = 1; r < 9; r++){
			for(int f = 0; f < 8; f++){
				Position pos = new Position(r,f);
				Piece piece = board.getPiece(pos);
				if((piece instanceof King) && piece.getColor().equals(playerColor)){
					return pos;
				}
			}
		}
		return null;
	}
	private static boolean isCheckmate(Player player){
		String playerColor = Player.white == player ? "white" : "black";
		for(int startR = 1; startR < 9; startR++){
			for(int startF = 0; startF < 8; startF++){
				Position currPosition = new Position(startR,startF);
				Piece currPiece = board.getPiece(currPosition);
				if(!(currPiece instanceof VacantSquare) && currPiece.getColor().equals(playerColor)){
					for(int endR = 1; endR < 9; endR++){
						for(int endF = 0; endF < 8; endF++){
							Position endPos = new Position(endR, endF);
							if(currPiece.moveValid(endPos, board)){
								Piece oldPiece = board.getPiece(endPos);
								board.setPiece(endPos, currPiece,"");
								board.setPiece(currPosition, new VacantSquare(currPosition),"");
								boolean check = inCheck(player);
								board.setPiece(endPos,oldPiece,"");
								board.setPiece(currPosition,currPiece,"");
								if(!check){
									return false;
								}
							}
						}
					}
				}
			}
		}
		return true;
	}
}
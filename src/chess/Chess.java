package chess;
import java.util.*;
public class Chess {
	
	private static Board board;
	private static Player player;
	enum Player { white, black }
	private static boolean castleLegal = false;
	
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
		if(!move.contains(" "))
		{
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			result.piecesOnBoard = getBoard();
			return result;
		}
		String[] split = move.split(" ");
		Position from = convertPosition(split[0]);
		Position to = convertPosition(split[1]);
		String promote = split.length >= 3 ? split[2] : "";
		boolean isPromotion = false;

		if(!promote.equals(""))
			isPromotion = true;
		if(!from.inBounds() || !to.inBounds()){
			result.message = ReturnPlay.Message.ILLEGAL_MOVE;
			result.piecesOnBoard = getBoard();
			return result;
		}
		if(canCastle(from, to))
			executeCastle(from,to);
		else
		{
			Piece piece = board.getPiece(from);
			if((piece instanceof VacantSquare) || !(piece.getColor().equals(playerColor)) || !piece.moveValid(to, board)){
				
				result.message = ReturnPlay.Message.ILLEGAL_MOVE;
				if(castleLegal)
				{
					result.message = ReturnPlay.Message.CHECK;
					castleLegal = false;
				}
				result.piecesOnBoard = getBoard();
				return result;
			}
			boolean completedEnPassant = false;
			if(piece instanceof Pawn){
				int fileDiff = Math.abs(to.getFile() - piece.getPosition().getFile());
				if(fileDiff == 1 
				&& ((piece.getColor().equals("white") && to.getRank()==piece.getPosition().getRank()+1)
				|| (piece.getColor().equals("black") && to.getRank()==piece.getPosition().getRank()-1))){
					Position adjacent = new Position(piece.getPosition().getRank(),to.getFile());
					Piece adjPiece = board.getPiece(adjacent);
					if(adjPiece instanceof Pawn && ((Pawn)adjPiece).canEnPassant() && adjPiece.getColor()!=piece.getColor() && board.getPiece(to) instanceof VacantSquare){
						Piece pieceAtNew = board.getPiece(to);
						Piece adjacentPiece = board.getPiece(adjacent);
						board.setPiece(to,piece,"");
						board.setPiece(from,new VacantSquare(from),"");
						board.setPiece(adjacent, new VacantSquare(adjacent), "");
						if(inCheck(player)){
							board.setPiece(from,piece,"");
							board.setPiece(to,pieceAtNew,"");
							board.setPiece(adjacent,adjacentPiece,"");
							result.message = ReturnPlay.Message.CHECK;
						}
						completedEnPassant = true;
					}
				}
			}
			if(!completedEnPassant){
				Piece pieceAtNew = board.getPiece(to);
				board.setPiece(to, piece, promote);
				board.setPiece(from, new VacantSquare(from),"");
				if(inCheck(player)){
					board.setPiece(from,piece,promote);
					board.setPiece(to,pieceAtNew,promote);
					result.message = ReturnPlay.Message.CHECK;

				}
				piece.setMoved(true);
			}
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
		if (split.length == 4 && split[3].equalsIgnoreCase("draw?")) 
		{
			result.message = ReturnPlay.Message.DRAW;
		}
		else if(split.length == 3 && isPromotion && promote.equalsIgnoreCase("draw?"))
		{
			result.message = ReturnPlay.Message.DRAW;
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
		player = Player.white;
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
	private static void executeCastle(Position oldPos, Position newPos)
	{
		Piece kingObj, rookObj;
		kingObj = board.getPiece(oldPos);
		Position oldRook, newRook;

		if(inCheck(player))
			return;

		if(newPos.equals(new Position(1, 3-1))) 
		{
			oldRook = new Position(1, 1-1);
			newRook = new Position(1, 4-1);
			rookObj = board.getPiece(oldRook);
		}
		else if(newPos.equals(new Position(1, 7-1))) 
		{
			oldRook = new Position(1, 8-1);
			newRook = new Position(1, 6-1);
			rookObj = board.getPiece(oldRook);
		}
		else if(newPos.equals(new Position(8, 3-1))) 
		{
			oldRook = new Position(8, 1-1);
			newRook = new Position(8, 4-1);
			rookObj = board.getPiece(oldRook);
		}
		else // Checking g8 since compiler does not like for some objects to not be initialized at the end
		{
			oldRook = new Position(8, 8-1);
			newRook = new Position(8, 6-1);
			rookObj = board.getPiece(oldRook);
		}
		board.setPiece(newPos, kingObj, "");
		board.setPiece(oldPos, new VacantSquare(oldPos), "");
		kingObj.setMoved(true);
		board.setPiece(newRook, rookObj, "");
		board.setPiece(oldRook, new VacantSquare(oldRook), "");
		rookObj.setMoved(true);
	}

	private static boolean canCastle(Position oldPos, Position newPos) {
		
		Position oldRook;
		Piece kingObj, rookObj;
		
		kingObj = board.getPiece(oldPos);
		if(!(kingObj instanceof King)) {
			return false;        
		}
		//rank is column, file is row
		Position whiteKing = new Position(1, 5-1);
		Position blackKing = new Position(8, 5-1);
		Position c1 = new Position(1, 3-1);
		Position g1 = new Position(1,7-1);
		Position c8 = new Position(8,3-1);
		Position g8 = new Position(1, 7-1);

		Position pos1, pos2, pos3;
		// remember to add to check if kingObj is an instnace of king
		if(kingObj instanceof King && ((oldPos.equals(whiteKing) && (newPos.equals(c1) || newPos.equals(g1))) || (oldPos.equals(blackKing) && (newPos.equals(c8) || newPos.equals(g8)))))
		{
			if(newPos.equals(c1)) 
			{
				oldRook = new Position(1, 1-1);
				rookObj = board.getPiece(oldRook);

				//Check e1, d1, c1
				pos1 = new Position(1, 5-1);
				pos2 = new Position(1, 4-1);
				pos3 = c1;
			}
			else if(newPos.equals(g1)) 
			{
				oldRook = new Position(1, 8-1);
				rookObj = board.getPiece(oldRook);
				//Check e1, f1, g1
				pos1 = new Position(1, 5-1);
				pos2 = new Position(1, 6-1);
				pos3 = g1;
			}
			else if(newPos.equals(c8)) 
			{
				oldRook = new Position(8, 1-1);
				rookObj = board.getPiece(oldRook);

				//Check e8, d8, c8
				pos1 = new Position(8, 5-1);
				pos2 = new Position(8, 4-1);
				pos3 = c8;
			}
			else // Checking g8 since compiler does not like for some objects to not be initialized at the end
			{
				oldRook = new Position(8, 8-1);
				rookObj = board.getPiece(oldRook);

				//Check e8, f8, g8
				pos1 = new Position(8, 5-1);
				pos2 = new Position(8, 4-1);
				pos3 = g8;

			}
			if(!rookObj.emptyPath(oldPos, board))
				return false;
			if (inCheck(player)) 
			{
				castleLegal = true;
				return false;
			}
			board.setPiece(pos2, kingObj, "");
			if (inCheck(player))
			{
				castleLegal = true;
				board.setPiece(oldPos, kingObj, "");
				board.setPiece(pos2, new VacantSquare(pos2), "");
				return false;
			} 
			board.setPiece(pos3, kingObj, "");
			if (inCheck(player))
			{
				castleLegal = true;
				board.setPiece(oldPos, kingObj, "");
				board.setPiece(pos3, new VacantSquare(pos3), "");
				return false;
			} 
			Position tempKingPos = kingObj.getPosition();
			board.setPiece(oldPos, kingObj, "");
			board.setPiece(tempKingPos, new VacantSquare(tempKingPos), "");
			if(rookObj instanceof Rook) 
			{	
				if(kingObj.getMoved() == false && rookObj.getMoved() == false) 
				{
					System.out.println(kingObj.getPosition());
					System.out.println(rookObj.getPosition());
					System.out.println("REACHED TRUE FOR CASTLE");
					return true;
				}
				else 
					return false;
			}
			else
				return false;
		}
		return false;
	} 
}

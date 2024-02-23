package chess;

import java.text.ParsePosition;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(String color, String piece, Position position)
    {
        super(color, piece, position);
    }
    public boolean moveValid(Position newPos, Piece piece, Board board)
    {
        Position oldPos = this.getPosition();
        int oldPosFile = oldPos.getFile(); // File is letters 0-7
        int oldPosRank = oldPos.getRank() - 1; // Rank is 1-8
        int newPosFile = newPos.getFile();
        int newPosRank = newPos.getRank() - 1;

        //Bishop can move multiple spaces diagonally
        int x = Math.abs(oldPosRank - newPosRank);
        int y = Math.abs(oldPosFile - newPosFile);

        if(x==y && oldPos!=newPos)
        {
            if(board.getPiece(newPos) instanceof VacantSquare)
            {
                if(emptyPath(newPos, board)) 
                {  
					return true;
				}
				else 
                {
					//invalid move as there is a piece in the way
					return false;
				}
            }
            else
            {
                if(board.getPiece(newPos).getColor()==board.getPiece(oldPos).getColor())
                    return false;
                else
                {
                    if(emptyPath(newPos, board))
                        return true;
                    else
                        return false;
                }
            }
        }
        

        return false;
    }
    public void movePiece(Position newPos, Piece piece, char promPiece, Board board)
    {
        super(color, position);
    }

    public boolean emptyPath(Position newPos, Board board) 
    {
        Position oldPos = this.getPosition();
        int x = Math.abs((newPos.getRank()-1) - (oldPos.getRank()-1)) ;

        // Determine the direction of the diagonal move
        int fileDirection = Integer.compare(newPos.getFile(), oldPos.getFile());
        int rankDirection = Integer.compare(newPos.getRank(), oldPos.getRank());

        // Traverse the path and check for pieces
        for (int i = 1; i < x; i++) {
            int intermediateFile = oldPos.getFile() + i * fileDirection;
            int intermediateRank = oldPos.getRank()-1 + i * rankDirection;
            Position pos = new Position(intermediateRank, intermediateFile);
            if (!(board.getPiece(pos) instanceof VacantSquare)) 
            {
                // There is a piece in the path
                return false;
            }
        }

        // The path is empty
        return true;
	}
}

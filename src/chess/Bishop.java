package chess;

import java.text.ParsePosition;
import java.util.List;

public class Bishop extends Piece
{
    public Bishop(String color, Position position)
    {
        super(color, position);
    }
    public boolean moveValid(Position newPos, Board board)
    {
        if(!(newPos.inBounds())){ return false;}

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

    public boolean emptyPath(Position newPos, Board board) 
    {
        Position oldPos = this.getPosition();

        // Determine the direction of the diagonal move
        int fileDirection = Integer.compare(newPos.getFile(), oldPos.getFile());
        int rankDirection = Integer.compare(newPos.getRank(), oldPos.getRank());

        int currRank = oldPos.getRank() + rankDirection;
        int currFile = oldPos.getFile() + fileDirection;
        // Traverse the path and check for pieces
        while(currRank != newPos.getRank() || currFile != newPos.getFile()){
            if(!(board.getPiece(new Position(currRank, currFile)) instanceof VacantSquare)){
                return false;
            }
            currRank += rankDirection;
            currFile += fileDirection;
        }

        // The path is empty
        return true;
	}
}

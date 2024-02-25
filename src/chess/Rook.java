package chess;
import java.util.*;
public class Rook extends Piece
{

    public Rook(String color, Position position)
    {
        super(color, position);
    }
    @Override
    public boolean moveValid(Position newPos, Board board)
    {
        if(!(newPos.inBounds())){ return false;}
        Position oldPos = this.getPosition();

        int oldPosFile = oldPos.getFile(); // File is letters 0-7 (col)
        int oldPosRank = oldPos.getRank() - 1; // Rank is 1-8 (row)
        int newPosFile = newPos.getFile();
        int newPosRank = newPos.getRank() - 1;

        //King can move one space anywhere arount it.
        int y = Math.abs(oldPosFile - newPosFile);
        int x = Math.abs(oldPosRank - newPosRank);

        if((x==0 || y==0) && oldPos!=newPos)
        {
            if(board.getPiece(newPos) instanceof VacantSquare)
            {
                if(emptyPath(newPos, board))
                {
                    return true;
                }
                else
                {
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
        // Illegal move. King can only move one space around it
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

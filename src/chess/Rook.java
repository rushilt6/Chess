package chess;
import java.util.*;
public class Rook extends Piece
{

    public Rook(String color, String piece, Position position)
    {
        super(color, piece, position);
    }
    @Override
    public boolean moveValid(Position newPos, Piece piece, Board board)
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
        int x = Math.abs((newPos.getRank()-1) - (oldPos.getRank()-1));
        int y = Math.abs(newPos.getFile() - oldPos.getFile());

        int fileDirection = Integer.compare(newPos.getFile(), oldPos.getFile());
        int rankDirection = Integer.compare(newPos.getRank(), oldPos.getRank());

        for(int i = 1; i<x+y; i++)
        {
            int intermediateFile = oldPos.getFile() + i * fileDirection;
            int intermediateRank = oldPos.getRank()-1 + i * rankDirection;
            Position pos = new Position(intermediateRank, intermediateFile);
            if (!(board.getPiece(pos) instanceof VacantSquare)) 
            {
                // There is a piece in the path
                return false;
            }
        }

        return true;
    }
}

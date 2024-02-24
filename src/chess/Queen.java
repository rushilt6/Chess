package chess;
import java.util.*;
public class Queen extends Piece
{
    public Queen(String color, Position position)
    {
        super(color, position);
    }
    
    public boolean moveValid(Position newPos, Board board){
        if(!(newPos.inBounds())){ return false;}

        Position oldPos = this.getPosition();
        int oldPosFile = oldPos.getFile(); // File is letters 0-7
        int oldPosRank = oldPos.getRank() - 1; // Rank is 1-8
        int newPosFile = newPos.getFile();
        int newPosRank = newPos.getRank() - 1;

        //Bishop can move multiple spaces diagonally
        int x = Math.abs(oldPosRank - newPosRank);
        int y = Math.abs(oldPosFile - newPosFile);

        if(((x==y) || (x==0) || (y==0)) && oldPos!=newPos)
        {
            if(board.getPiece(newPos) instanceof VacantSquare)
            {
                if(emptyPath(newPos, board))
                    return true;
                else
                    return false;
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
        int x = Math.abs((newPos.getRank()-1) - (oldPos.getRank()-1));
        int y = Math.abs(newPos.getFile() - oldPos.getFile());

        int fileDirection = Integer.compare(newPos.getFile(), oldPos.getFile());
        int rankDirection = Integer.compare(newPos.getRank(), oldPos.getRank());

        int steps = 0;
        if(x>y)
            steps = x;
        else
            steps = y;

        for(int i = 1; i<steps; i++)
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

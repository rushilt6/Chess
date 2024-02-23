package chess;
import java.util.*;

public class King extends Piece
{
    public King(String color, Position position)
    {
        super(color, position);
    }
    public boolean moveValid(Position oldPos, Position newPos, Board board)
    {
        int oldPosFile = oldPos.getFile(); // File is letters 0-7
        int oldPosRank = oldPos.getRank(); // Rank is 1-8
        int newPosFile = newPos.getFile();
        int newPosRank = newPos.getRank();

        //King can move one space anywhere arount it.
        int x = Math.abs(oldPosFile - newPosFile);
        int y = Math.abs(oldPosRank - newPosRank);

        if((x==0 && y==1) || (x==1 && y==0) || (x==1 && y==1))
        {
            if(board.getPiece(newPos) instanceof VacantSquare)
            {
                if(isCheck(newPos))
                {
                    return false;
                }
                return true;
            }
            else
            {
                if(!(board.getPiece(newPos) instanceof VacantSquare))
                {
                    if(board.getPiece(newPos).getColor()==board.getPiece(oldPos).getColor())
                        return false;
                    else
                    {
                        if(isCheck(newPos))
                            return false;
                        return true;
                    }
                }
            }
        }
        else
        {

            return false; // Illegal move. King can only move one space around it
        }



        return false;
    }


}

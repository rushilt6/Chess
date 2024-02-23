package chess;
import java.util.*;

public class King extends Piece
{
    public King(String color, Position position)
    {
        super(color, position);
    }
    public boolean moveValid(Position newPos, Board board)
    {
        if(!(newPos.inBounds())){ return false;}
        int oldPosFile = this.getPosition().getFile(); // File is letters 0-7
        int oldPosRank = this.getPosition().getRank(); // Rank is 1-8
        int newPosFile = newPos.getFile();
        int newPosRank = newPos.getRank();

        //King can move one space anywhere arount it.
        int x = Math.abs(oldPosFile - newPosFile);
        int y = Math.abs(oldPosRank - newPosRank);

        if((x==0 && y==1) || (x==1 && y==0) || (x==1 && y==1))
        {
            if(board.getPiece(newPos) instanceof VacantSquare)
                return true;

            if(board.getPiece(newPos).getColor()==this.getColor())
                return false;

            return true;
        }
        return false;
    }


}

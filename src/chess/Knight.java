package chess;
import java.util.*;
public class Knight extends Piece
{
    public Knight(String color, Position position)
    {
        super(color, position);
    }
    
    public boolean moveValid(Position newPosition, Board board){
        if(!(newPosition.inBounds())) return false;
        int rankDiff = Math.abs(newPosition.getRank() - this.getPosition().getRank());
        int fileDiff = Math.abs(newPosition.getFile() - this.getPosition().getFile());
        if(((rankDiff == 2 && fileDiff == 1) || (rankDiff==1 && fileDiff==2)) && this.getPosition()!=newPosition){
            if(board.getPiece(newPosition).getColor()==this.getColor()){
                return false;
            }
            return true;
        }


        return false;
    }
}



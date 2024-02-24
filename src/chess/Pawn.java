package chess;
import java.util.*;
public class Pawn extends Piece
{

    public Pawn(String color, String piece, Position position)
    {
        super(color, piece, position);
    }

    @Override
    public boolean moveValid(Position newPosition, Piece piece, Board board)
    {
        if(!(newPosition.inBounds())){ return false;}
        
        int dir = this.getColor().equals("white") ? 1 : -1;
        int startRank = this.getColor().equals("white") ? 2 : 7;

        int rankDiff = newPosition.getRank() - this.getPosition().getRank();
        int fileDiff = newPosition.getFile() - this.getPosition().getFile();
        if(fileDiff == 0){
            if(rankDiff == dir){
                return board.getPiece(newPosition) instanceof VacantSquare;
            } else if(this.getPosition().getRank() == startRank && rankDiff == (2 * dir)){
                Position infront = new Position(this.getPosition().getRank() + dir, this.getPosition().getFile());
                return board.getPiece(newPosition) instanceof VacantSquare;
            }
        }
        
        if(Math.abs(fileDiff) == 1 && rankDiff == dir){
            return !(board.getPiece(newPosition) instanceof VacantSquare) && 
            !(board.getPiece(newPosition).getColor()==this.getColor());
        }
        return false;
    }
}

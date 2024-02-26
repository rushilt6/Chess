package chess;
import java.util.*;
public class Pawn extends Piece
{

    private boolean inEnPassant = false;
    public Pawn(String color, Position position)
    {
        super(color, position);
    }

    @Override
    public boolean moveValid(Position newPosition, Board board)
    {
        if(!(newPosition.inBounds())){ return false;}

        if(this.enPassant(newPosition,board)){
            return true;
        }

        int dir = this.getColor().equals("white") ? 1 : -1;
        int startRank = this.getColor().equals("white") ? 2 : 7;

        int rankDiff = newPosition.getRank() - this.getPosition().getRank();
        int fileDiff = newPosition.getFile() - this.getPosition().getFile();
        if(fileDiff == 0){
            if(rankDiff == dir){
                if(board.getPiece(newPosition) instanceof VacantSquare){
                    setEnPassant(false);
                    return true;
                }
                return false;
            } else if(this.getPosition().getRank() == startRank && rankDiff == (2 * dir)){
                Position infront = new Position(this.getPosition().getRank() + dir, this.getPosition().getFile());
                if(board.getPiece(newPosition) instanceof VacantSquare && board.getPiece(infront) instanceof VacantSquare){
                    setEnPassant(true);
                    return true;
                }
                return false;
            }
        }
        if(Math.abs(fileDiff) == 1 && rankDiff == dir){
            return !(board.getPiece(newPosition) instanceof VacantSquare) && 
            !(board.getPiece(newPosition).getColor()==this.getColor());
        }
        return false;
    }
    public boolean canPromote(Position newPosition){
        return ((this.getColor()=="white" && newPosition.getRank()==8) || 
                (this.getColor()=="black" && newPosition.getRank()==1));
    }
    public boolean canEnPassant(){
        return inEnPassant;
    }
    public void setEnPassant(boolean inEnPassant){
        this.inEnPassant = inEnPassant;
    }
    private boolean enPassant(Position newPosition, Board board){
        int fileDiff = Math.abs(newPosition.getFile() - this.getPosition().getFile());
        if(fileDiff == 1 
        && ((this.getColor().equals("white") && newPosition.getRank()==this.getPosition().getRank()+1)
        || (this.getColor().equals("black") && newPosition.getRank()==this.getPosition().getRank()-1))){
            Position adjacent = new Position(this.getPosition().getRank(),newPosition.getFile());
            Piece adjPiece = board.getPiece(adjacent);
            if(adjPiece instanceof Pawn && ((Pawn)adjPiece).canEnPassant()){
                return true;
            }
        }
        return false;
    }
}

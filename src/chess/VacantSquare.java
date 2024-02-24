package chess;
import java.util.*;
public class VacantSquare extends Piece
{
    public VacantSquare(Position position)
    {
        super(null,null,position);
    }
    public boolean moveValid(Position newPos, Piece piece, Board board){
        return false;
    }
}

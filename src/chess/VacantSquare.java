package chess;
import java.util.*;
public class VacantSquare extends Piece
{
    public VacantSquare(Position position)
    {
        super(null,position);
    }
    public boolean moveValid(Position newPos, Board board){
        return false;
    }
}

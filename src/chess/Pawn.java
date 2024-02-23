package chess;
import java.util.*;
public class Pawn extends Piece
{

    public Pawn(String color, Position position)
    {
        super(color,position);
    }

    @Override
    public boolean moveValid(Position position, Board board)
    {
        return false;
    }
}

package chess;
import java.util.*;
public class Rook extends Piece
{

    public Rook(String color, Position position)
    {
        super(color,position);
    }
    @Override
    public boolean moveValid(Position newPosition, Board board){
        return false;
    }

}

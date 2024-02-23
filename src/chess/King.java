package chess;
import java.util.*;

public class King extends Piece
{
    public King(String color, Position position)
    {
        super(color, position);
    }
    public boolean moveValid(Position newPosition, Board board){
        return false;
    }
}

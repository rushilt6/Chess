package chess;
import java.util.*;
public class Knight extends Piece
{
    public Knight(String color, Position position)
    {
        super(color, position);
    }
    
    public boolean moveValid(Position newPosition, Board board){
        return false;
    }
}



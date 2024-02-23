package chess;
import java.util.*;
public class Queen extends Piece
{
    public Queen(String color, Position position)
    {
        super(color, position);
    }
    
    public boolean moveValid(Position position, Board board){
        return false;
    }
}

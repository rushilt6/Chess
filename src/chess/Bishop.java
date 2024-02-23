package chess;
public class Bishop extends Piece
{
    public Bishop(String color, Position position)
    {
        super(color, position);
    }
    public boolean moveValid(Position newPosition, Board board){
        return false;
    }
}

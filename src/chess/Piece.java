package chess;
import java.util.*;
public abstract class Piece 
{
    protected String color;
    protected String piece;
    protected Position position;
    
    public Piece(String color, String piece, Position position)
    {
        this.color = color;
        this.piece = piece;
        this.position = position;
    }

    public String getColor(){
        return color;
    }
    public Position getPosition(){
        return position;
    }
    public String getPiece()
    {
        return piece;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public abstract boolean moveValid(Position newPos, Piece piece, Board board);

    public void movePiece(Position newPos, Piece piece, char promPiece, Board board)
    {
        Position oldPos = this.getPosition();
        board.setPiece(newPos, piece); // Sets the piece at the new position
        board.setPiece(oldPos, new VacantSquare(oldPos)); // Sets the old piece as a vacant square
    }

    public boolean emptyPath(Position newPos, Board board)
    {
        return false;
    }

    
}

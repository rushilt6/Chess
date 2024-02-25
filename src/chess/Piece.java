package chess;
import java.util.*;
public abstract class Piece 
{
    protected String color;
    protected Position position;
    protected boolean moved;

    public Piece(String color, Position position)
    {
        this.color = color;
        this.position = position;
        moved = false;
    }

    public String getColor(){
        return color;
    }
    public Position getPosition(){
        return position;
    }
    public void setPosition(Position position){
        this.position = position;
    }
    public abstract boolean moveValid(Position newPos, Board board);

    public boolean emptyPath(Position newPos, Board board)
    {
        return false;
    }
    public boolean getMoved()
    {
        return moved;
    }
    public void setMoved(boolean moved)
    {
        this.moved = moved;
    }


}

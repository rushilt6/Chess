package chess;
import java.util.*;
public abstract class Piece 
{
    String input;
    boolean hasMoved;
    
    public Piece(String input)
    {
        this.input = input;
        hasMoved = false;
    }

    public String getInput()
    {
        return input;
    }

    public boolean hasMoved()
    {
        return this.hasMoved;
    }

    public void setMoved(boolean hasMoved)
    {
        this.hasMoved = hasMoved;
    }

    public void move(String oldPos, String newPos)
    {
        return;
    }

    public boolean moveValid(String oldPos, String newPos)
    {
        return true;
    }

    public boolean isPathEmpty(String oldPos, String newPos)
    {
        return true;
    }

}

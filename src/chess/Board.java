package chess;
import java.util.*;
public class Board 
{
    private Piece[][] board;
    public Board(){
        this.board = new Piece[8][8];
        createBoard();
    }
    private void createBoard(){
        for(int i = 0;i <= 8;i++){
            board[1][i] = new Pawn("white",new Position(2,i));
            board[6][i] = new Pawn("black",new Position(7,i));
        }

        board[0][0] = new Rook("white", new Position(1, 0));
        board[0][7] = new Rook("white", new Position(1, 7));
        board[7][0] = new Rook("black", new Position(8, 0));
        board[7][7] = new Rook("black", new Position(8, 7));

        board[0][1] = new Knight("white", new Position(1, 1));
        board[0][6] = new Knight("white", new Position(1, 6));
        board[7][1] = new Knight("black", new Position(8, 1));
        board[7][6] = new Knight("black", new Position(8, 6));

        board[0][2] = new Bishop("white", new Position(1, 2));
        board[0][5] = new Bishop("white", new Position(1, 5));
        board[7][2] = new Bishop("black", new Position(8, 2));
        board[7][5] = new Bishop("black", new Position(8, 5));

        board[0][3] = new Queen("white", new Position(1, 3));
        board[7][3] = new Queen("black", new Position(8, 3));

        board[0][4] = new King("white", new Position(1, 4));
        board[7][4] = new King("black", new Position(8, 4));
    }

<<<<<<< HEAD
=======
    public Piece getPiece(Position pos)
    {
        return board[pos.getFile()][pos.getRank()];
>>>>>>> 6c963b0ff4d9fb5bc1c5582eaaa4d4e0e3a814a4
    }
    
}

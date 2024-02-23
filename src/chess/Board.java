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
    }
}

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

        for (int rank = 2; rank < 6; rank++) {
            for (int file = 0; file < 8; file++) {
                board[rank][file] = new VacantSquare(new Position(rank + 1, file));
            }
        }

        for(int i = 0;i <= 8;i++){
            board[1][i] = new Pawn("white", "Pawn", new Position(2,i));
            board[6][i] = new Pawn("black", "Pawn", new Position(7,i));
        }

        board[0][0] = new Rook("white", "Rook", new Position(1, 0));
        board[0][7] = new Rook("white", "Rook", new Position(1, 7));
        board[7][0] = new Rook("black", "Rook", new Position(8, 0));
        board[7][7] = new Rook("black", "Rook", new Position(8, 7));

        board[0][1] = new Knight("white", "Knight", new Position(1, 1));
        board[0][6] = new Knight("white", "Knight", new Position(1, 6));
        board[7][1] = new Knight("black", "Knight", new Position(8, 1));
        board[7][6] = new Knight("black", "Knight", new Position(8, 6));

        board[0][2] = new Bishop("white", "Bishop", new Position(1, 2));
        board[0][5] = new Bishop("white", "Bishop", new Position(1, 5));
        board[7][2] = new Bishop("black", "Bishop", new Position(8, 2));
        board[7][5] = new Bishop("black", "Bishop", new Position(8, 5));

        board[0][4] = new King("white", "King", new Position(1, 4));
        board[7][4] = new King("black", "King", new Position(8, 4));
        
        board[0][4] = new King("white", "King", new Position(1, 4));
        board[7][4] = new King("black", "King", new Position(8, 4));
    }
    
    public Piece getPiece(Position pos)
    {
        return board[pos.getRank()-1][pos.getFile()];
    }
    
    public void setPiece(Position newPos, Piece piece)
    {
        if (piece == null) {
            Piece newPiece = new VacantSquare(newPos);
            board[newPos.getRank()-1][newPos.getFile()] = newPiece;
            return;
        }

        String namePiece = piece.getPiece();
        String color = piece.getColor();

        Piece newPiece;
        switch (namePiece) 
        {
            case "Rook":
                newPiece = new Rook(color, "Rook", newPos);
                break;
            case "Knight":
                newPiece = new Knight(color, "Knight", newPos);
                break;
            case "Bishop":
                newPiece = new Bishop(color, "Bishop", newPos);
                break;
            case "Queen":
                newPiece = new Queen(color, "Queen", newPos);
                break;
            case "Pawn":
                newPiece = new Pawn(color, "Pawn", newPos);
                break;
            case "King":
                newPiece = new King(color, "King", newPos);
                break;
            default:
                // Handle the default case or throw an exception if needed
                throw new IllegalArgumentException("Unknown piece type: " + namePiece);
        }

        board[newPos.getRank()-1][newPos.getFile()] = newPiece;
    }
    
}

package chess;
import java.util.*;
public class Board 
{
    public HashMap<String, Piece> board = new HashMap<String, Piece>();
    String letterRank = "abcdefgh";

    public void initializeBoard()
    {
        for(int i = 0; i<letterRank.length(); i++)
        {
            for(int j = 1; j<=8; j++)
            {
                String fileRank = letterRank.charAt(i) + Integer.toString(j);
                
                //Vacant Squares:
                board.put(fileRank, new VacantSquare("  "));


                //White Side:
                if(j==2)
                {
                    board.put(fileRank, new Pawn("wP"));
                }
                else if(fileRank.equals("a1") || fileRank.equals("h1"))
                {
                    board.put(fileRank, new Rook("wR"));
                }
                else if(fileRank.equals("b1") || fileRank.equals("g1"))
                {
                    board.put(fileRank, new Knight("wN"));
                }
                else if(fileRank.equals("c1")|| fileRank.equals("f1"))
                {
                    board.put(fileRank, new Bishop("wB"));
                }
                else if(fileRank.equals("d1"))
                {
                    board.put(fileRank, new Queen("wQ"));
                }
                else if(fileRank.equals("e1"))
                {
                    board.put(fileRank, new King("wK"));
                }

                //Black Side:
                if(j==7)
                {
                    board.put(fileRank, new Pawn("bP"));
                }
                else if(fileRank.equals("a8") || fileRank.equals("h8"))
                {
                    board.put(fileRank, new Rook("bR"));
                }
                else if(fileRank.equals("b8") || fileRank.equals("g8"))
                {
                    board.put(fileRank, new Knight("bN"));
                }
                else if(fileRank.equals("c8")|| fileRank.equals("f8"))
                {
                    board.put(fileRank, new Bishop("bB"));
                }
                else if(fileRank.equals("d8"))
                {
                    board.put(fileRank, new Queen("bQ"));
                }
                else if(fileRank.equals("e8"))
                {
                    board.put(fileRank, new King("bK"));
                }
            }
        }
    }

}

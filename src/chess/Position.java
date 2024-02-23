package chess;

public class Position {
    private int file; // a-h, but 0-7
    private int rank; // 1-8
    public Position(int rank, int file){
        this.file = file;
        this.rank = rank;
    }
    public int getRank(){
        return rank;
    }
    public int getFile(){
        return file;
    }
    public void setRank(int rank){
        this.rank = rank;
    }
    public void setFile(int file){
        this.file = file;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Position)) return false;
        Position position = (Position) o;
        return rank == position.rank && file == position.file;
    }

    @Override
    public String toString(){
        char filetoChar = (char) ('a'+file);
        return ""+filetoChar + rank;
    }

    public boolean inBounds(){
        return rank >=1 && rank <=8 && file >= 0 && file <=7;
    }
}

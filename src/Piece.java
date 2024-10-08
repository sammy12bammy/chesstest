public class Piece {
    private int row;
    private int col;
    private String type;
    private String color;
    private boolean canCastle;

    public Piece(String type, String color, int row, int col){
        this.type = type;
        this.color = color;
        this.row = row;
        this.col = col;
        canCastle = false;
    }
    //overload for a king or rook
    public Piece(String type, String color, int row, int col, boolean canCastle){
        this.type = type;
        this.color = color;
        this.row = row;
        this.col = col;
        this.canCastle = canCastle;
    }
    //overload for a blank piece
    public boolean canCastle(){
        return canCastle;
    }
  
    public int getRow(){
        return row;
    }

    public void changeRow(int row){
        this.row = row;
    }

    public int getCol(){
        return col;
    }

    public void changeCol(int col){
        this.col = col;
    }

    public String getType(){
        return type;
    }

    public void setType(String type){
        this.type = type;
    }

    public String getColor(){
        if(color.equals("white") || color.equals("black")){
            return color;
        } else {
            return null;
        }
        
    }

    public void setColor(String color){
        this.color = color;
    }

    public String toString(){
        if(!type.equals("--")){
            return color.substring(0,1) + type.toUpperCase().substring(0,1);
        } else {
            return "--";
        }
        
    }
}

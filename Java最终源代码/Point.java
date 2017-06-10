public class Point implements java.io.Serializable{
    int x,y;
    boolean haveChessPiece,isPlayPoint,isBoxPoint;
     ChessPiece chessPicec;                   
    public Point(int x,int y){
       this.x=x;
       this.y=y;
    }//x,y为位置坐标
    public boolean isHaveChessPiece(){
       return haveChessPiece;
    }
    public void setHaveChessPiece(boolean boo){
       haveChessPiece=boo;
    }
    public int getX(){
       return x;
    }
    public int getY(){
       return y;
    }
    public boolean equals(Point p){
       if(p.getX()==this.getX()&&p.getY()==this.getY())
           return true;
       else
           return false; 
    }
    //判断两个棋子位置是否相同
    public void setChessPiece(ChessPiece chessPicec){
       this.chessPicec=chessPicec;  
    }//?
    public ChessPiece getChessPiece(){
       return chessPicec;
    }
    public void setIsPlayPoint(boolean boo){
       isPlayPoint=true;
    }//
    public void setIsBoxPoint(boolean boo){
       isBoxPoint=true;
    }
    public boolean getIsPlayPoint(){
       return  isPlayPoint;
    }//返回游戏区域
    public boolean getIsBoxPoint(){
       return  isBoxPoint;
    }//返回棋盒区域
}

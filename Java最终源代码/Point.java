public class Point implements java.io.Serializable{
    int x,y;
    boolean haveChessPiece,isPlayPoint,isBoxPoint;
     ChessPiece chessPicec;                   
    public Point(int x,int y){
       this.x=x;
       this.y=y;
    }//x,yΪλ������
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
    //�ж���������λ���Ƿ���ͬ
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
    }//������Ϸ����
    public boolean getIsBoxPoint(){
       return  isBoxPoint;
    }//�����������
}

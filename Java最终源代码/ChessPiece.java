import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
public class ChessPiece extends JPanel{
   String name;
   Point point;
   boolean isRed,isBlack;
   Color color;
   //五个成员变量：棋子名称，位置，是黑子，是白子，棋子颜色
   ChessPiece(){
	      Cursor c=Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	      setCursor(c);
	      setBorder(null);
	      setOpaque(false);
	   }//方法，（去除棋子边界和白底）
   public void setName(String s){
       name=s;
   }//方法，设置棋子名称
   public String getName(){
       return name;
   }//方法，返回棋子名称
   public void setIsRed(boolean boo){
       isRed=boo;
       color=Color.red;
   }//方法，设置棋子是否为红棋
   public boolean getIsRed(){
       return isRed;
   }//方法，返回是红子
   public void setIsBlack(boolean boo){
       isBlack=boo;
       color=Color.black;
   }//设置棋子是否为黑子
   public boolean  getIsBlack(){
       return isBlack;
   }//方法，返回是黑子
   public void setAtPoint(Point p){
      point=p;
   }//设置point对象的引用
   public Point getAtPoint(){
      return point;
   }//返回point对象的引用
   public void paintComponent(Graphics g){
	      super.paintComponent(g);
	      int w=getBounds().width;
	      int h=getBounds().height;
	      Image image=null;
	      g.setColor(color); 
	      g.fillOval(1/2,1/2,w-1,h-1);
	      g.setColor(Color.white);
	      g.setFont(new Font("隶书",Font.BOLD,22));
	      g.drawString(name,7*w/32,2*h/3);
	  }     
   //棋子初始化，绘制棋子颜色，名字
   //paintComponent(Graphics g)方法
}

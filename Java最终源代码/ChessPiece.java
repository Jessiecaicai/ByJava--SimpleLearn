import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;
public class ChessPiece extends JPanel{
   String name;
   Point point;
   boolean isRed,isBlack;
   Color color;
   //�����Ա�������������ƣ�λ�ã��Ǻ��ӣ��ǰ��ӣ�������ɫ
   ChessPiece(){
	      Cursor c=Cursor.getPredefinedCursor(Cursor.HAND_CURSOR);
	      setCursor(c);
	      setBorder(null);
	      setOpaque(false);
	   }//��������ȥ�����ӱ߽�Ͱ׵ף�
   public void setName(String s){
       name=s;
   }//������������������
   public String getName(){
       return name;
   }//������������������
   public void setIsRed(boolean boo){
       isRed=boo;
       color=Color.red;
   }//���������������Ƿ�Ϊ����
   public boolean getIsRed(){
       return isRed;
   }//�����������Ǻ���
   public void setIsBlack(boolean boo){
       isBlack=boo;
       color=Color.black;
   }//���������Ƿ�Ϊ����
   public boolean  getIsBlack(){
       return isBlack;
   }//�����������Ǻ���
   public void setAtPoint(Point p){
      point=p;
   }//����point���������
   public Point getAtPoint(){
      return point;
   }//����point���������
   public void paintComponent(Graphics g){
	      super.paintComponent(g);
	      int w=getBounds().width;
	      int h=getBounds().height;
	      Image image=null;
	      g.setColor(color); 
	      g.fillOval(1/2,1/2,w-1,h-1);
	      g.setColor(Color.white);
	      g.setFont(new Font("����",Font.BOLD,22));
	      g.drawString(name,7*w/32,2*h/3);
	  }     
   //���ӳ�ʼ��������������ɫ������
   //paintComponent(Graphics g)����
}

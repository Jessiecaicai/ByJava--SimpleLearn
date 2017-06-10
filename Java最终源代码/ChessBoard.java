
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class ChessBoard extends JLayeredPane implements ActionListener {
	int m = 10, n = 9, distance = 42;
	Point[][] playPoint;
	String[] redName = { "帅", "士", "士", "相", "相", "马", "马", "车", "车", "炮", "炮", "兵", "兵", "兵", "兵", "兵" };
	String[] blackName = { "将", "仕", "仕", "象", "象", "馬", "馬", "車", "車", "炮", "炮", "卒", "卒", "卒", "卒", "卒" };
	ChessPiece[] redPiece;
	ChessPiece[] blackPiece;	//数组对象
	ChessBox redChessBox, blackChessBox;
	InitPieceLocation initPieceLocation;
	InitCanju initCanju;
	HandleMouse handleMouse;
	ArrayList<Point> step;		 //将棋点进行了包装,保存步骤
	JButton cancel;		//悔棋

	JLabel label1,label2,label3;	//新添加的对象
	MainBoard mainBoard;

	public ChessBoard() {	//构造方法
		setLayout(null);	//null为布局对象，这个方法可以设置自己的布局
		step = new ArrayList<Point>();		//ArrayList是动态数组
		initPointAndPiece();
		redChessBox = new ChessBox(distance, 11, 7);
		blackChessBox = new ChessBox(distance, 11, 1);		//distance是尺寸，中间是左右，最后是上下
		for (int i = 0; i < redPiece.length; i++)
			redChessBox.putPieceToBox(redPiece[i]);
		for (int i = 0; i < blackPiece.length; i++)
			blackChessBox.putPieceToBox(blackPiece[i]);		//把棋子放到棋盒里
		initPieceLocation = new InitPieceLocation();		//创建对象
		initPieceLocation.setPoint(playPoint);
		initPieceLocation.setRedChessPiece(redPiece);
		initPieceLocation.setBlackChessPiece(blackPiece);
		initPieceLocation.setRedChessBox(redChessBox);
		initPieceLocation.setBlackChessBox(blackChessBox);
		initCanju = new InitCanju();
		initCanju.setPoint(playPoint);
		initCanju.setRedChessBox(redChessBox);
		initCanju.setBlackChessBox(blackChessBox);
		handleMouse = new HandleMouse();
		handleMouse.setStep(step);
		handleMouse.setPoint(playPoint);
		handleMouse.setRedChessBox(redChessBox);
		handleMouse.setBlackChessBox(blackChessBox);
		handleMouse.initStep();

		cancel = new JButton("悔棋");	//按键
		add(cancel);
		cancel.setVisible(false);		//初始界面悔棋不可见，只有在开始棋谱了之后悔棋才可见
		cancel.setSize(60, 30);
		cancel.setLocation(13 * distance, (int) (5 * distance));	//设置位置
		cancel.addActionListener(this);		//cancel是事件源，this是监听器

		mainBoard = new MainBoard();		//创建对象
		mainBoard.setDistance(distance);
		mainBoard.setPlayPoint(playPoint);
		mainBoard.setRedChessBox(redChessBox);
		mainBoard.setBlackChessBox(blackChessBox);
		
		mainBoard.setRedPiece(redPiece);
		mainBoard.setBlackPiece(blackPiece);
		mainBoard.setBounds(0, 0, 710, 480);
		mainBoard.setOpaque(false);		//可以设置是否透明
		add(mainBoard, JLayeredPane.DEFAULT_LAYER);	//添加到JLayeredPane的最底层
		label1 = new JLabel(new ImageIcon("default.jpg"));		//添加图片
		label1.setBounds(0, 0, 710, 480);
		label2= new JLabel(new ImageIcon("p.jpg"));
		label2.setBounds(480,12,180,180);
		label3= new JLabel(new ImageIcon("p.jpg"));
		label3.setBounds(480,266,180,180);
		add(label2, JLayeredPane.DEFAULT_LAYER);
		add(label3, JLayeredPane.DEFAULT_LAYER);
		add(label1, JLayeredPane.DEFAULT_LAYER);
	}
	public void background1(){
		remove(label1);
		label1 = new JLabel(new ImageIcon("shizhan.jpg"));
		label1.setBounds(0, 0, 710, 480);
		add(label1, JLayeredPane.DEFAULT_LAYER);
	}
	public void background2(){
		remove(label1);
		label1 = new JLabel(new ImageIcon("canju.jpg"));
		label1.setBounds(0, 0, 710, 480);
		add(label1, JLayeredPane.DEFAULT_LAYER);
	}
	public void background3(){
		remove(label1);
		label1 = new JLabel(new ImageIcon("yanshi.jpg"));
		label1.setBounds(0, 0, 710, 480);
		add(label1, JLayeredPane.DEFAULT_LAYER);
	}
	public void background4(){
		remove(label1);
		label1 = new JLabel(new ImageIcon("default.jpg"));
		label1.setBounds(0, 0, 710, 480);
		add(label1, JLayeredPane.DEFAULT_LAYER);
	}

	private void initPointAndPiece() {		//私有方法设置初始棋子和棋点
		removeAll();	//把所有组件从容器中移走，父类JLayerPane里定义的方法
		playPoint = new Point[m][n];	//把棋点的位置用数组表示
		int Hspace = distance, Vspace = distance;	//h为水平间距，v为垂直间距
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				playPoint[i][j] = new Point(distance + Hspace, Vspace);
				playPoint[i][j].setIsPlayPoint(true);
				Hspace = Hspace + distance;
			}
			Hspace = distance;
			Vspace = Vspace + distance;		//先是水平每个point都依次set为playPoint，然后外循环再到下一列
		}
		redPiece = new ChessPiece[redName.length];	//设置红棋子，红棋子为chessPiece类的对象
		int size = distance;
		for (int i = 0; i < redPiece.length; i++) {
			redPiece[i] = new ChessPiece();
			add(redPiece[i], JLayeredPane.PALETTE_LAYER);	//把红棋子放到倒二层，在mainboard之上
			redPiece[i].setSize(size, size);
			redPiece[i].setIsRed(true);
			redPiece[i].setName(redName[i]);	//chessPiece中的方法，设置组件的名字
			redPiece[i].repaint();		/*若调用swing里的方法，那么尽可能用swing里的方法，因为swing里有很多与
				图形界面有关的方法，若是是调用awt里的方法，那么就用更新的方法，此方法相当于一个自刷新*/
		}
		blackPiece = new ChessPiece[blackName.length];	//同上
		for (int i = 0; i < blackPiece.length; i++) {
			blackPiece[i] = new ChessPiece();
			add(blackPiece[i], JLayeredPane.PALETTE_LAYER);
			blackPiece[i].setSize(size, size);
			blackPiece[i].setIsBlack(true);
			blackPiece[i].setName(blackName[i]);
			blackPiece[i].repaint();
		}
	}

	public ArrayList<Point> getStep() {		//step是ArrayList数组表
		return step;
	}

	public Point[][] getPoint() {
		return playPoint;
	}

	public void setShizhanPlay() {	//设置实战棋盘初始样式
		handleMouse.setRedFirstMove(true);
		handleMouse.initStep();		//清空所有步骤
		cancel.setVisible(false);	//swing中的方法
		initPieceLocation.putAllPieceToPlayChessArea();		//开始时的标准放法
		for (int i = 0; i < redPiece.length; i++) {		//设置鼠标监听器与处理器
			MouseListener[] listener = redPiece[i].getMouseListeners();		//将红棋的依数组中的序号设为mouseListen
			for (int k = 0; k < listener.length; k++)
				redPiece[i].removeMouseListener(listener[k]);
			listener = blackPiece[i].getMouseListeners();
			for (int k = 0; k < listener.length; k++)
				blackPiece[i].removeMouseListener(listener[k]);
			MouseMotionListener[] mr = redPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				redPiece[i].removeMouseMotionListener(mr[k]);
			mr = blackPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				blackPiece[i].removeMouseMotionListener(mr[k]);
		}
	}

	public void setCanjuPlay() {		//设置残局棋盘初始界面
		handleMouse.setRedFirstMove(true);
		handleMouse.initStep();
		cancel.setVisible(false);
		initPieceLocation.putAllPieceToChessBox();
		for (int i = 0; i < redPiece.length; i++) {
			MouseListener[] listener = redPiece[i].getMouseListeners();
			for (int k = 0; k < listener.length; k++)
				redPiece[i].removeMouseListener(listener[k]);
			listener = blackPiece[i].getMouseListeners();
			for (int k = 0; k < listener.length; k++)
				blackPiece[i].removeMouseListener(listener[k]);
			MouseMotionListener[] mr = redPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				redPiece[i].removeMouseMotionListener(mr[k]);
			mr = blackPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				blackPiece[i].removeMouseMotionListener(mr[k]);
			redPiece[i].addMouseListener(initCanju);	//注册监听器，监听器是initCanju
			blackPiece[i].addMouseListener(initCanju);
			redPiece[i].addMouseMotionListener(initCanju);	//注册监听器，这个接口中有两个方法
			blackPiece[i].addMouseMotionListener(initCanju);
		}

	}

	public void startMakeManul() {		//开始制作菜单
		cancel.setVisible(true);
		handleMouse.setRedFirstMove(true);
		handleMouse.initStep();		//清空所有步骤
		for (int i = 0; i < redPiece.length; i++) {
			MouseListener[] listener = redPiece[i].getMouseListeners();
			for (int k = 0; k < listener.length; k++)
				redPiece[i].removeMouseListener(listener[k]);
			listener = blackPiece[i].getMouseListeners();
			for (int k = 0; k < listener.length; k++)
				blackPiece[i].removeMouseListener(listener[k]);
			MouseMotionListener[] mr = redPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				redPiece[i].removeMouseMotionListener(mr[k]);
			mr = blackPiece[i].getMouseMotionListeners();
			for (int k = 0; k < mr.length; k++)
				blackPiece[i].removeMouseMotionListener(mr[k]);
			redPiece[i].addMouseListener(handleMouse);		//注册监听器
			blackPiece[i].addMouseListener(handleMouse);
			redPiece[i].addMouseMotionListener(handleMouse);	//注册监听器
			blackPiece[i].addMouseMotionListener(handleMouse);
		}
	}

	// public void paintComponent(Graphics g){
	// super.paintComponent(g);
	// g.setColor(Color.cyan); //设置g颜色
	// g.fillRect(0,0,this.getBounds().width,this.getBounds().height);//把所设置的颜色填充到当前矩形
	// g.setColor(Color.orange); //同上
	// g.fillRect(playPoint[0][0].x,playPoint[0][0].y,8*distance,9*distance);
	// g.setColor(Color.black); //设置线的颜色
	// for(int i=0;i<=m-1;i++){ //画出棋盘上的横线
	// g.drawLine(playPoint[i][0].x,playPoint[i][0].y,
	// playPoint[i][n-1].x,playPoint[i][n-1].y);
	// }
	// for(int j=0;j<=n-1;j++){ //画出竖线
	// if(j>0&&j<n-1){ //画出除边界以外的竖线
	// g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
	// playPoint[4][j].x,playPoint[4][j].y);
	// g.drawLine(playPoint[5][j].x,playPoint[5][j].y,
	// playPoint[m-1][j].x,playPoint[m-1][j].y);
	// }
	// else{ //画出边界的竖线
	// g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
	// playPoint[m-1][j].x,playPoint[m-1][j].y);
	// }
	// }
	// //画斜线
	// g.drawLine(playPoint[0][3].x,playPoint[0][3].y,
	// playPoint[2][5].x,playPoint[2][5].y);
	// g.drawLine(playPoint[0][5].x,playPoint[0][5].y,
	// playPoint[2][3].x,playPoint[2][3].y);
	// g.drawLine(playPoint[7][3].x,playPoint[7][3].y,
	// playPoint[m-1][5].x,playPoint[m-1][5].y);
	// g.drawLine(playPoint[7][5].x,playPoint[7][5].y,
	// playPoint[m-1][3].x,playPoint[m-1][3].y);
	// g.setFont(new Font("宋体",Font.BOLD,18));
	// int w=blackPiece[0].getBounds().width;
	// int h=blackPiece[0].getBounds().height;
	// Point [] boxPoint=blackChessBox.getBoxPoint();
	// g.drawString("黑棋盒",boxPoint[5].getX(),boxPoint[5].getY());
	// g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
	// 4*distance+5,4*distance+8); //画出黑棋盒的矩形边界
	// g.setColor(Color.red);
	// boxPoint=redChessBox.getBoxPoint();
	// w=redPiece[0].getBounds().width;
	// h=redPiece[0].getBounds().height;
	// g.drawString("红棋盒",boxPoint[5].getX(),boxPoint[5].getY());
	// g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
	// 4*distance+5,4*distance+8);//用红线画出红棋盒的矩形边界
	// g.setColor(Color.black);
	// for(int j=1;j<=n;j++){ //写上下两边的数字
	// g.drawString(""+j,(1+j)*distance-5,2*distance/5+2);
	// g.drawString(""+j,(1+j)*distance-5,11*distance-5);
	// }
	// int t=1;
	// for(char c='a';c<='j';c++,t++) //写左边的字母
	// g.drawString(""+c,distance+3,t*distance+distance/5);
	// }
	public ChessPiece[] getRedPiece() {
		return redPiece;
	}

	public ChessPiece[] getBlackPiece() {
		return blackPiece;
	}

	public void actionPerformed(ActionEvent e) {
		if (step.size() > 0)
			handleMouse.cancelAnStep();
		else
			JOptionPane.showMessageDialog(this, "无法再悔棋", "提示对话框", JOptionPane.WARNING_MESSAGE);
	}
}

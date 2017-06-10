import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class InitCanju implements MouseListener, MouseMotionListener, Serializable {
	Point[][] point;//声明一个point型的二维数组
	ChessBox redChessBox, blackChessBox;
	Point[] redChessBoxPoint, blackChessBoxPoint;//声明俩个point型的一维数组
	int a, b, x0, y0, x, y;
	Point startPoint = null, endPoint = null;//初始化

	public void setPoint(Point[][] point) {
		this.point = point;
	}
	
	public void setRedChessBox(ChessBox redChessBox) {
		this.redChessBox = redChessBox;
		redChessBoxPoint = redChessBox.getBoxPoint();
	}
	
	public void setBlackChessBox(ChessBox blackChessBox) {
		this.blackChessBox = blackChessBox;
		blackChessBoxPoint = blackChessBox.getBoxPoint();
	}
	
	public void mousePressed(MouseEvent e) {//按下鼠标时发生的鼠标事件
		ChessPiece piece = null;
		piece = (ChessPiece) e.getSource();
		a = piece.getBounds().x;//棋子所在方格左上角的横坐标
		b = piece.getBounds().y;//棋子所在方格左上角的纵坐标
		x0 = e.getX();//开始时鼠标横坐标
		y0 = e.getY();//开始时鼠标纵坐标
		startPoint = piece.getAtPoint();//棋子初始位置，即按下鼠标时的位置
	}

	public void mouseMoved(MouseEvent e) {//空，因为必须重写所有方法
	}

	public void mouseDragged(MouseEvent e) {//拖动时的事件
		ChessPiece piece = null;//初始化
		piece = (ChessPiece) e.getSource();//piece为鼠标事件的来源，即需要移动的棋子
		Container con = (Container) piece.getParent();//新出一个容器
		if (con instanceof JLayeredPane)// 拖动棋子放到棋盘上后，修改棋子的层次为JLayeredPane.PALETTE_LAYER，（原层次为JLayeredPane.DEFAULT_LAYER），防止棋子被遮挡。
			((JLayeredPane) con).setLayer(piece, JLayeredPane.DRAG_LAYER);
		a = piece.getBounds().x;//棋子所在方格左上角的横坐标
		b = piece.getBounds().y;//棋子所在方格左上角的纵坐标
		x = e.getX();//移动后鼠标横坐标
		y = e.getY();//移动后鼠标纵坐标
		a = a + x;
		b = b + y;
		piece.setLocation(a - x0, b - y0);//使棋子与鼠标同步移动
	}

	public void mouseReleased(MouseEvent e) {//释放鼠标按键时的事件
		ChessPiece piece = null;//初始化
		piece = (ChessPiece) e.getSource();//piece为鼠标事件的来源，即需要放下的棋子
		int w = piece.getBounds().width;//棋子宽度
		int h = piece.getBounds().height;//棋子高度
		Container con = (Container) piece.getParent();
		if (con instanceof JLayeredPane)  //使释放后棋子位于底层，可被顶层拖动中的棋子覆盖
			((JLayeredPane) con).setLayer(piece, JLayeredPane.PALETTE_LAYER);
		Rectangle rect = piece.getBounds();//使rect为该棋子坐在位置方格
		for (int i = 0; i < point.length; i++) {//在point二维数组中先纵向再横向循环，若重合则使endpoint为point数组中的一个位置
			for (int j = 0; j < point[i].length; j++) {
				if (rect.contains(point[i][j].getX(), point[i][j].getY())) {
					if (piece.getName().equals("兵")) { //修改代码使得用户在布置残局时，某些棋子的位置必须遵守一些规矩。
						if (i < 5 || i < 7 && j % 2 == 0)
							endPoint = point[i][j];
					} else if (piece.getName().equals("帅")) {
						if (i <= 9 && i >= 7 && j <= 5 && j >= 3)
							endPoint = point[i][j];
					} else if (piece.getName().equals("士")) {
						if ((i == 7 || i == 9) && (j == 3 || j == 5) || i == 8 && j == 4)
							endPoint = point[i][j];
					} else if (piece.getName().equals("相")) {
						if ((i == 5 || i == 9) && (j == 2 || j == 6) || i == 7 && (j == 0 || j == 4 || j == 8))
							endPoint = point[i][j];
					} else if (piece.getName().equals("卒")) {
						if (i > 4 || i > 2 && j % 2 == 0)
							endPoint = point[i][j];
					} else if (piece.getName().equals("将")) {
						if (i <= 2 && i >= 0 && j <= 5 && j >= 3)
							endPoint = point[i][j];
					} else if (piece.getName().equals("仕")) {
						if ((i == 0 || i == 2) && (j == 3 || j == 5) || i == 1 && j == 4)
							endPoint = point[i][j];
					} else if (piece.getName().equals("象")) {
						if ((i == 0 || i == 4) && (j == 2 || j == 6) || i == 2 && (j == 0 || j == 4 || j == 8))
							endPoint = point[i][j];
					} else
						endPoint = point[i][j];
					break;
				}
			}
		}
		if (piece.getIsRed()) {//使红棋子移动回棋盒
			for (int i = 0; i < redChessBoxPoint.length; i++) {
				if (rect.contains(redChessBoxPoint[i].getX(), redChessBoxPoint[i].getY())) {
					endPoint = redChessBoxPoint[i];
					break;
				}
			}
		} else if (piece.getIsBlack()) {//使黑棋子移动回棋盒
			for (int i = 0; i < blackChessBoxPoint.length; i++) {
				if (rect.contains(blackChessBoxPoint[i].getX(), blackChessBoxPoint[i].getY())) {
					endPoint = blackChessBoxPoint[i];
					break;
				}
			}
		}
		if (endPoint != null && endPoint.isHaveChessPiece() == false) {//如果要移动去的位置有意义且上面没有棋子，即成功可以移动
			piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
			piece.setAtPoint(endPoint);
			endPoint.setHaveChessPiece(true);
			endPoint.setChessPiece(piece);
			startPoint.setHaveChessPiece(false);
			endPoint = null;
		} else//如果不是，棋子回到原来的位置
			piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
	}

	public void mouseEntered(MouseEvent e) {}//重写所有方法
	public void mouseExited(MouseEvent e) {}//重写所有方法
	public void mouseClicked(MouseEvent e) {}//重写所有方法
}
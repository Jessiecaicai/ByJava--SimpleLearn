
import java.awt.*;
import javax.swing.*;

/*
 * MainBoard 类，我们新建的类，是一个棋盘，为了不让背景图片遮挡棋盘。
 */



public class MainBoard extends JPanel {
	int distance;
	int m, n;
	Point playPoint[][];
	Point boxpoint[][];
	ChessPiece redPiece[], blackPiece[];
	ChessBox redChessBox, blackChessBox;
	String[] redName = { "帅", "士", "士", "相", "相", "马", "马", "车", "车", "炮", "炮", "兵", "兵", "兵", "兵", "兵" };
	String[] blackName = { "将", "仕", "仕", "象", "象", "馬", "馬", "車", "車", "炮", "炮", "卒", "卒", "卒", "卒", "卒" };

	MainBoard() {
		m = 10;
		n = 9;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}

	public void setPlayPoint(Point playPoint[][]) {
		this.playPoint = playPoint;
	}

	public void setRedPiece(ChessPiece piece[]) {
		redPiece = piece;
	}

	public void setBlackPiece(ChessPiece piece[]) {
		blackPiece = piece;
	}

	public void setRedChessBox(ChessBox box) {
		redChessBox = box;
	}

	public void setBlackChessBox(ChessBox box) {
		blackChessBox = box;
	}

	public void paintComponent(Graphics g) {
		// TODO Auto-generated method stub
		super.paintComponent(g);
		g.setColor(Color.orange); // 同上
		g.fillRect(playPoint[0][0].x, playPoint[0][0].y, 8 * distance, 9 * distance);
		g.setColor(Color.black); // 设置线的颜色
		for (int i = 0; i <= m - 1; i++) { // 画出棋盘上的横线
			g.drawLine(playPoint[i][0].x, playPoint[i][0].y, playPoint[i][n - 1].x, playPoint[i][n - 1].y);
		}
		for (int j = 0; j <= n - 1; j++) { // 画出竖线
			if (j > 0 && j < n - 1) { // 画出除边界以外的竖线
				g.drawLine(playPoint[0][j].x, playPoint[0][j].y, playPoint[4][j].x, playPoint[4][j].y);
				g.drawLine(playPoint[5][j].x, playPoint[5][j].y, playPoint[m - 1][j].x, playPoint[m - 1][j].y);
			} else { // 画出边界的竖线
				g.drawLine(playPoint[0][j].x, playPoint[0][j].y, playPoint[m - 1][j].x, playPoint[m - 1][j].y);
			}
		}
		// 画斜线
		
		
		g.drawLine(playPoint[0][3].x, playPoint[0][3].y, playPoint[2][5].x, playPoint[2][5].y);
		g.drawLine(playPoint[0][5].x, playPoint[0][5].y, playPoint[2][3].x, playPoint[2][3].y);
		g.drawLine(playPoint[7][3].x, playPoint[7][3].y, playPoint[m - 1][5].x, playPoint[m - 1][5].y);
		g.drawLine(playPoint[7][5].x, playPoint[7][5].y, playPoint[m - 1][3].x, playPoint[m - 1][3].y);
		int w = blackPiece[0].getBounds().width;
		int h = blackPiece[0].getBounds().height;
		g.setFont(new Font("隶书", Font.BOLD, 35));//加上楚河汉界字样
		g.drawString("楚 河", playPoint[5][1].getX(),playPoint[5][1].getY());
		g.drawString("汉 界", playPoint[5][5].getX(),playPoint[5][6].getY());
	
		
		g.setFont(new Font("隶书", Font.BOLD, 30));
		//int w = blackPiece[0].getBounds().width;
		//int h = blackPiece[0].getBounds().height;
		Point[] boxPoint = blackChessBox.getBoxPoint();
		g.drawString("黑棋盒", boxPoint[5].getX()- w / 2, boxPoint[5].getY());
		//g.drawRect(boxPoint[0].getX() - w / 2 - 2, boxPoint[0].getY() - 2 * h / 3, 4 * distance + 5, 4 * distance + 8); // 画出黑棋盒的矩形边界
		g.setColor(Color.red);
		boxPoint = redChessBox.getBoxPoint();
		w = redPiece[0].getBounds().width;
		h = redPiece[0].getBounds().height;
		g.drawString("红棋盒", boxPoint[5].getX()- w / 2, boxPoint[5].getY());
		//g.drawRect(boxPoint[0].getX() - w / 2 - 2, boxPoint[0].getY() - 2 * h / 3, 4 * distance + 5, 4 * distance + 8);// 用红线画出红棋盒的矩形边界
		g.setColor(Color.yellow);
		for (int j = 1; j <= n; j++) { // 写上下两边的数字
			g.drawString("" + j, (1 + j) * distance - 5, 2 * distance / 5 + 2);
			g.drawString("" + j, (1 + j) * distance - 5, 11 * distance - 5);
		}
		int t = 1;
		for (char c = 'a'; c <= 'j'; c++, t++) // 写左边的字母
			g.drawString("" + c, distance + 3, t * distance + distance / 5);
	}

}


import java.awt.*;
import javax.swing.*;

/*
 * MainBoard �࣬�����½����࣬��һ�����̣�Ϊ�˲��ñ���ͼƬ�ڵ����̡�
 */



public class MainBoard extends JPanel {
	int distance;
	int m, n;
	Point playPoint[][];
	Point boxpoint[][];
	ChessPiece redPiece[], blackPiece[];
	ChessBox redChessBox, blackChessBox;
	String[] redName = { "˧", "ʿ", "ʿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��" };
	String[] blackName = { "��", "��", "��", "��", "��", "�R", "�R", "܇", "܇", "��", "��", "��", "��", "��", "��", "��" };

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
		g.setColor(Color.orange); // ͬ��
		g.fillRect(playPoint[0][0].x, playPoint[0][0].y, 8 * distance, 9 * distance);
		g.setColor(Color.black); // �����ߵ���ɫ
		for (int i = 0; i <= m - 1; i++) { // ���������ϵĺ���
			g.drawLine(playPoint[i][0].x, playPoint[i][0].y, playPoint[i][n - 1].x, playPoint[i][n - 1].y);
		}
		for (int j = 0; j <= n - 1; j++) { // ��������
			if (j > 0 && j < n - 1) { // �������߽����������
				g.drawLine(playPoint[0][j].x, playPoint[0][j].y, playPoint[4][j].x, playPoint[4][j].y);
				g.drawLine(playPoint[5][j].x, playPoint[5][j].y, playPoint[m - 1][j].x, playPoint[m - 1][j].y);
			} else { // �����߽������
				g.drawLine(playPoint[0][j].x, playPoint[0][j].y, playPoint[m - 1][j].x, playPoint[m - 1][j].y);
			}
		}
		// ��б��
		
		
		g.drawLine(playPoint[0][3].x, playPoint[0][3].y, playPoint[2][5].x, playPoint[2][5].y);
		g.drawLine(playPoint[0][5].x, playPoint[0][5].y, playPoint[2][3].x, playPoint[2][3].y);
		g.drawLine(playPoint[7][3].x, playPoint[7][3].y, playPoint[m - 1][5].x, playPoint[m - 1][5].y);
		g.drawLine(playPoint[7][5].x, playPoint[7][5].y, playPoint[m - 1][3].x, playPoint[m - 1][3].y);
		int w = blackPiece[0].getBounds().width;
		int h = blackPiece[0].getBounds().height;
		g.setFont(new Font("����", Font.BOLD, 35));//���ϳ��Ӻ�������
		g.drawString("�� ��", playPoint[5][1].getX(),playPoint[5][1].getY());
		g.drawString("�� ��", playPoint[5][5].getX(),playPoint[5][6].getY());
	
		
		g.setFont(new Font("����", Font.BOLD, 30));
		//int w = blackPiece[0].getBounds().width;
		//int h = blackPiece[0].getBounds().height;
		Point[] boxPoint = blackChessBox.getBoxPoint();
		g.drawString("�����", boxPoint[5].getX()- w / 2, boxPoint[5].getY());
		//g.drawRect(boxPoint[0].getX() - w / 2 - 2, boxPoint[0].getY() - 2 * h / 3, 4 * distance + 5, 4 * distance + 8); // ��������еľ��α߽�
		g.setColor(Color.red);
		boxPoint = redChessBox.getBoxPoint();
		w = redPiece[0].getBounds().width;
		h = redPiece[0].getBounds().height;
		g.drawString("�����", boxPoint[5].getX()- w / 2, boxPoint[5].getY());
		//g.drawRect(boxPoint[0].getX() - w / 2 - 2, boxPoint[0].getY() - 2 * h / 3, 4 * distance + 5, 4 * distance + 8);// �ú��߻�������еľ��α߽�
		g.setColor(Color.yellow);
		for (int j = 1; j <= n; j++) { // д�������ߵ�����
			g.drawString("" + j, (1 + j) * distance - 5, 2 * distance / 5 + 2);
			g.drawString("" + j, (1 + j) * distance - 5, 11 * distance - 5);
		}
		int t = 1;
		for (char c = 'a'; c <= 'j'; c++, t++) // д��ߵ���ĸ
			g.drawString("" + c, distance + 3, t * distance + distance / 5);
	}

}

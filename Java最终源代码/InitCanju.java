import java.awt.event.*;
import java.awt.*;
import javax.swing.*;
import java.io.*;


public class InitCanju implements MouseListener, MouseMotionListener, Serializable {
	Point[][] point;//����һ��point�͵Ķ�ά����
	ChessBox redChessBox, blackChessBox;
	Point[] redChessBoxPoint, blackChessBoxPoint;//��������point�͵�һά����
	int a, b, x0, y0, x, y;
	Point startPoint = null, endPoint = null;//��ʼ��

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
	
	public void mousePressed(MouseEvent e) {//�������ʱ����������¼�
		ChessPiece piece = null;
		piece = (ChessPiece) e.getSource();
		a = piece.getBounds().x;//�������ڷ������Ͻǵĺ�����
		b = piece.getBounds().y;//�������ڷ������Ͻǵ�������
		x0 = e.getX();//��ʼʱ��������
		y0 = e.getY();//��ʼʱ���������
		startPoint = piece.getAtPoint();//���ӳ�ʼλ�ã����������ʱ��λ��
	}

	public void mouseMoved(MouseEvent e) {//�գ���Ϊ������д���з���
	}

	public void mouseDragged(MouseEvent e) {//�϶�ʱ���¼�
		ChessPiece piece = null;//��ʼ��
		piece = (ChessPiece) e.getSource();//pieceΪ����¼�����Դ������Ҫ�ƶ�������
		Container con = (Container) piece.getParent();//�³�һ������
		if (con instanceof JLayeredPane)// �϶����ӷŵ������Ϻ��޸����ӵĲ��ΪJLayeredPane.PALETTE_LAYER����ԭ���ΪJLayeredPane.DEFAULT_LAYER������ֹ���ӱ��ڵ���
			((JLayeredPane) con).setLayer(piece, JLayeredPane.DRAG_LAYER);
		a = piece.getBounds().x;//�������ڷ������Ͻǵĺ�����
		b = piece.getBounds().y;//�������ڷ������Ͻǵ�������
		x = e.getX();//�ƶ�����������
		y = e.getY();//�ƶ������������
		a = a + x;
		b = b + y;
		piece.setLocation(a - x0, b - y0);//ʹ���������ͬ���ƶ�
	}

	public void mouseReleased(MouseEvent e) {//�ͷ���갴��ʱ���¼�
		ChessPiece piece = null;//��ʼ��
		piece = (ChessPiece) e.getSource();//pieceΪ����¼�����Դ������Ҫ���µ�����
		int w = piece.getBounds().width;//���ӿ��
		int h = piece.getBounds().height;//���Ӹ߶�
		Container con = (Container) piece.getParent();
		if (con instanceof JLayeredPane)  //ʹ�ͷź�����λ�ڵײ㣬�ɱ������϶��е����Ӹ���
			((JLayeredPane) con).setLayer(piece, JLayeredPane.PALETTE_LAYER);
		Rectangle rect = piece.getBounds();//ʹrectΪ����������λ�÷���
		for (int i = 0; i < point.length; i++) {//��point��ά�������������ٺ���ѭ�������غ���ʹendpointΪpoint�����е�һ��λ��
			for (int j = 0; j < point[i].length; j++) {
				if (rect.contains(point[i][j].getX(), point[i][j].getY())) {
					if (piece.getName().equals("��")) { //�޸Ĵ���ʹ���û��ڲ��òо�ʱ��ĳЩ���ӵ�λ�ñ�������һЩ��ء�
						if (i < 5 || i < 7 && j % 2 == 0)
							endPoint = point[i][j];
					} else if (piece.getName().equals("˧")) {
						if (i <= 9 && i >= 7 && j <= 5 && j >= 3)
							endPoint = point[i][j];
					} else if (piece.getName().equals("ʿ")) {
						if ((i == 7 || i == 9) && (j == 3 || j == 5) || i == 8 && j == 4)
							endPoint = point[i][j];
					} else if (piece.getName().equals("��")) {
						if ((i == 5 || i == 9) && (j == 2 || j == 6) || i == 7 && (j == 0 || j == 4 || j == 8))
							endPoint = point[i][j];
					} else if (piece.getName().equals("��")) {
						if (i > 4 || i > 2 && j % 2 == 0)
							endPoint = point[i][j];
					} else if (piece.getName().equals("��")) {
						if (i <= 2 && i >= 0 && j <= 5 && j >= 3)
							endPoint = point[i][j];
					} else if (piece.getName().equals("��")) {
						if ((i == 0 || i == 2) && (j == 3 || j == 5) || i == 1 && j == 4)
							endPoint = point[i][j];
					} else if (piece.getName().equals("��")) {
						if ((i == 0 || i == 4) && (j == 2 || j == 6) || i == 2 && (j == 0 || j == 4 || j == 8))
							endPoint = point[i][j];
					} else
						endPoint = point[i][j];
					break;
				}
			}
		}
		if (piece.getIsRed()) {//ʹ�������ƶ������
			for (int i = 0; i < redChessBoxPoint.length; i++) {
				if (rect.contains(redChessBoxPoint[i].getX(), redChessBoxPoint[i].getY())) {
					endPoint = redChessBoxPoint[i];
					break;
				}
			}
		} else if (piece.getIsBlack()) {//ʹ�������ƶ������
			for (int i = 0; i < blackChessBoxPoint.length; i++) {
				if (rect.contains(blackChessBoxPoint[i].getX(), blackChessBoxPoint[i].getY())) {
					endPoint = blackChessBoxPoint[i];
					break;
				}
			}
		}
		if (endPoint != null && endPoint.isHaveChessPiece() == false) {//���Ҫ�ƶ�ȥ��λ��������������û�����ӣ����ɹ������ƶ�
			piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
			piece.setAtPoint(endPoint);
			endPoint.setHaveChessPiece(true);
			endPoint.setChessPiece(piece);
			startPoint.setHaveChessPiece(false);
			endPoint = null;
		} else//������ǣ����ӻص�ԭ����λ��
			piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
	}

	public void mouseEntered(MouseEvent e) {}//��д���з���
	public void mouseExited(MouseEvent e) {}//��д���з���
	public void mouseClicked(MouseEvent e) {}//��д���з���
}
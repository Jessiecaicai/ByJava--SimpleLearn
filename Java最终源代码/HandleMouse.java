import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;




public class HandleMouse implements MouseListener, MouseMotionListener, Serializable {
//ʵ���˼������ӿڣ�MouseListener,MouseMotionListener��Serializable 
//Serializable��һ�������л����࣬�������Ա�д���ļ���Ҳ�ɴ��ļ��ж���������������Ķ���
	Point[][] point;
	ChessBox redChessBox, blackChessBox;
	MoveRule rule;
	ArrayList<Point> step;
	int a, b, x0, y0, x, y;
	boolean redFirstMove, blackFirstMove, isPlaypoint;
	Point startPoint = null, endPoint = null;
	File music1, music2, music3;//����File����
	URI uri[];//����URI����
	URL url[];//����URL����
	AudioClip clip[];//������Ƶ���󡣵�AudioClip�ӿڲ�����������һ���򵥵ĳ���
	//�����������Ŀ����ͬʱ���ţ����ɴ˲��������������һ�����һ�����ϡ�

	HandleMouse() {
		redFirstMove = true;
		blackFirstMove = false;
		rule = new MoveRule();
		music1 = new File("sound.wav");//����File����
		music2 = new File("sound1.wav");
		music3 = new File("sound2.wav");
		uri = new URI[3];//����URL����
		uri[0] = music1.toURI();//����һ���ļ���URI��ʾ�˳���·������
		uri[1] = music2.toURI();
		uri[2] = music3.toURI();
		url = new URL[3];
		clip = new AudioClip[3];//������Ƶ����
		
		try {
			for (int i = 0; i < uri.length; i++) {
				url[i] = uri[i].toURL();
				clip[i] = Applet.newAudioClip(url[i]);//�Ӹ����ĵ�ַ��ȡ��Ƶ��
			}
		} catch (MalformedURLException e) {//�׳���ʾ�Ѿ��������ε�URL��
			e.printStackTrace();
		}
	}
	
	public void setClipNull() {//����������ʱ�������setClipNull()��ʹ���������������š�
		for (int i = 0; i < clip.length; i++){
			clip[i] = null;
		}
	}
	
	public void setClip() {
		for (int i = 0; i < clip.length; i++){
			clip[i] = Applet.newAudioClip(url[i]);
		}
	}
	
	public void setStep(ArrayList<Point> step) {
		this.step = step;
	}
	//����һ��Point���͵Ķ�̬���飬���Զ�̬����ɾԪ�أ�û�пռ����ƣ�ArrayList������¼�߷�
	public void setRedFirstMove(boolean boo) {
		redFirstMove = true;
		blackFirstMove = false;
	}
	//���ƺ췽����
	public void setPoint(Point[][] point) {
		this.point = point;
		rule.setPoint(point);
	}
	
	public void setRedChessBox(ChessBox redChessBox) {
		this.redChessBox = redChessBox;
	}
	
	public void setBlackChessBox(ChessBox blackChessBox) {
		this.blackChessBox = blackChessBox;
	}
	
	public void initStep() {
		step.clear();
	}
	//���壬���ø÷������������һ��
	public ArrayList<Point> getStep() {
		return step;
	}//��¼
    	//������괥���¼�
	public void mousePressed(MouseEvent e) {
		ChessPiece piece = null;//�����������Ϊ��
		piece = (ChessPiece) e.getSource();//������갴�µ�����λ��
		a = piece.getBounds().x;
		b = piece.getBounds().y;
		x0 = e.getX();
		y0 = e.getY();
		startPoint = piece.getAtPoint();//��ȡ����λ��
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				if (point[i][j].equals(startPoint))
					isPlaypoint = true;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {}//����ƶ��¼����գ�
	//�϶�����¼�
	public void mouseDragged(MouseEvent e) {
		ChessPiece piece = null;
		piece = (ChessPiece) e.getSource();
		Container con = (Container) piece.getParent();
		a = piece.getBounds().x;
		b = piece.getBounds().y;
		x = e.getX();
		y = e.getY();
		a = a + x;
		b = b + y;
		if (con instanceof JLayeredPane)//�������con��ͼ������ʵ��
			((JLayeredPane) con).setLayer(piece, JLayeredPane.DRAG_LAYER);
		if (piece.getIsRed() && redFirstMove)
			piece.setLocation(a - x0, b - y0);
		if (piece.getIsBlack() && blackFirstMove)
			piece.setLocation(a - x0, b - y0);
	}
	 //�ͷ�����¼������壬�Ե����ӣ���¼�߷���
	public void mouseReleased(MouseEvent e) {
		ChessPiece piece = null;
		piece = (ChessPiece) e.getSource();//getSource,�����¼�Դ
		int w = piece.getBounds().width;
		int h = piece.getBounds().height;
		Container con = (Container) piece.getParent();
		if (con instanceof JLayeredPane)
			((JLayeredPane) con).setLayer(piece, JLayeredPane.PALETTE_LAYER);
		Rectangle rect = piece.getBounds();
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				if (rect.contains(point[i][j].getX(), point[i][j].getY())) {
					endPoint = point[i][j];
					break;
				}
			}
		}
		if (endPoint != null && isPlaypoint) {
			if (endPoint.isHaveChessPiece()) {
				if (endPoint.getChessPiece().getIsRed() == startPoint.getChessPiece().getIsRed())
					piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				else {
					boolean ok = rule.movePieceRule(piece, startPoint, endPoint);
					if (ok) {
						ChessPiece backToBoxPiece = endPoint.getChessPiece();
						Point boxPoint = null;
						if (backToBoxPiece.getIsRed())
							boxPoint = redChessBox.putPieceToBox(backToBoxPiece);
						else if (backToBoxPiece.getIsBlack())
							boxPoint = blackChessBox.putPieceToBox(backToBoxPiece);
						piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
						piece.setAtPoint(endPoint);
						endPoint.setHaveChessPiece(true);
						endPoint.setChessPiece(piece);
						startPoint.setHaveChessPiece(false);
						step.add(startPoint);
						step.add(endPoint);
						step.add(boxPoint);
						if (piece.getIsRed()) {
							redFirstMove = false;
							blackFirstMove = true;
						} else if (piece.getIsBlack()) {
							blackFirstMove = false;
							redFirstMove = true;
						}
						if (clip != null) {//���ӵ�ʱ��
							clip[1].play();
						}
						if (boxPoint.getChessPiece().getName().equals("��")|| boxPoint.getChessPiece().getName().equals("˧")) {
							if (boxPoint.getChessPiece().isBlack) {
								JOptionPane.showMessageDialog((Container) piece.getParent(), "����ʤ");
								blackFirstMove = false;
							} else {
								JOptionPane.showMessageDialog((Container) piece.getParent(), "����ʤ", "��ʾ",JOptionPane.INFORMATION_MESSAGE);
								redFirstMove = false;
							}
						}
					} else
						piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				}
			} else {
				boolean ok = rule.movePieceRule(piece, startPoint, endPoint);
				if (ok) {
					piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
					piece.setAtPoint(endPoint);
					endPoint.setHaveChessPiece(true);
					endPoint.setChessPiece(piece);
					startPoint.setHaveChessPiece(false);
					step.add(startPoint);
					step.add(endPoint);
					step.add(null);
					if (piece.getIsRed()) {
						redFirstMove = false;
						blackFirstMove = true;
					} else if (piece.getIsBlack()) {
						blackFirstMove = false;
						redFirstMove = true;
					}
					if (clip != null) {//
						clip[0].play();
					}
				} else
					piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
			}
		} else
			piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
		if (startPoint.equals(piece.getAtPoint()) && clip != null) {
			clip[2].play();
		}
	}
	
	public void cancelAnStep() {
		int length = step.size();
		if (length >= 3) {
			Point boxPoint = step.get(length - 1);
			Point endPoint = step.get(length - 2);
			Point startPoint = step.get(length - 3);
			ChessPiece piece = null;
			if (boxPoint != null) {
				piece = endPoint.getChessPiece();
				if (piece.getIsRed()) {
					redFirstMove = true;
					blackFirstMove = false;
				} else if (piece.getIsBlack()) {
					blackFirstMove = true;
					redFirstMove = false;
				}
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				piece.setAtPoint(startPoint);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
				piece = boxPoint.getChessPiece();
				w = piece.getBounds().width;
				h = piece.getBounds().height;
				piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
				piece.setAtPoint(endPoint);
				endPoint.setHaveChessPiece(true);
				endPoint.setChessPiece(piece);
				boxPoint.setHaveChessPiece(false);
			} else {
				piece = endPoint.getChessPiece();
				if (piece.getIsRed()) {
					redFirstMove = true;
					blackFirstMove = false;
				} else if (piece.getIsBlack()) {
					blackFirstMove = true;
					redFirstMove = false;
				}
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				piece.setAtPoint(startPoint);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
			}
			step.remove(step.size() - 1);
			step.remove(step.size() - 1);
			step.remove(step.size() - 1);
		}
	}
	
	public void mouseEntered(MouseEvent e) {}
	public void mouseExited(MouseEvent e) {}
	public void mouseClicked(MouseEvent e) {}
}
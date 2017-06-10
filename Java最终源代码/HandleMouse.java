import java.applet.*;
import java.awt.event.*;
import java.awt.*;
import java.util.*;
import javax.swing.*;
import java.io.*;
import java.net.*;




public class HandleMouse implements MouseListener, MouseMotionListener, Serializable {
//实现了监听器接口，MouseListener,MouseMotionListener，Serializable 
//Serializable是一个可序列化的类，其对象可以被写入文件，也可从文件中读出，用来传送类的对象
	Point[][] point;
	ChessBox redChessBox, blackChessBox;
	MoveRule rule;
	ArrayList<Point> step;
	int a, b, x0, y0, x, y;
	boolean redFirstMove, blackFirstMove, isPlaypoint;
	Point startPoint = null, endPoint = null;
	File music1, music2, music3;//声明File对象
	URI uri[];//声明URI对象
	URL url[];//声明URL对象
	AudioClip clip[];//声明音频对象。的AudioClip接口播放声音剪辑一个简单的抽象。
	//多个剪辑的项目可以同时播放，和由此产生的声音混合在一起产生一个复合。

	HandleMouse() {
		redFirstMove = true;
		blackFirstMove = false;
		rule = new MoveRule();
		music1 = new File("sound.wav");//创建File对象
		music2 = new File("sound1.wav");
		music3 = new File("sound2.wav");
		uri = new URI[3];//创建URL对象
		uri[0] = music1.toURI();//建立一个文件：URI表示此抽象路径名。
		uri[1] = music2.toURI();
		uri[2] = music3.toURI();
		url = new URL[3];
		clip = new AudioClip[3];//创建音频对象
		
		try {
			for (int i = 0; i < uri.length; i++) {
				url[i] = uri[i].toURL();
				clip[i] = Applet.newAudioClip(url[i]);//从给定的地址获取音频。
			}
		} catch (MalformedURLException e) {//抛出表示已经发生畸形的URL。
			e.printStackTrace();
		}
	}
	
	public void setClipNull() {//当保存棋谱时，会调用setClipNull()，使得音乐能正常播放。
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
	//定义一个Point类型的动态数组，可以动态的增删元素，没有空间限制（ArrayList），记录走法
	public void setRedFirstMove(boolean boo) {
		redFirstMove = true;
		blackFirstMove = false;
	}
	//控制红方先手
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
	//悔棋，调用该方法即可清空上一步
	public ArrayList<Point> getStep() {
		return step;
	}//记录
    	//按下鼠标触发事件
	public void mousePressed(MouseEvent e) {
		ChessPiece piece = null;//最初棋子设置为空
		piece = (ChessPiece) e.getSource();//返回鼠标按下的棋子位置
		a = piece.getBounds().x;
		b = piece.getBounds().y;
		x0 = e.getX();
		y0 = e.getY();
		startPoint = piece.getAtPoint();//获取棋子位置
		for (int i = 0; i < point.length; i++) {
			for (int j = 0; j < point[i].length; j++) {
				if (point[i][j].equals(startPoint))
					isPlaypoint = true;
			}
		}
	}
	
	public void mouseMoved(MouseEvent e) {}//鼠标移动事件（空）
	//拖动鼠标事件
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
		if (con instanceof JLayeredPane)//如果容器con是图层面板的实例
			((JLayeredPane) con).setLayer(piece, JLayeredPane.DRAG_LAYER);
		if (piece.getIsRed() && redFirstMove)
			piece.setLocation(a - x0, b - y0);
		if (piece.getIsBlack() && blackFirstMove)
			piece.setLocation(a - x0, b - y0);
	}
	 //释放鼠标事件（下棋，吃掉棋子，记录走法）
	public void mouseReleased(MouseEvent e) {
		ChessPiece piece = null;
		piece = (ChessPiece) e.getSource();//getSource,返回事件源
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
						if (clip != null) {//吃子的时候
							clip[1].play();
						}
						if (boxPoint.getChessPiece().getName().equals("将")|| boxPoint.getChessPiece().getName().equals("帅")) {
							if (boxPoint.getChessPiece().isBlack) {
								JOptionPane.showMessageDialog((Container) piece.getParent(), "红棋胜");
								blackFirstMove = false;
							} else {
								JOptionPane.showMessageDialog((Container) piece.getParent(), "黑棋胜", "提示",JOptionPane.INFORMATION_MESSAGE);
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
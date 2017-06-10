
import javax.swing.*;
import java.util.*;
import java.awt.event.*;

public class ChessBoard extends JLayeredPane implements ActionListener {
	int m = 10, n = 9, distance = 42;
	Point[][] playPoint;
	String[] redName = { "˧", "ʿ", "ʿ", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��", "��" };
	String[] blackName = { "��", "��", "��", "��", "��", "�R", "�R", "܇", "܇", "��", "��", "��", "��", "��", "��", "��" };
	ChessPiece[] redPiece;
	ChessPiece[] blackPiece;	//�������
	ChessBox redChessBox, blackChessBox;
	InitPieceLocation initPieceLocation;
	InitCanju initCanju;
	HandleMouse handleMouse;
	ArrayList<Point> step;		 //���������˰�װ,���沽��
	JButton cancel;		//����

	JLabel label1,label2,label3;	//����ӵĶ���
	MainBoard mainBoard;

	public ChessBoard() {	//���췽��
		setLayout(null);	//nullΪ���ֶ�������������������Լ��Ĳ���
		step = new ArrayList<Point>();		//ArrayList�Ƕ�̬����
		initPointAndPiece();
		redChessBox = new ChessBox(distance, 11, 7);
		blackChessBox = new ChessBox(distance, 11, 1);		//distance�ǳߴ磬�м������ң����������
		for (int i = 0; i < redPiece.length; i++)
			redChessBox.putPieceToBox(redPiece[i]);
		for (int i = 0; i < blackPiece.length; i++)
			blackChessBox.putPieceToBox(blackPiece[i]);		//�����ӷŵ������
		initPieceLocation = new InitPieceLocation();		//��������
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

		cancel = new JButton("����");	//����
		add(cancel);
		cancel.setVisible(false);		//��ʼ������岻�ɼ���ֻ���ڿ�ʼ������֮�����ſɼ�
		cancel.setSize(60, 30);
		cancel.setLocation(13 * distance, (int) (5 * distance));	//����λ��
		cancel.addActionListener(this);		//cancel���¼�Դ��this�Ǽ�����

		mainBoard = new MainBoard();		//��������
		mainBoard.setDistance(distance);
		mainBoard.setPlayPoint(playPoint);
		mainBoard.setRedChessBox(redChessBox);
		mainBoard.setBlackChessBox(blackChessBox);
		
		mainBoard.setRedPiece(redPiece);
		mainBoard.setBlackPiece(blackPiece);
		mainBoard.setBounds(0, 0, 710, 480);
		mainBoard.setOpaque(false);		//���������Ƿ�͸��
		add(mainBoard, JLayeredPane.DEFAULT_LAYER);	//��ӵ�JLayeredPane����ײ�
		label1 = new JLabel(new ImageIcon("default.jpg"));		//���ͼƬ
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

	private void initPointAndPiece() {		//˽�з������ó�ʼ���Ӻ����
		removeAll();	//��������������������ߣ�����JLayerPane�ﶨ��ķ���
		playPoint = new Point[m][n];	//������λ���������ʾ
		int Hspace = distance, Vspace = distance;	//hΪˮƽ��࣬vΪ��ֱ���
		for (int i = 0; i < m; i++) {
			for (int j = 0; j < n; j++) {
				playPoint[i][j] = new Point(distance + Hspace, Vspace);
				playPoint[i][j].setIsPlayPoint(true);
				Hspace = Hspace + distance;
			}
			Hspace = distance;
			Vspace = Vspace + distance;		//����ˮƽÿ��point������setΪplayPoint��Ȼ����ѭ���ٵ���һ��
		}
		redPiece = new ChessPiece[redName.length];	//���ú����ӣ�������ΪchessPiece��Ķ���
		int size = distance;
		for (int i = 0; i < redPiece.length; i++) {
			redPiece[i] = new ChessPiece();
			add(redPiece[i], JLayeredPane.PALETTE_LAYER);	//�Ѻ����ӷŵ������㣬��mainboard֮��
			redPiece[i].setSize(size, size);
			redPiece[i].setIsRed(true);
			redPiece[i].setName(redName[i]);	//chessPiece�еķ������������������
			redPiece[i].repaint();		/*������swing��ķ�������ô��������swing��ķ�������Ϊswing���кܶ���
				ͼ�ν����йصķ����������ǵ���awt��ķ�������ô���ø��µķ������˷����൱��һ����ˢ��*/
		}
		blackPiece = new ChessPiece[blackName.length];	//ͬ��
		for (int i = 0; i < blackPiece.length; i++) {
			blackPiece[i] = new ChessPiece();
			add(blackPiece[i], JLayeredPane.PALETTE_LAYER);
			blackPiece[i].setSize(size, size);
			blackPiece[i].setIsBlack(true);
			blackPiece[i].setName(blackName[i]);
			blackPiece[i].repaint();
		}
	}

	public ArrayList<Point> getStep() {		//step��ArrayList�����
		return step;
	}

	public Point[][] getPoint() {
		return playPoint;
	}

	public void setShizhanPlay() {	//����ʵս���̳�ʼ��ʽ
		handleMouse.setRedFirstMove(true);
		handleMouse.initStep();		//������в���
		cancel.setVisible(false);	//swing�еķ���
		initPieceLocation.putAllPieceToPlayChessArea();		//��ʼʱ�ı�׼�ŷ�
		for (int i = 0; i < redPiece.length; i++) {		//�������������봦����
			MouseListener[] listener = redPiece[i].getMouseListeners();		//��������������е������ΪmouseListen
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

	public void setCanjuPlay() {		//���òо����̳�ʼ����
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
			redPiece[i].addMouseListener(initCanju);	//ע�����������������initCanju
			blackPiece[i].addMouseListener(initCanju);
			redPiece[i].addMouseMotionListener(initCanju);	//ע�������������ӿ�������������
			blackPiece[i].addMouseMotionListener(initCanju);
		}

	}

	public void startMakeManul() {		//��ʼ�����˵�
		cancel.setVisible(true);
		handleMouse.setRedFirstMove(true);
		handleMouse.initStep();		//������в���
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
			redPiece[i].addMouseListener(handleMouse);		//ע�������
			blackPiece[i].addMouseListener(handleMouse);
			redPiece[i].addMouseMotionListener(handleMouse);	//ע�������
			blackPiece[i].addMouseMotionListener(handleMouse);
		}
	}

	// public void paintComponent(Graphics g){
	// super.paintComponent(g);
	// g.setColor(Color.cyan); //����g��ɫ
	// g.fillRect(0,0,this.getBounds().width,this.getBounds().height);//�������õ���ɫ��䵽��ǰ����
	// g.setColor(Color.orange); //ͬ��
	// g.fillRect(playPoint[0][0].x,playPoint[0][0].y,8*distance,9*distance);
	// g.setColor(Color.black); //�����ߵ���ɫ
	// for(int i=0;i<=m-1;i++){ //���������ϵĺ���
	// g.drawLine(playPoint[i][0].x,playPoint[i][0].y,
	// playPoint[i][n-1].x,playPoint[i][n-1].y);
	// }
	// for(int j=0;j<=n-1;j++){ //��������
	// if(j>0&&j<n-1){ //�������߽����������
	// g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
	// playPoint[4][j].x,playPoint[4][j].y);
	// g.drawLine(playPoint[5][j].x,playPoint[5][j].y,
	// playPoint[m-1][j].x,playPoint[m-1][j].y);
	// }
	// else{ //�����߽������
	// g.drawLine(playPoint[0][j].x,playPoint[0][j].y,
	// playPoint[m-1][j].x,playPoint[m-1][j].y);
	// }
	// }
	// //��б��
	// g.drawLine(playPoint[0][3].x,playPoint[0][3].y,
	// playPoint[2][5].x,playPoint[2][5].y);
	// g.drawLine(playPoint[0][5].x,playPoint[0][5].y,
	// playPoint[2][3].x,playPoint[2][3].y);
	// g.drawLine(playPoint[7][3].x,playPoint[7][3].y,
	// playPoint[m-1][5].x,playPoint[m-1][5].y);
	// g.drawLine(playPoint[7][5].x,playPoint[7][5].y,
	// playPoint[m-1][3].x,playPoint[m-1][3].y);
	// g.setFont(new Font("����",Font.BOLD,18));
	// int w=blackPiece[0].getBounds().width;
	// int h=blackPiece[0].getBounds().height;
	// Point [] boxPoint=blackChessBox.getBoxPoint();
	// g.drawString("�����",boxPoint[5].getX(),boxPoint[5].getY());
	// g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
	// 4*distance+5,4*distance+8); //��������еľ��α߽�
	// g.setColor(Color.red);
	// boxPoint=redChessBox.getBoxPoint();
	// w=redPiece[0].getBounds().width;
	// h=redPiece[0].getBounds().height;
	// g.drawString("�����",boxPoint[5].getX(),boxPoint[5].getY());
	// g.drawRect(boxPoint[0].getX()-w/2-2,boxPoint[0].getY()-2*h/3,
	// 4*distance+5,4*distance+8);//�ú��߻�������еľ��α߽�
	// g.setColor(Color.black);
	// for(int j=1;j<=n;j++){ //д�������ߵ�����
	// g.drawString(""+j,(1+j)*distance-5,2*distance/5+2);
	// g.drawString(""+j,(1+j)*distance-5,11*distance-5);
	// }
	// int t=1;
	// for(char c='a';c<='j';c++,t++) //д��ߵ���ĸ
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
			JOptionPane.showMessageDialog(this, "�޷��ٻ���", "��ʾ�Ի���", JOptionPane.WARNING_MESSAGE);
	}
}

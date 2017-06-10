
import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.awt.Image;
import java.io.IOException;
import javax.imageio.ImageIO;


public class ChinaChess extends JFrame implements ActionListener {
	ChessBoard board;
	DemoManulDialog demoManul;
	JMenuBar bar;
	JMenu makeMenu, showMenu,boardMenu;
	JMenuItem makeShiZhanManul, makeCanJuManul, openManulAndShow,optionChessBoard1,optionChessBoard2,optionChessBoard3,optionChessBoard4;
	JButton startMakeManul, saveManul;
	JTextField hintMessage;
	Clock clock;//����ʱ��
	
	private static final long serialVersionUID = 6385933774053272194L; 
	public ChinaChess() {
		String path="/Img/o.jpg";
		try{//����Java����Ĭ��ͼ��
	    	Image img=ImageIO.read(this.getClass().getResource(path));
	    	this.setIconImage(img);
	    }catch (IOException e){
	    	e.printStackTrace();
	    }
		setTitle("�й�������������ϵͳ");
		board = new ChessBoard();
		add(board, BorderLayout.CENTER);
		bar = new JMenuBar();
		makeMenu = new JMenu("ѡ���������׵ķ�ʽ");
		showMenu = new JMenu("����");
		
		makeMenu.setMnemonic('b');
		showMenu.setMnemonic('n');
		boardMenu=new JMenu("�Զ��屳��");
		boardMenu.setMnemonic('m');
		
		makeShiZhanManul = new JMenuItem("����ʵս����",new ImageIcon("b.jpg"));
		makeCanJuManul = new JMenuItem("�����о�����",new ImageIcon("c.png"));
		openManulAndShow = new JMenuItem("��һ�����ײ���ʾ",new ImageIcon("a.jpg"));
		
		
		optionChessBoard1=new JMenuItem("����1");
		optionChessBoard2=new JMenuItem("����2");
		optionChessBoard3=new JMenuItem("����3");
		optionChessBoard4=new JMenuItem("Ĭ��");
		
		
		makeMenu.add(makeShiZhanManul);
		makeMenu.add(makeCanJuManul);
		showMenu.add(openManulAndShow);
		boardMenu.add(optionChessBoard1);
		boardMenu.add(optionChessBoard2);
		boardMenu.add(optionChessBoard3);
		boardMenu.add(optionChessBoard4);
		bar.add(makeMenu);
		bar.add(showMenu);
		bar.add(boardMenu);
		setJMenuBar(bar);
		makeShiZhanManul.addActionListener(this);
		makeCanJuManul.addActionListener(this);
		openManulAndShow.addActionListener(this);
		optionChessBoard1.addActionListener(this);
		optionChessBoard2.addActionListener(this);
		optionChessBoard3.addActionListener(this);
		optionChessBoard4.addActionListener(this);
		startMakeManul = new JButton("��ʼ��������");
		startMakeManul.setVisible(false);
		startMakeManul.addActionListener(this);
		
		saveManul = new JButton("��������");
		saveManul.setVisible(false);
		saveManul.addActionListener(this);
		
		hintMessage = new JTextField(30);
		hintMessage.setHorizontalAlignment(JTextField.CENTER);
		hintMessage.setFont(new Font("ϸ��", Font.PLAIN, 15));
		hintMessage.setEditable(false);
		hintMessage.setText("�뵥���˵�,ѡ���������׵ķ�ʽ����ʾ���е�����");
		hintMessage.setBackground(Color.GRAY);
		JPanel north = new JPanel();
		north.add(hintMessage);
		north.add(startMakeManul);
		north.add(saveManul);
		add(north, BorderLayout.NORTH);
		setVisible(true);
		setBounds(120, 10, 710, 580);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		validate();
		JOptionPane.showMessageDialog(this, "�뵥���˵�,ѡ���������׵ķ�ʽ����ʾ���е�����", "��ʾ�Ի���", JOptionPane.WARNING_MESSAGE);
		addWindowFocusListener(new WindowFocusListener() { 
			 
            @Override 
            public void windowGainedFocus(WindowEvent e) { 
              
                System.out.println("���ڻ���˽��㣡"); 
            } 
 
            @Override 
            public void windowLostFocus(WindowEvent e) { 
                // TODO Auto-generated method stub  
                System.out.println("����ʧȥ�˽��㣡"); 
            } 
             
        }); 
		
		
		
		//����ʱ��
		clock=new Clock();
		JPanel south = new JPanel();
		south.add(clock);
		add(south, BorderLayout.SOUTH);
		setVisible(true);

	
	
	
	}

	public void actionPerformed(ActionEvent e) {
		String message = "����\"��ʼ��������\",Ȼ���϶���������";
		if (e.getSource() == makeShiZhanManul) {
			board.setShizhanPlay();
			hintMessage.setText(message);
			startMakeManul.setVisible(true);
			saveManul.setVisible(false);
			JOptionPane.showMessageDialog(this, message, "��ʾ�Ի���", JOptionPane.WARNING_MESSAGE);
		}
		if (e.getSource() == makeCanJuManul) {
			message = "������������������̰ںòо�,Ȼ�󵥻�\"��ʼ��������\"";
			hintMessage.setText(message);
			board.setCanjuPlay();
			startMakeManul.setVisible(true);
			saveManul.setVisible(false);
			JOptionPane.showMessageDialog(this, message, "��ʾ�Ի���", JOptionPane.WARNING_MESSAGE);
		}
		/*
		 * �޸Ĵ���ʹ���ڲ��òо�ʱ�����н���˧��
		 */
		if (e.getSource() == startMakeManul) {
			boolean judge = false;
			Point[] boxPoint = new Point[board.redChessBox.boxPoint.length];
			boxPoint = board.redChessBox.getBoxPoint();
			for (int i = 0; i < board.redChessBox.boxPoint.length; i++) {
				if (boxPoint[i].haveChessPiece) {
					if (boxPoint[i].getChessPiece().getName().equals("˧")) {
						judge = true;
						break;
					}
				}
			}
			if (judge == false) {
				boxPoint = board.blackChessBox.getBoxPoint();
				for (int i = 0; i < board.blackChessBox.boxPoint.length; i++) {
					if (boxPoint[i].haveChessPiece) {
						if (boxPoint[i].getChessPiece().getName().equals("��")) {
							judge = true;
							break;
						}
					}

				}
			}
			if (judge) {
				message = "ȱ��˧��!";
				JOptionPane.showMessageDialog(this, message, "��ʾ�Ի���", JOptionPane.WARNING_MESSAGE);
			} else {
				board.startMakeManul();
				hintMessage.setText("����\"��������\"��ť���Ա�������");
				saveManul.setVisible(true);
				startMakeManul.setVisible(false);
			}
		}
		/*
		 * �޸Ĵ���,����������ʱ������setClipNull()��Ϊ��֮����˳����ȡ�� ������֮�󣬵���setClip()��ʹ���������������š�
		 */
		if (e.getSource() == saveManul) {
			startMakeManul.setVisible(false);
			JFileChooser chooser = new JFileChooser();
			int state = chooser.showSaveDialog(null);
			File file = chooser.getSelectedFile();
			if (file != null && state == JFileChooser.APPROVE_OPTION) {
				
				board.handleMouse.setClipNull();
				try {
					FileOutputStream out = new FileOutputStream(file);
					ObjectOutputStream objectOut = new ObjectOutputStream(out);
					objectOut.writeObject(board);
					out.close();
					objectOut.close();
				}
				catch (Exception event) {
				}
				board.handleMouse.setClip();
			}
		}
		if (e.getSource() == openManulAndShow) {
			JFileChooser chooser = new JFileChooser();
			int state = chooser.showOpenDialog(null);
			File file = chooser.getSelectedFile();
			if (file != null && state == JFileChooser.APPROVE_OPTION) {
				demoManul = new DemoManulDialog(file);
				demoManul.setVisible(true);
			}
		}
		if (e.getSource()==optionChessBoard1){
			board.background1();
		}
		else if(e.getSource()==optionChessBoard2){
			board.background2();
		}
		else if(e.getSource()==optionChessBoard3){
			board.background3();
		}
		else if(e.getSource()==optionChessBoard4){
			board.background4();
		}

	}

	public static void main(String args[]) {
		new ChinaChess();
		try {
			//UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
			UIManager.setLookAndFeel("com.jtattoo.plaf.bernstein.BernsteinLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.smart.SmartLookAndFeel");
			//1UIManager.setLookAndFeel("com.jtattoo.plaf.mcwin.McWinLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.luna.LunaLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.aluminium.AluminiumLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.hifi.HiFiLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.mint.MintLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.aero.AeroLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.fast.FastLookAndFeel");
			//UIManager.setLookAndFeel("com.jtattoo.plaf.acryl.AcrylLookAndFeel");
			}
			catch (Exception e) 
			{
			e.printStackTrace();
			} 
	}
}
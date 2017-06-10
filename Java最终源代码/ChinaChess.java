
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
	Clock clock;//加入时钟
	
	private static final long serialVersionUID = 6385933774053272194L; 
	public ChinaChess() {
		String path="/Img/o.jpg";
		try{//更改Java程序默认图标
	    	Image img=ImageIO.read(this.getClass().getResource(path));
	    	this.setIconImage(img);
	    }catch (IOException e){
	    	e.printStackTrace();
	    }
		setTitle("中国象棋棋谱制作系统");
		board = new ChessBoard();
		add(board, BorderLayout.CENTER);
		bar = new JMenuBar();
		makeMenu = new JMenu("选择制作棋谱的方式");
		showMenu = new JMenu("棋谱");
		
		makeMenu.setMnemonic('b');
		showMenu.setMnemonic('n');
		boardMenu=new JMenu("自定义背景");
		boardMenu.setMnemonic('m');
		
		makeShiZhanManul = new JMenuItem("制作实战棋谱",new ImageIcon("b.jpg"));
		makeCanJuManul = new JMenuItem("制作残局棋谱",new ImageIcon("c.png"));
		openManulAndShow = new JMenuItem("打开一个棋谱并演示",new ImageIcon("a.jpg"));
		
		
		optionChessBoard1=new JMenuItem("背景1");
		optionChessBoard2=new JMenuItem("背景2");
		optionChessBoard3=new JMenuItem("背景3");
		optionChessBoard4=new JMenuItem("默认");
		
		
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
		startMakeManul = new JButton("开始制作棋谱");
		startMakeManul.setVisible(false);
		startMakeManul.addActionListener(this);
		
		saveManul = new JButton("保存棋谱");
		saveManul.setVisible(false);
		saveManul.addActionListener(this);
		
		hintMessage = new JTextField(30);
		hintMessage.setHorizontalAlignment(JTextField.CENTER);
		hintMessage.setFont(new Font("细黑", Font.PLAIN, 15));
		hintMessage.setEditable(false);
		hintMessage.setText("请单击菜单,选择制作棋谱的方式或演示已有的棋谱");
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
		JOptionPane.showMessageDialog(this, "请单击菜单,选择制作棋谱的方式或演示已有的棋谱", "提示对话框", JOptionPane.WARNING_MESSAGE);
		addWindowFocusListener(new WindowFocusListener() { 
			 
            @Override 
            public void windowGainedFocus(WindowEvent e) { 
              
                System.out.println("窗口获得了焦点！"); 
            } 
 
            @Override 
            public void windowLostFocus(WindowEvent e) { 
                // TODO Auto-generated method stub  
                System.out.println("窗口失去了焦点！"); 
            } 
             
        }); 
		
		
		
		//增加时间
		clock=new Clock();
		JPanel south = new JPanel();
		south.add(clock);
		add(south, BorderLayout.SOUTH);
		setVisible(true);

	
	
	
	}

	public void actionPerformed(ActionEvent e) {
		String message = "单击\"开始制作棋谱\",然后拖动棋子走棋";
		if (e.getSource() == makeShiZhanManul) {
			board.setShizhanPlay();
			hintMessage.setText(message);
			startMakeManul.setVisible(true);
			saveManul.setVisible(false);
			JOptionPane.showMessageDialog(this, message, "提示对话框", JOptionPane.WARNING_MESSAGE);
		}
		if (e.getSource() == makeCanJuManul) {
			message = "将棋盒中棋子拖入棋盘摆好残局,然后单击\"开始制作棋谱\"";
			hintMessage.setText(message);
			board.setCanjuPlay();
			startMakeManul.setVisible(true);
			saveManul.setVisible(false);
			JOptionPane.showMessageDialog(this, message, "提示对话框", JOptionPane.WARNING_MESSAGE);
		}
		/*
		 * 修改代码使得在布置残局时必须有将和帅。
		 */
		if (e.getSource() == startMakeManul) {
			boolean judge = false;
			Point[] boxPoint = new Point[board.redChessBox.boxPoint.length];
			boxPoint = board.redChessBox.getBoxPoint();
			for (int i = 0; i < board.redChessBox.boxPoint.length; i++) {
				if (boxPoint[i].haveChessPiece) {
					if (boxPoint[i].getChessPiece().getName().equals("帅")) {
						judge = true;
						break;
					}
				}
			}
			if (judge == false) {
				boxPoint = board.blackChessBox.getBoxPoint();
				for (int i = 0; i < board.blackChessBox.boxPoint.length; i++) {
					if (boxPoint[i].haveChessPiece) {
						if (boxPoint[i].getChessPiece().getName().equals("将")) {
							judge = true;
							break;
						}
					}

				}
			}
			if (judge) {
				message = "缺乏帅或将!";
				JOptionPane.showMessageDialog(this, message, "提示对话框", JOptionPane.WARNING_MESSAGE);
			} else {
				board.startMakeManul();
				hintMessage.setText("单击\"保存棋谱\"按钮可以保存棋谱");
				saveManul.setVisible(true);
				startMakeManul.setVisible(false);
			}
		}
		/*
		 * 修改代码,当保存棋谱时，调用setClipNull()，为了之后能顺利读取， 保存完之后，调用setClip()，使得音乐能正常播放。
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
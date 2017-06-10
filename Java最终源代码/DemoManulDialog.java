import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import java.io.*;

public class DemoManulDialog extends JDialog implements ActionListener {
	ArrayList<Point> step = null;//step用来存放ChessBoard类中的step，相当于一个载体，但它是不同步的
	Stack<Point> stackStep, stackBack;//先进后出，存储下棋的步骤，存储下一步和返回的步骤
	ChessBoard demoOne = null;//棋盘
	File file;//声明存放棋谱文件的引用
	JTextArea text;//声明一个文本区
	JButton previous, next;//声明变量 两个按钮
	JSplitPane split;//声明拆分窗格
	int stepNumber = 1;

	DemoManulDialog(File f) {//构造方法，根据参数指定的棋谱文件创建一个对话框
		file = f;
		setTitle("演示棋谱,棋谱文件:" + f.getName());
		split = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT);//水平拆分
		stackStep = new Stack<Point>();
		stackBack = new Stack<Point>();
		next = new JButton("第" + stepNumber + "步");//“第 步”按钮
		previous = new JButton("返回");//“返回”按钮
		next.addActionListener(this);//增加一个接口，监视器
		previous.addActionListener(this);//增加一个接口，监视器
		previous.setEnabled(false);//禁用按钮
		text = new JTextArea();//创建文本框
		text.setEditable(false);//禁用文本框
		text.setFont(new Font("宋体", Font.PLAIN, 16));//设置字体
		JPanel west = new JPanel();//创建面板west
		west.add(text);//加一个文本框
		JPanel south = new JPanel();//创建面板south
		south.add(previous);//添加按钮
		south.add(next);//添加按钮
		split.setLeftComponent(new JScrollPane(text));//拆分窗格，增加一个滚动窗格
		add(south, BorderLayout.SOUTH);//把面板south添加到南边区域
		add(split, BorderLayout.CENTER);//把拆分窗格添加到中间区域
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		//单击右上角的关闭图标后，隐藏当前窗口，并释放窗体占有的其他资源
		showTextManul();//显示图形化界面的棋谱
		showBoardManul();//显示文本化界面的棋谱
		setBounds(10, 10, 700, 570);//设置窗口在屏幕内的位置和大小
	}

	public void showBoardManul() {//提供图形化界面的棋谱，用户可以使用该界面演示棋谱
		stepNumber = 1;
		stackStep.clear();
		stackBack.clear();
		if (demoOne != null)
			remove(demoOne);
		//可能出现异常的操作
		try {
			FileInputStream file_in = new FileInputStream(file);//读取原始文件的图像数据流
			ObjectInputStream object_in = new ObjectInputStream(file_in);//存取原始数据和对象
			demoOne = (ChessBoard) object_in.readObject();//读入棋局
			file_in.close();//关闭此文件输入流并释放与流关联的任何系统资源。
			object_in.close();//关闭输入流。必须调用以释放与流关联的任何资源。
			ChessPiece[] redPiece = demoOne.getRedPiece();//调用ChessPiece中的红子
			ChessPiece[] blackPiece = demoOne.getBlackPiece();
			 //调用监听器，当时下的每一步棋
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
			}
			step = demoOne.getStep();//读取步
			for (int i = step.size() - 1; i >= 0; i--) {
				stackStep.push(step.get(i));//下一步
			}
			demoOne.remove(demoOne.cancel);//返回
			restoreChessBoard(step);
			split.setRightComponent(new JScrollPane(demoOne));//右边的窗格
		} catch (Exception exp) {
			add(new JButton("不是棋谱文件"), BorderLayout.CENTER);
		}
	}

	public void actionPerformed(ActionEvent e) {
		/*是ActionListener接口中的方法，当用户单击showBoardManul()方法提供的图形化界面上的“步骤”
    	按钮"???时，actionPerformed(ActionEvent)方法所执行的操作就是顺序地演示走棋的每个步骤。*/
		JButton source = (JButton) e.getSource();//创建了一个新的按钮
		Point startPoint = null, endPoint = null, boxPoint = null;//初始
		
		//当按钮是下一个。并且存在下一步
		if (source == next && stackStep.empty() == false) {
			stepNumber++;
			next.setText("第" + stepNumber + "步");
			startPoint = stackStep.pop();//在这个堆栈的顶部删除对象，并返回该对象的值作为该函数的值。
			stackBack.push(startPoint);//把下一个再推倒顶部
			ChessPiece startPiece = startPoint.getChessPiece();
			
			//符合吃子的一些规则
			int w = startPiece.getBounds().width;
			int h = startPiece.getBounds().height;
			endPoint = stackStep.pop();
			stackBack.push(endPoint);
			boxPoint = stackStep.pop();
			stackBack.push(boxPoint);
			
			if (boxPoint != null) {
				ChessPiece endPiece = endPoint.getChessPiece();
				endPiece.setLocation(boxPoint.getX() - w / 2, boxPoint.getY() - h / 2);
				startPiece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
				endPiece.setAtPoint(boxPoint);
				boxPoint.setHaveChessPiece(true);
				endPoint.setChessPiece(startPiece);
				endPoint.setHaveChessPiece(true);
				startPoint.setHaveChessPiece(false);
			} else {
				startPiece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
				startPiece.setAtPoint(endPoint);
				endPoint.setHaveChessPiece(true);
				endPoint.setChessPiece(startPiece);
				startPoint.setHaveChessPiece(false);
			}
		}

		//返回按钮的操作
		if (source == previous && stackBack.empty() == false) {
			stepNumber--;
			next.setText("第" + stepNumber + "步");
			boxPoint = stackBack.pop();
			stackStep.push(boxPoint);
			endPoint = stackBack.pop();
			stackStep.push(endPoint);
			startPoint = stackBack.pop();
			stackStep.push(startPoint);
			ChessPiece piece = null;
			if (boxPoint != null) {
				piece = endPoint.getChessPiece();
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				piece.setAtPoint(startPoint);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
				piece = boxPoint.getChessPiece();
				piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
				piece.setAtPoint(endPoint);
				endPoint.setHaveChessPiece(true);
				endPoint.setChessPiece(piece);
				boxPoint.setHaveChessPiece(false);
			} else {
				piece = endPoint.getChessPiece();
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				piece.setAtPoint(startPoint);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
			}
		}
		 //返回的那一步为空
		if (stackBack.empty())
			previous.setEnabled(false);
		else
			previous.setEnabled(true);
		
		if (stackStep.empty()) {
			int n = JOptionPane.showConfirmDialog(this, "演示完毕,重新演示吗?", "确认对话框", JOptionPane.YES_NO_CANCEL_OPTION);
			 //确认对话框中的是和否
			if (n == JOptionPane.YES_OPTION) {
				stepNumber = 1;
				next.setText("第" + stepNumber + "步");
				restoreChessBoard(step);
				stackStep.clear();
				stackBack.clear();
				previous.setEnabled(false);
				for (int i = step.size() - 1; i >= 0; i--) {
					stackStep.push(step.get(i));
				}
			} else if (n == JOptionPane.NO_OPTION)
				dispose();
		}
	}

	//存储棋盘
	private void restoreChessBoard(ArrayList<Point> step) {
		for (int j = step.size() - 1; j >= 2; j = j - 3) {
			Point boxPoint = step.get(j);
			Point endPoint = step.get(j - 1);
			Point startPoint = step.get(j - 2);
			ChessPiece piece = null;
			if (boxPoint != null) {
				piece = endPoint.getChessPiece();
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
				piece = boxPoint.getChessPiece();
				piece.setLocation(endPoint.getX() - w / 2, endPoint.getY() - h / 2);
				endPoint.setHaveChessPiece(true);
				endPoint.setChessPiece(piece);
				boxPoint.setHaveChessPiece(false);
			} else {
				piece = endPoint.getChessPiece();
				int w = piece.getBounds().width;
				int h = piece.getBounds().height;
				piece.setLocation(startPoint.getX() - w / 2, startPoint.getY() - h / 2);
				startPoint.setHaveChessPiece(true);
				startPoint.setChessPiece(piece);
				endPoint.setHaveChessPiece(false);
			}
		}
	}

	private void showTextManul() {//提供文本化棋谱，以便用户阅读棋谱
		ChessBoard demoTwo = null;
		try {
			FileInputStream file_in = new FileInputStream(file);
			ObjectInputStream object_in = new ObjectInputStream(file_in);
			demoTwo = (ChessBoard) object_in.readObject();
			file_in.close();
			object_in.close();
		} catch (Exception exp) {
			add(new JButton("不是棋谱文件"), BorderLayout.CENTER);
		}
		if (demoTwo != null) {
			ArrayList<Point> step = demoTwo.getStep();
			step = demoTwo.getStep();
			restoreChessBoard(step);
			text.setText(null);
			StringBuffer buffer = new StringBuffer();
			text.append("棋谱" + file.getName() + ":");
			Point[][] point = demoTwo.getPoint();
			Point startPoint = null, endPoint = null, boxPoint = null;
			ChessPiece startPiece = null;
			int m = -1, n = -1, p = -1, q = -1;
			int index = 0, number = 0;
			while (index <= step.size() - 1) {
				startPoint = step.get(index);
				startPiece = startPoint.getChessPiece();
				String name = startPiece.getName();
				endPoint = step.get(index + 1);
				boxPoint = step.get(index + 2);
				for (int i = 0; i < point.length; i++) {
					for (int j = 0; j < point[i].length; j++) {
						if (point[i][j].equals(startPoint)) {
							m = i + 1;
							n = j + 1;
							break;
						}
					}
				}
				for (int i = 0; i < point.length; i++) {
					for (int j = 0; j < point[i].length; j++) {
						if (point[i][j].equals(endPoint)) {
							p = i + 1;
							q = j + 1;
							break;
						}
					}
				}
				char c1 = numberToLetter(m);
				char c2 = numberToLetter(p);
				number++;
				if (boxPoint != null) {
					ChessPiece endPiece = endPoint.getChessPiece();
					if (startPiece.getIsRed()) {
						buffer.append("\n(" + number + ")红" + name + ":" + c1 + "" + n + "→" + c2 + "" + q);
						buffer.append("(吃掉黑" + endPiece.getName() + ")");
					} else if (startPiece.getIsBlack()) {
						buffer.append("\n(" + number + ")黑" + name + ":" + c1 + "" + n + "→" + c2 + "" + q);
						buffer.append("(吃掉红" + endPiece.getName() + ")");
					}
					endPiece.setAtPoint(boxPoint);
					boxPoint.setHaveChessPiece(true);
					endPoint.setChessPiece(startPiece);
					endPoint.setHaveChessPiece(true);
					startPoint.setHaveChessPiece(false);
				} else {
					if (startPiece.getIsRed())
						buffer.append("\n(" + number + ")红" + name + ":" + c1 + "" + n + "→" + c2 + "" + q);
					else if (startPiece.getIsBlack())
						buffer.append("\n(" + number + ")黑" + name + ":" + c1 + "" + n + "→" + c2 + "" + q);
					startPiece.setAtPoint(endPoint);
					endPoint.setHaveChessPiece(true);
					endPoint.setChessPiece(startPiece);
					startPoint.setHaveChessPiece(false);
				}
				index = index + 3;
			}
			text.append(new String(buffer));
		}
	}

	public char numberToLetter(int n) { //将数字换成相对应的字母
		char c = '\0';
		switch (n) {
		case 1:
			c = 'a';
			break;
		case 2:
			c = 'b';
			break;
		case 3:
			c = 'c';
			break;
		case 4:
			c = 'd';
			break;
		case 5:
			c = 'e';
			break;
		case 6:
			c = 'f';
			break;
		case 7:
			c = 'g';
			break;
		case 8:
			c = 'h';
			break;
		case 9:
			c = 'i';
			break;
		case 10:
			c = 'j';
			break;
		}
		return c;
	}
}
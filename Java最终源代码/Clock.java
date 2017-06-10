import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Clock extends JTextField implements ActionListener{
	private Timer t ;//一个或多个actionevents在指定的时间间隔。
	//设置计时器包括创建计时器对象，在其上注册一个或多个操作侦听器，并使用启动方法启动计时器。
	Calendar c;//Calendar类是一个抽象类，为一个特定的瞬间和一组日历等领域的年，月
	//day_of_month，小时之间的转换提供了方法，等，和操纵日历字段，例如获得下一周的日期。

	public Clock(){
		
		super();//构建了一个新的文本框。创建默认模型，初始字符串为空，列数设置为0。
		s();
		this.addActionListener(this);//添加指定的监听行动从文本接受的动作事件。
		t.start();//启动计时器，使其开始向其监听器发送动作事件.。
	}
	public Clock(int i){
		super(i);
		s();
		this.addActionListener(this);
		t.start();
	}
	private void s(){
		t=new Timer(1000,this);
	}
	public void actionPerformed(ActionEvent e) {
		c=Calendar.getInstance();//使用默认时区和区域设置获取日历。返回的日历基于默认时区的默认时区中的当前时间。
		this.setText(""+c.getTime());
	}
	public static void main(String args[]){
		JFrame f=new JFrame();
		f.setSize(500, 200);
		f.getContentPane().setLayout(new BorderLayout());
		f.getContentPane().add(new Clock(20),BorderLayout.CENTER);
		f.setVisible(true);
	}
}


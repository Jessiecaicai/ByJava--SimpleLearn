import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

import javax.swing.JFrame;
import javax.swing.JTextField;
import javax.swing.Timer;

public class Clock extends JTextField implements ActionListener{
	private Timer t ;//һ������actionevents��ָ����ʱ������
	//���ü�ʱ������������ʱ������������ע��һ��������������������ʹ����������������ʱ����
	Calendar c;//Calendar����һ�������࣬Ϊһ���ض���˲���һ��������������꣬��
	//day_of_month��Сʱ֮���ת���ṩ�˷������ȣ��Ͳ��������ֶΣ���������һ�ܵ����ڡ�

	public Clock(){
		
		super();//������һ���µ��ı��򡣴���Ĭ��ģ�ͣ���ʼ�ַ���Ϊ�գ���������Ϊ0��
		s();
		this.addActionListener(this);//���ָ���ļ����ж����ı����ܵĶ����¼���
		t.start();//������ʱ����ʹ�俪ʼ������������Ͷ����¼�.��
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
		c=Calendar.getInstance();//ʹ��Ĭ��ʱ�����������û�ȡ���������ص���������Ĭ��ʱ����Ĭ��ʱ���еĵ�ǰʱ�䡣
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


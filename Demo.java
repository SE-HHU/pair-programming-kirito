package d1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/*
 * =====================
 * 主要类：
 * Calculation类:
 * 		嵌于Question类的计算器类，用于返回Question类内题目字符串的答案字符串
 * FileManagement类：
 * 		文件管理，用于处理生成的题目、答案与文件保存、读取的交互管理
 * LunchFrame类：
 * 		用于设计可视化窗口
 * Question类：
 * 		主要的成员类，保存单个题目的所有数据（题号、算式、答案、封装）
 * QuestionCheck类：
 * 		Question类的功能类，用于题目列表的查重功能
 * QuestionJudge类：
 * 		Question类的功能类，用于题目列表的批改功能
 * QuestionMaker类：
 * 		Question类的功能类，用于题目列表的生成功能
 * 
 */




public class Demo {

	public static void main(String[] args) throws IOException {
		// TODO 自动生成的方法存根
		
		new LunchFrame();
		
	}
}



@SuppressWarnings("serial")
class LunchFrame extends JFrame implements ActionListener {
	JLabel label1;
	JTextField t1;
	JButton button;
	
	JLabel label2;

	public LunchFrame(){

		label1 = new JLabel("输入生成题目的个数：");
		t1 = new JTextField(10);
		button = new JButton("开始生成");
		add(label1);
		add(t1);
		add(button);
		button.addActionListener(this);
		setLayout(new FlowLayout());
		setSize(300,200);
		setLocation(200,200);
		setVisible(true);
		setTitle("自动生成题目");
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

	}

	public void actionPerformed(ActionEvent e){
		if(e.getSource()== button){
			//开始任务
			allActionDone();
			//任务提示
			label2 = new JLabel("题目生成成功！");
			add(label2);
			setLayout(new FlowLayout());
			setLocation(200,200);
			setVisible(true);
			this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}
	
	public void allActionDone() {
		int quesNum = Integer.valueOf(t1.getText());
		List<Question> questionList = new ArrayList<Question>();
		QuestionMaker.generateQuestions(questionList, quesNum);
		try {
			FileManagement.fileManagement(questionList);
			FileManagement.judgementToFile(questionList,new File("D:\\Judgement.txt"));
		} catch (IOException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}
}


package d1;
/************
 * **成员变量：
 * numNum;//运算数个数
 * num[];//运算数数组
 * opt[];//运算符数组
 * isUnique;//是否唯一
 * bracketNum;//括号个数
 * answer;//算式计算结果
 * questionParts ArrayList<String>();//算式各成分元素数组
 * 
 * **********
 * **成员方法：
 * public Question(int numNum, int bracketNum);//构造方法
 * public boolean isUnique();//查询算式唯一性
 * public int getBracketNum();//查询算式括号数
 * private String toStringWithNoBrackets();//获取无括号版第一级算式版式
 * public String toString();//返回完整的算式表达式
 * public double getAnswer(String str);//获取算式答案
 * public int getOrder()；//获取题号
 * public static String convertDoubleToString(double num);//double变量尾数去0
 * 
 */

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Question extends Calculation{

	private int order;//题号
	private int numNum;//运算数个数
	private double[] num;//运算数数组
	private String[] opt;//运算符数组
//	private int isUnique = 0;//是否唯一
	private int bracketNum;//括号个数
	private List<String> questionParts = new ArrayList<String>();//算式各成分元素数组
	private String toFinalString;//算式题目表达式
	private String finalAnswer;//算式答案
	private int answerJudgement = 0;
	
	//获取答案正确性
	public int getAnswerJudgement() {
		return this.answerJudgement;
	}
	
	//设置答案正确性
	public void setAnswerJudgement(int judge) {
		this.answerJudgement = judge;
	}
	
	
	//获取题号
	public int getOrder() {
		return this.order;
	}
	
	//获取算式各成分元素数组
	public List<String> getQuestionParts(){
		return this.questionParts;
	}
	
	//基于运算数个数和括号个数的构造方法
	public Question(int numNum, int bracketNum ,int order) {
		super();
		this.order = order;
		this.numNum = numNum;
		this.bracketNum = bracketNum;
		this.toStr();
		this.finalAnswer = this.getAnswer(toFinalString);
	}
	
	//自定义Quetion变量
	public Question(String toFinalString,int order) {
		super();
		this.order = order;
		this.toFinalString = toFinalString;
		this.finalAnswer = this.getAnswer(toFinalString);
	}

	//获取算式答案
	public String getFinalAnswer() {
		return finalAnswer;
	}
	
	//Old
//	public void setIsUnique(int num) {
//		this.isUnique = num;
//	}
	
	//Old
//	public int getIsUnique() {
//		return this.isUnique;
//	}
	
	//获取最终算式表达式
		public String getToFinalString() {
			return toFinalString;
		}
	
	//获取括号数目
	public int getBracketNum() {
		return bracketNum;
	}

	//保留double类型最小位数
	public static String convertDoubleToString(double num) {
		BigDecimal bd = new BigDecimal(String.valueOf(num));
		return bd.stripTrailingZeros().toPlainString();
	}
	
	
	//无括号版第一级算式版式
	private String toStringWithNoBrackets() {
		this.num = new double[this.numNum];//生成运算数
		this.opt = new String[this.numNum-1];//生成算式运算符号
		
		Random r = new Random();
		String[] op = new String[] {"+","-","×","÷"};//四则运算符库
		
		//随机数填充运算数数组和运算符数组
		for(int order=0;order<opt.length;order++) {
			opt[order] = op[(r.nextInt(8)+1)%4]; 
		}
		for(int order=0;order<num.length;order++) {
			num[order] = r.nextInt(50)+1;
		}
		
		//生成demo无括号版第一级算式版式
		//生成questionParts运算数和运算符的String类型算式组成数组
		StringBuffer demo = new StringBuffer();
		for(int order = 0;order<this.numNum;order++) {
			demo.append(convertDoubleToString(num[order]));
			this.questionParts.add(convertDoubleToString(num[order]));
			
			if(order<this.numNum-1) {
				demo.append(opt[order]);
				this.questionParts.add(opt[order]);
			}
		}
		
		//返回String类型demo无括号版第一级算式版式
		return demo.toString();
	}
	
	//描述算式的最终表达式
	private void toStr() {
		//1、括号数目为0
		//或运算数只有两个
		if(this.bracketNum==0 || this.numNum==2) {
			this.bracketNum = 0;
			this.toFinalString = this.toStringWithNoBrackets();
		}
		
		//2、括号数目不为0
		///添加括号*
		else{
			//获取无括号版第一级算式版式
			this.toStringWithNoBrackets();
			//定义newToString带括号版第二级算式版式
			StringBuffer newToString = new StringBuffer();
			//插空版式的算式数组
			int tempLen = this.numNum * 4 - 1;//插空版式的算式数组长度
			String[] temp = new String[tempLen];
			//给数组插空
			for (int order = 0; order < tempLen; order++) {
				if ((order % 2) != 0) {//数组下标为奇数时插入questionParts表示算式的各个成分
					temp[order] = this.questionParts.get((order - 1) / 2);
				} else {//数组下标为偶数时插空
					temp[order] = null;
				}
			}
			//给空格处插入括号
			int fMove = 0;//描述左括号的位置
			boolean isHaveFormer = false;//描述左括号是否放置完毕
			for (int order = 0; order < tempLen; order++) {
				if (temp[order] == null //判断数组元素是否为空来选择插入左括号
						&& isHaveFormer == false && order % 4 == 0) {//保证左括号的位置在运算数的邻左边
					if (order == tempLen - 7) {//保证左括号的最远位置
						temp[order] = "(";
						isHaveFormer = true;
					} else {
						if ((new Random().nextInt(100) + 1) % 2 == 0) {//保证左括号放置的随机性
							temp[order] = "(";
							isHaveFormer = true;
						}
					}
					fMove = order;//记录左括号的位置

				}
				//若左括号在最左边，保证右括号不在最右边
				if (fMove == 0) {
					if (temp[order] == null && isHaveFormer == true && (order - fMove - 6) % 4 == 0 //保证右括号在运算数的邻右边
							&& order - fMove >= 6) {//保证右括号在运算数邻右边的最小位置
						if (order == tempLen - 5) {//保证右括号在运算数邻右边的最大位置
							temp[order] = ")";
							break;
						} else {
							if ((new Random().nextInt(100) + 1) % 2 == 0) {//保证右括号放置的随机性
								temp[order] = ")";
								break;
							}
						}
					}
					//左括号不在最左边时，放置右括号
				} else if (temp[order] == null && isHaveFormer == true && (order - fMove - 6) % 4 == 0
						&& order - fMove >= 6) {
					if (order == tempLen - 1) {
						temp[order] = ")";
						break;
					} else {
						if ((new Random().nextInt(100) + 1) % 2 == 0) {
							temp[order] = ")";
							break;
						}
					}
				}
			}
			//生成newToString带括号版第二级算式版式
			for (int i = 0; i < tempLen; i++) {
				if (temp[i] != null) {
					newToString.append(temp[i]);
				}
			}
			//返回完整的算式表达式
			this.toFinalString = newToString.toString();
		}
	}
	
	//计算算式答案
//	private String getAnswer(String str){
//		return answer(calculateAnswer(str));
//	}
	//new function
	private String getAnswer(String str){
		return calculateAnswer(str);
	}

}
package d1;

import java.util.HashSet;
import java.util.List;


public class QuestionCheck{
	String str;                //计算式
	String answer;             //结果
	int[] symbol=new int[4];   //加减乘除各符号数目
	
	//===
	public String stringAt(String str,int i) {
		String[] strArr = str.split("");
		return strArr[i];
	}
	
	//===
	public String stringDeleteAt(String str,int i) {
		String[] s = str.split("");
		s[i] = "null";
		String news = "";
		for(int j=0;j<str.length();j++) {
			if(!"null".equals(s[j])) {
				news += s[j];
			}
		}
		return news;
	}
	
	//查重
	public QuestionCheck(String str1,String answ){
		this.str=str1;
	
		this.answer = answ;
	
	
		for(int i=0;i<str.length();i++){
			switch(stringAt(str, i)){
				case "+": symbol[0]++;
							break;
				case "-": symbol[1]++;
							break;          
				case "×": symbol[2]++;
							break;
				case "÷": symbol[3]++;
							break;
				case "(":  str=stringDeleteAt(str, i);
							break;
				case ")":  str=stringDeleteAt(str, i);
          					break;
			}
		
		}
	}
	
	
	//查重原理为检查计算式对应的各运算符数目，数字种类，计算结果是否与list中的各元素是否对应相等
	public boolean Isunique(List<QuestionCheck> list){ //是否不重复
		HashSet<Double> set=new HashSet<Double>();//将计算式中的各数字存入其中
		int index=0;
		for(int i=0;i<str.length();i++){
			if(str.charAt(i)=='+'
				   ||str.charAt(i)=='-'
				   ||"×".equals(stringAt(str, i))
				   ||"÷".equals(stringAt(str, i))){
				Double value=Double.parseDouble(str.substring(index, i));
				set.add(value);
				index=i+1;
			}
			if(i==str.length()-1){
				Double value=Double.parseDouble(str.substring(index, i+1));
			 
				set.add(value);   
			}   
		}
	    int point=0;    //标记变量
	    int point2=0;
		for(QuestionCheck d:list){
			for(int i=0;i<4;i++){
				if(d.symbol[i]!=this.symbol[i]){//比较各符号数 
					point++;
					break;
				}
			}
			if(point!=0){  
				continue;
			}
			if(d.answer.equals(this.answer)==false){//比较结果  
				continue;
			}
		   
			index=0;
			for(int i=0;i<d.str.length();i++){//分割出list中计算式的每一个数字与set比对
				if(d.str.charAt(i)=='+'
						||d.str.charAt(i)=='-'
						||"×".equals(stringAt(d.str, i))
						||"÷".equals(stringAt(d.str, i))){
					Double value2=Double.parseDouble(d.str.substring(index, i));
					  
					if(set.contains(value2)!=true){
						point2++;
						break;
					   	}
					   	index=i+1;
				   	}
				   	if(i==d.str.length()-1){
				   		Double value2=Double.parseDouble(d.str.substring(index, i+1));
				   		if(set.contains(value2)!=true){
				   			point2++;
				   			break;
				   		}   
				   	}   
			}
			if(point2!=0){  
				continue;
			} 
			return false;  //若以上每一步比较都通过，则重复
		}
		return true;
	}
		

}

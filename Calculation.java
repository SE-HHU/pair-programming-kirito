package d1;
import java.math.BigDecimal;

public class Calculation {

	public String stringAt(String str,int i) {
		String[] strArr = str.split("");
		return strArr[i];
	}
	

	public String calculateAnswer(String str){
		for(int i=0;i<str.length();i++){              //检查有无括号
			if(str.charAt(i)=='('){
				int point=str.indexOf(')');
				String	s=str.substring(i+1, point);
				String valuestr=calculateAnswer(s);//对于有括号的，将括号内的部分先进行计算 
				if(valuestr.indexOf("+")!=-1){
					int t1=valuestr.indexOf("+"),t2=valuestr.indexOf("÷");
					int num2=Integer.parseInt(valuestr.substring(t2+1));
					int num1=Integer.parseInt(valuestr.substring(t1+1,t2));
					int num=Integer.parseInt(valuestr.substring(0,t1));
					num=num*num2+num1;
					valuestr=""+num+"÷"+num2;    //化为假分数
				}
				StringBuffer strbf=new StringBuffer(str);
				strbf=strbf.replace(i, point+1, valuestr);  //将原括号位置替换为数字      
				str=new String(strbf);
				i=i-1+valuestr.length(); //将位指针指向替换的数字的最后一位
			}
		}
		for(int i=0;i<str.length();i++){  //先计算乘除
			String index="";
			int indexnum=0;
			if("×".equals(stringAt(str, i))
					||"÷".equals(stringAt(str, i))){  //先搜索乘除号位置
				int num1=0,num2=0;
				int len1=0,len2=0;
				for(int j=i-1;j>=0;j--){   
					if(str.charAt(j)=='+'
							||str.charAt(j)=='-'){//遇到符号停止
						String numline1=str.substring(j+1,i);  //截取两符号之间的部分，即数字部分
						index=numline1;
						len1=numline1.length();
						indexnum=j+1;
						break;
					}
					if(j==0){//遇到字符串头部的情况
						String numline1=str.substring(j,i);
						index=numline1;
						len1=numline1.length();
						indexnum=j;
					}
		
				}
				for(int j=i+1;j<str.length();j++){
					if(str.charAt(j)=='+'
							||str.charAt(j)=='-'
							||"×".equals(stringAt(str, j))
							||"÷".equals(stringAt(str, j))){   
						String numline2=str.substring(i+1,j);
						num2=Integer.parseInt(numline2);
						len2=numline2.length();
						break;
					}
					if(j==str.length()-1){	//遇到字符串尾部的情况
						String numline2=str.substring(i+1,j+1);
						num2=Integer.parseInt(numline2);
						len2=numline2.length(); 
					}
				}
				if("×".equals(stringAt(str, i))){  //若*前的式子里有/，则以分子乘以*后的数，没有则正常计算
					int t;
					if((t=index.indexOf("÷"))!=-1){
						String str2=index.substring(0,t);
						int num3=Integer.parseInt(str2);
						num3*=num2;
						StringBuffer answerbf=new StringBuffer(index);
						answerbf=answerbf.replace(0,t,String.valueOf(num3));
						String answer=new String(answerbf);
						StringBuffer strbf=new StringBuffer(str);  	
						strbf=strbf.replace(indexnum,i+len2+1,answer);
						str=new String(strbf);
						i=i-len1-1+answer.length(); 
						continue;	
					}else{
						num1=Integer.parseInt(index);
						int answer=num1*num2;
						StringBuffer strbf=new StringBuffer(str);  	
						strbf=strbf.replace(i-len1, i+len2+1,String.valueOf(answer));//将算式所在位置替换为答案数字以便下一步计算
						str=new String(strbf);
						i=i-len1-1+String.valueOf(answer).length();//将位指针指向替换的数字的最后一位
						continue;
					}
				}
				if("÷".equals(stringAt(str, i))){ //若/前式子里有/，则分母乘以/后的数
					int t;
					if((t=index.indexOf("÷"))!=-1){
						String str2=index.substring(t+1);
						int num3=Integer.parseInt(str2);
						num3*=num2;
						StringBuffer answerbf=new StringBuffer(index);
						answerbf=answerbf.replace(t+1,index.length()+1,String.valueOf(num3));
						String answer=new String(answerbf);
						StringBuffer strbf=new StringBuffer(str);  	
						strbf=strbf.replace(indexnum,i+len2+1,answer);
						str=new String(strbf);
						i=i-len1-1+answer.length();
						continue;
					}else{
						i=i+String.valueOf(num2).length();//保留原来形式，直接向下计算
						continue;
					}
				}
			}
		}
		if(str.indexOf("÷")==-1){ //再计算加减	
			for(int t=0;t<str.length()-1;t++){
				if(str.charAt(t)=='+'
						||str.charAt(t)=='-'){
					int num1=0,num2=0;
					int len1=0,len2=0;
					String numline1=str.substring(0,t);//计算加减时前半部分已经不可能有符号了，直接赋值
					num1=Integer.parseInt(numline1);
					len1=numline1.length();
					for(int j=t+1;j<str.length();j++){
						if(str.charAt(j)=='+'
								||str.charAt(j)=='-'){   
							String numline2=str.substring(t+1,j);
							num2=Integer.parseInt(numline2);
							len2=numline2.length();
							break;
						}
						if(j==str.length()-1){
							String numline2=str.substring(t+1,j+1);
							num2=Integer.parseInt(numline2);
							len2=numline2.length(); 
						}
				
					}
					if(str.charAt(t)=='+'){
						int answer=num1+num2;
			
						StringBuffer strbf=new StringBuffer(str);  	
						strbf=strbf.replace(t-len1, t+len2+1,String.valueOf(answer));
						str=new String(strbf);
			
						t=t-len1-1+String.valueOf(answer).length();
						continue;
					}
			
					if(str.charAt(t)=='-'){
						int answer=num1-num2;
						StringBuffer strbf=new StringBuffer(str);  	
						strbf=strbf.replace(t-len1, t+len2+1,String.valueOf(answer));
						str=new String(strbf);
						t=t-len1-1+String.valueOf(answer).length();
						continue;
					}		
				}	
			}
		}else{//将各式中的分母相乘作为总的分母进行通分
			int num2=1;
			int index=0,sum=0;
			for(int i=0;i<str.length();i++){
				if("÷".equals(stringAt(str, i))){ 
					for(int j=i+1;j<str.length();j++){
						if(str.charAt(j)=='+'
								||str.charAt(j)=='-'){
							String numstr=str.substring(i+1,j);
							int num=Integer.parseInt(numstr);
							num2*=num;			
						}
						if(j==str.length()-1){
							String numstr=str.substring(i+1,j+1);
							int num=Integer.parseInt(numstr);
							num2*=num;
						}
					}
					
				}	
		
			}
			for(int i=0;i<str.length();i++){  
				if(str.charAt(i)=='+'
						||str.charAt(i)=='-'){
					int t=0;
					String numstr=str.substring(index,i);
		  
					if((t=numstr.indexOf("÷"))==-1)	{
						int num=Integer.parseInt(numstr);
						if(index==0
								||str.charAt(index-1)=='+'){
							sum=sum+num*num2;
						}else if(str.charAt(index-1)=='-'){
							sum=sum-num*num2;
						}
			 
					}else{
						int num=Integer.parseInt(numstr.substring(0, t));
						int num1=Integer.parseInt(numstr.substring(t+1)); 
						if(index==0||str.charAt(index-1)=='+'){
							sum=sum+num*num2/num1; 
						}else if(str.charAt(index-1)=='-'){
							sum=sum-num*num2/num1; 
						} 
					}
					index=i+1;
				}
				if(i==str.length()-1){
					int t=0;
					String numstr=str.substring(index,i+1);
				  
					if((t=numstr.indexOf("÷"))==-1)	{
						int num=Integer.parseInt(numstr);
						if(index==0
								||str.charAt(index-1)=='+'){
							sum=sum+num*num2;
						}else if(str.charAt(index-1)=='-'){
						 sum=sum-num*num2;
						}	
					 
					}else{
						int num=Integer.parseInt(numstr.substring(0, t));
						int num1=Integer.parseInt(numstr.substring(t+1)); 
						if(index==0||str.charAt(index-1)=='+'){
							sum=sum+num*num2/num1; 
						}else if(str.charAt(index-1)=='-'){
							sum=sum-num*num2/num1; 
						}  
					}		
				}	
			}
			int numint=(int)(sum/num2);
			sum=sum-numint*num2;
			if(sum==0){//若整除直接输出整数
				return String.valueOf(numint);
			}
		
			int num3=getnum2(num2,sum);//得最大公约数
			sum/=num3;
			num2/=num3;
		
			String answerstr="";
			if(numint==0){//真分数
				answerstr=answerstr+sum+"/"+num2;	
			}else{
				answerstr=answerstr+numint+"+"+sum+"/"+num2;//结果以带分数表示
			}
		
			str=answerstr;

		}
		return str;
		

	}
	
	
	
	
	//辗转相除求最大公约数
	private int getnum2(int m,int n){
		while(true){   
			int r=m%n;
			if(r==0){
				return n;
			}else{
				m=n;
				n=r;
			}
		}
	}
	
	//小数尾数去零
	public static String convertDoubleToString(double num) {
		BigDecimal bd = new BigDecimal(String.valueOf(num));
		return bd.stripTrailingZeros().toPlainString();
	}
}
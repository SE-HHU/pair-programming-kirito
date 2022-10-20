package d1;
import java.util.List;
import java.util.ArrayList;
import java.util.HashSet;
class Data
{
	String str;                //计算式
	String answer;             //结果
	int[] symbol=new int[4];   //加减乘除各符号数目
	
	public Data(String str1)
	{  str=str1;
	
	Answer answ=new Answer();
	answer=answ.getAnswer(str);
	
	
	for(int i=0;i<str.length();i++)  
	 { switch(str.charAt(i))
		{ case '+': symbol[0]++;
		            break;
		  case '-': symbol[1]++;
                    break;          
		  case '*': symbol[2]++;
                    break;
		  case '/': symbol[3]++;
                    break;
		  case '(': StringBuffer strbf=new StringBuffer(str);//去括号排除干扰
		            strbf.deleteCharAt(i);
		            str=new String(strbf);
		            break;
		  case ')': StringBuffer strbf2=new StringBuffer(str);
                    strbf2.deleteCharAt(i);
                    i-=1;//不减的话下一位的符号将会漏记
                    str=new String(strbf2);
                    break;
		}
		
	 }
	  
		
		
	}
	//查重原理为检查计算式对应的各运算符数目，数字种类，计算结果是否与list中的各元素是否对应相等
	public boolean Isunique(List<Data> list) //是否不重复
	{  HashSet<Double> set=new HashSet<Double>();//将计算式中的各数字存入其中
	   int index=0;
	    for(int i=0;i<str.length();i++)
	   {  
		   if(str.charAt(i)=='+'||str.charAt(i)=='-'||str.charAt(i)=='*'||str.charAt(i)=='/')
		   {  Double value=Double.parseDouble(str.substring(index, i));
		
		   set.add(value);
			   index=i+1;
		   }
		   if(i==str.length()-1)
		   {
			   Double value=Double.parseDouble(str.substring(index, i+1));
			 
			   set.add(value);
			   
			   
		   }
		   
	   }
	    
	    int point=0;    //标记变量
	    int point2=0;
		for(Data d:list)
		{ for(int i=0;i<4;i++)
		{ if(d.symbol[i]!=this.symbol[i])//比较各符号数
		{  
			point++;
			break;
		}
			
		}
		
		  if(point!=0)
		  {  
			  continue;
		  }
			if(d.answer.equals(this.answer)==false)//比较结果
			{ 
				continue;
			}
		   
		    index=0;
			   for(int i=0;i<d.str.length();i++)//分割出list中计算式的每一个数字与set比对
			   {  
				   if(d.str.charAt(i)=='+'||d.str.charAt(i)=='-'||d.str.charAt(i)=='*'||d.str.charAt(i)=='/')
				   {  Double value2=Double.parseDouble(d.str.substring(index, i));
					  
				     if(set.contains(value2)!=true)
					   {
						   point2++;
						   break;
					   }
					   index=i+1;
				   }
				   if(i==d.str.length()-1)
				   {
					   Double value2=Double.parseDouble(d.str.substring(index, i+1));
					  
					  if(set.contains(value2)!=true)
					   {
						   point2++;
						   break;
					   }
					   
					   
				   }
				   
			   }
			   if(point2!=0)
			   {  
				   continue;
			   }
			   
			   return false;  //若以上每一步比较都通过，则重复
		}
		   
			
		
		return true;
	}

}




public class Unique {
        public static void main(String args[])
        {Data data=new Data("6*2*3+1");
        Data data1=new Data("5/6*7+1");
	    Data data2=new Data("(8+4)*(6-2)");
	    Data data3=new Data("1+2*(6*2)");
	    Data data4=new Data("1+7*5/6");
	    Data data5=new Data("7/3+6+5");
	    List<Data> list=new ArrayList<Data>();
	    list.add(data);
	    list.add(data1);
	    list.add(data2);
	    list.add(data3);
	    list.add(data4);
	    System.out.println(data5.Isunique(list));
        	
        	
        
        }
	    
	
	
}

package d1;
/*
* public static void fileManagement(List<Question> list);
* public static void questionManagement(File f,List<Question> list);
* public static void answerManagement(File f,List<Question> list);
* public static void questionToFile(File f,Question qu,int order);
* public static void answerToFile(File f,Question qu,int order);
* public static void fileToQuestion(Map<Integer, String> questionList,File f)；
* public static void fileToAnswer(List<String> answerList,File f)；
* public static void judgementToFile(List<Question> list,File f)；
* 
*/


import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FileManagement {

		//题目和答案的文件保存
		public static void fileManagement(List<Question> list) throws IOException {
			File questionFile = new File("D:\\QuestionList.txt");
			File answerFile = new File("D:\\AnswerList.txt");
			if(questionFile.exists()) {
				questionFile.delete();
			}
			if(answerFile.exists()) {
				answerFile.delete();
			}
			questionFile.createNewFile();
			answerFile.createNewFile();
			
			questionManagement(questionFile, list);
			answerManagement(answerFile, list);
		}
		
		
		//题目管理
		public static void questionManagement(File f,List<Question> list) throws IOException {
			Iterator<Question> it = list.iterator();
			int order = 1;
			while(it.hasNext()) {
				questionToFile(f, it.next(), order);
				order++;
			}
		}
		
		//答案管理
		public static void answerManagement(File f,List<Question> list) throws IOException {
			Iterator<Question> it = list.iterator();
			int order = 1;
			while(it.hasNext()) {
				answerToFile(f, it.next(), order);
				order++;
			}
		}
		
		//将算式题目保存到文件
		public static void questionToFile(File f,Question qu,int order) throws IOException {
			FileWriter fw = new FileWriter(f,true);
			fw.write(order+". "+qu.getToFinalString()+" =\r\n");
			fw.close();
		}
		
		//将算式答案保存到文件
		public static void answerToFile(File f,Question qu,int order) throws IOException {
			FileWriter fw = new FileWriter(f,true);
			fw.write(order+". "+qu.getFinalAnswer()+"\r\n");
			fw.close();
		}
		
		//读取文件算式题目
		public static void fileToQuestion(Map<Integer, String> questionList,File f) throws IOException {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String strtemp;
			while((strtemp = br.readLine())!=null){
				String str[] = strtemp.split(". ");
				str[1] = str[1].substring(0, str[1].length()-1);
				questionList.put(Integer.valueOf(str[0]), str[1]);
			}
			br.close();
		}
		
		//读取文件算式答案
		public static void fileToAnswer(List<String> answerList,File f) throws IOException {
			BufferedReader br = new BufferedReader(new FileReader(f));
			String strtemp;
			while((strtemp = br.readLine())!=null) {
				String[] str = strtemp.split(". ");
				answerList.add(str[1]);
			}
			br.close();
		}
		
		//保存算式答案的对错情况
		public static void judgementToFile(List<Question> list,File f) throws IOException {
			if(f.exists()) {
				f.delete();
				f.createNewFile();
			}
			
			QuestionJudge.setJudgement(list);
			List<QuestionCheck> newList = new ArrayList<QuestionCheck>();
			for(int i=0;i<list.size();i++) {
				QuestionCheck qc = new QuestionCheck(list.get(i).getToFinalString(), list.get(i).getFinalAnswer());
				newList.add(qc);
			}
			
			
			FileWriter fw = new FileWriter(f,true);
			List<Integer> correctOrder = new ArrayList<Integer>();
			List<Integer> wrongOrder = new ArrayList<Integer>();
			int correctNum = 0;
			int wrongNum = 0;
			for(int i = 0;i<list.size();i++) {
				if(list.get(i).getAnswerJudgement()>0) {
					correctOrder.add(list.get(i).getOrder());
					correctNum++;
				}else if(list.get(i).getAnswerJudgement()<0) {
					wrongOrder.add(list.get(i).getOrder());
					wrongNum++;
				}
			}
			fw.write("Correct:"+correctNum+"{");
			for(int i:correctOrder) {
				fw.write(i+" ");
			}fw.write("}\r\n");
			fw.write("Wrong:"+wrongNum+"{");
			for(int i:wrongOrder) {
				fw.write(i+" ");
			}fw.write("}\r\n");
			
			fw.write("RepeatDetail:\r\n");
			int repeatNum = 0;
			for(int i=0;i<newList.size()-1;i++) {
				QuestionCheck data = newList.get(i+1);
				if(!data.Isunique(newList)) {
					repeatNum++;
					fw.write("("+repeatNum+")"+(i+2)+"、"+data.str+"\r\n");
				}
			}
			fw.write("Repeat:"+repeatNum);
			
			fw.close();	
		}
		
		
}
package d1;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class QuestionMaker {


		//生成随机题目
		public static void generateQuestions(List<Question> list,int questionNum) {
			int order = 1;
			while(list.size() < questionNum) {
				try {
					Question ques = new Question((new Random().nextInt(3))%3+2, (new Random().nextInt(2))%2,order);
					list.add(ques);
					//=====================================================
					//控制台输出Question.toFinalString
					System.out.println((order++)+". "+ques.getToFinalString()+"="+ques.getFinalAnswer());
					
				} catch (Exception e) {
					// TODO 自动生成的 catch 块
					continue;
				}
			}
			

		}
}


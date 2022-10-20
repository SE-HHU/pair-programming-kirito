package d1;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class QuestionJudge{

	//比较文件答案和正确答案
	public static void setJudgement(List<Question> questionList) throws IOException {
		List<String> answerReadList = new ArrayList<String>();
		FileManagement.fileToAnswer(answerReadList, new File("D:\\AnswerList.txt"));
		int order = 0;
		while(order < questionList.size()) {
			if(questionList.get(order).getFinalAnswer().equals(answerReadList.get(order))){
				questionList.get(order).setAnswerJudgement(1);
			}else {
				questionList.get(order).setAnswerJudgement(-1);
			}
			order++;
		}
	}
	
	
}

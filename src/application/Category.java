package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * A Category object will hold a list of question objects and has utility for handling questions
 * @author Flynn Fromont
 *
 */
public class Category {
	private List<Question> _questions = new ArrayList<Question>();
	private String _name;
	private File _directory;
	private boolean _isComplete;
	
	/**
	 * create a specific category with a name and the directory that holds the files containing the questions
	 * @param name - name the category will be called
	 * @param dir - the directory of the category containing the question files
	 */
	public Category(String name, String dir) {
		_name = name;
		_directory = new File(dir);
		addQuestions();
		//sort the questions based on their value lowest first, highest last
		_questions.sort(new Comparator<Question>() {
			public int compare(Question q1, Question q2) {
				int n1 = q1.getValue();
				int n2 = q2.getValue();
				return Integer.compare(n1, n2);
			}
		});
		
	}
	/**
	 * converts question files in the given directory to a form where the information is used
	 * to create question objects that can easily model the questions and be used by the program
	 */
	private void addQuestions() {
		File[] files = _directory.listFiles();
		if(files != null) {
			for(File questionFile : files) {
				//for all the question files, read in the data
				FileInputStream fileInputStream = null;
				InputStreamReader inputStreamReader = null;
				BufferedReader bufferedReader = null;
				
				try {
					fileInputStream = new FileInputStream(questionFile);
					inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
					bufferedReader = new BufferedReader(inputStreamReader);
					
					//read the question files and combine the information across the lines
					String line;
					String combinedLines="";
					while((line = bufferedReader.readLine())!=null) {
						combinedLines+=line;
					} 
					//split the details into sections based around the ','
					String[] details = combinedLines.split(",");
					int value = Integer.parseInt(details[0]);//value parsed as an integer is the first section
					String question = details[1];//question is the second section
					String answer = details[2];//answer is the third section
					Boolean available = Boolean.parseBoolean(details[3]);//boolean parsed that represents if the question is available, or if already asked
					Question newQuestion = new Question(value,question,answer,available,this);//create a question objects representing the question
					_questions.add(newQuestion);
					bufferedReader.close();
					inputStreamReader.close();
					fileInputStream.close();
				} catch(IOException e) {
					e.printStackTrace();
				}
			}
	}
	}
	
	/**
	 * checks all the questions if any are still available (haven't been chosen yet)
	 * if any are available, return false,
	 * @return true if all questions have been asked, false if still some that have not been asked
	 */
	public boolean isComplete() {
		if(_isComplete==true) {
			return _isComplete;
		} else {
			_isComplete=true;
			for(Question question: _questions) {
				if(question.isAvailable()) {
					_isComplete=false;
				}
			}
		}
		return _isComplete;
		
	}
	/**
	 * returns category name
	 * @return
	 */
	public String getName() {
		return _name;
	}
	
	/**
	 * returns list of questions
	 * @return
	 */
	public List<Question> getQuestions(){
		return _questions;
	}
	/**
	 * displays questions in readable form on console, used for debugging purposes
	 */
	public void Display() {
		System.out.println(_name);
		for(Question q: _questions) {
			q.display();
		}
	}

}

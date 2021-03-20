package application;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


/**
 * Class that contains static functions for setting up/saving/reading data into and from files
 * @author Flynn Fromont
 *
 */
public class ProgramSetup {
	
	/**
	 * used to setup directory structure for the jeopardy program to easily read and write changes to files/data
	 */
	public static void setupData() {
		String path = "./.userData/";
		File dirUserData= new File(path);
		//create a new userData directory
		boolean isDirMade = dirUserData.mkdir();
		if(isDirMade) {
			try {
				path = "./.userData/score.txt";
				File data = new File(path);
				//create a file to hold the score of the user
				if(data.createNewFile()) {
					FileWriter writer = new FileWriter(path);
					writer.write("0");
					writer.close();
				}
				
				path="./.userData/currentCategories/";
				File dirCategory = new File(path);
				//create a directory to hold the current categories in play
				isDirMade = dirCategory.mkdir();
				if(isDirMade) {
					String categoryPath = "./categories/";
					File categoryDirectory = new File(categoryPath);
					File[] categories = categoryDirectory.listFiles();
					for(File category : categories) {
						String newCategory = path+category.getName()+"/";
						File newCategoryDir = new File(newCategory);
						isDirMade=newCategoryDir.mkdir();
						if(isDirMade) {
							FileInputStream fileInputStream = null;
							InputStreamReader inputStreamReader = null;
							BufferedReader bufferedReader = null;
							
							try {
								fileInputStream = new FileInputStream(category);
								inputStreamReader = new InputStreamReader(fileInputStream, "UTF-8");
								bufferedReader = new BufferedReader(inputStreamReader);
								
								//read the category file, and begin seperating into different lines
								String line;
								while((line = bufferedReader.readLine())!=null) {
									line.trim();
									line+=",true";//add a detail to questions that will be used to determine if question has been asked
									String[] details = line.split(",");
									String newFilePath = newCategory+details[0];
									File newQuestionFile = new File(newFilePath);
									//create new file for each question
									if(newQuestionFile.createNewFile()) {
										FileWriter writer = new FileWriter(newQuestionFile);
										writer.write(line);//write the details into the new file
										writer.close();
									}
								}
							} catch (IOException e) {
								e.printStackTrace();
							}
						}
					}
				}
				
			} catch (IOException e) {
				System.out.println("An error ocurred");
				e.printStackTrace();
			}
		}else {
			System.out.println("Error ocurred in creating new userData dir");
			
		}
	}
	
	/**
	 * used in saving the users score into a file form, to save between program open/closed
	 * @param score the score to save
	 */
	public static void saveScore(String score) {
		try {
			String path = "./.userData/score.txt";
			FileWriter writer = new FileWriter(path);
			writer.write(score);
			writer.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * used in updating the state of a question file, if it has been asked, so that between opening
	 * and closing the program, questions already asked cannot be asked again
	 * @param question - the question to update
	 */
	public static void saveQuestion(Question question) {
		String categoryName = question.getCategory().getName();
		String path ="./.userData/currentCategories/";
		File directory = new File(path);
		File[] categories = directory.listFiles();
		for(File category : categories) {
			//check for the category of the question
			if(category.getName().equals(categoryName)) {
				File[] questions = category.listFiles();
				for(File questionFile : questions) {
					//check for the correct question with the same value
					if(questionFile.getName().equals(question.getStringValue())) {
						try {
							//update the file to contain the new info with the available status set to false
							FileWriter writer = new FileWriter(questionFile);
							String newContent = question.getStringValue()+","+
									question.getQuestion()+","+question.getAnswer()+
									",false";
							writer.write(newContent);
							writer.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}
	}
	/**
	 * read the users last known score when the program was used previously from the saved file
	 * @return
	 */
	public static Integer readScore() {
		String path = "./.userData/score.txt";
		Integer score=0;
		try {
			
			File data = new File(path);
			Scanner reader = new Scanner(data);
			while(reader.hasNextLine()) {
				score = Integer.parseInt(reader.nextLine());
			}
			reader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return score;
	}
	
	/**
	 * load the categories that were in play last time, or that are currently saved in .userData
	 * @return the list of categories contained in a list for ease of use/manipulation in program
	 */
	public static List<Category> loadCurrentCategories(){
		List<Category> categories = new ArrayList<Category>();
		
		String path ="./.userData/currentCategories/";
		File directory = new File(path);
		File[] folders = directory.listFiles();
		if(folders != null) {
			for(File categoryFile : folders) {
				String name = categoryFile.getName();
				//create a new category to hold the category data in the files
				Category newCategory = new Category(name, categoryFile.getPath());
				categories.add(newCategory);
			}
		}
		
		return categories;
	}

	/**
	 * method used in deleting userData for easy reset
	 */
	public static void deleteUserData() {
		String path = "./.userData/";
		File dir = new File(path);
		deleteDir(dir);
	}
	/**
	 * recursive method used to go into directory and remove all the files in the directory
	 * @param dir
	 */
	private static void deleteDir(File dir) {
		File[] files = dir.listFiles();
		if (files != null) {
			for (final File file : files) {
				deleteDir(file);
			}
		}
		dir.delete();
				
	}
}

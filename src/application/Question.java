package application;

/**
 * a Question object holds all the data needed for a question to be in an easy to use form for the jeopardy program
 * @author Flynn Fromont
 *
 */
public class Question {
	private int value;
	private String question;
	private String answer;
	private boolean isAvailable;
	private Category _category;
	
	/**
	 * create a question object in a specific category
	 * @param value the score value of the question
	 * @param question the actual string of the question to be asked
	 * @param answer the correct answer for the question
	 * @param available boolean that represents if the question has been asked or not and if it is available
	 * @param category the category the question is from
	 */
	public Question(int value, String question, String answer, boolean available, Category category) {
		this.value=value;
		this.question=question;
		this.answer=answer;
		this.isAvailable=available;
		_category=category;
	}
	/**
	 * return the int value of the question
	 * @return
	 */
	public int getValue() {
		return value;
	}
	
	/**
	 * return the string value of the question
	 * @return
	 */
	public String getStringValue() {
		Integer stringValue = value;
		return stringValue.toString();
	}
	
	/**
	 * return the question 
	 * @return
	 */
	public String getQuestion() {
		return question;
	}
	/**
	 * return the correct answer for the question
	 * @return
	 */
	public String getAnswer() {
		return answer;
	}
	
	/**
	 * return whether the question is available or not
	 * @return
	 */
	public boolean isAvailable() {
		return isAvailable;
	}
	
	/**
	 * return the category the question is in
	 * @return
	 */
	public Category getCategory() {
		return _category;
	}
	/**
	 * checks whether a given answer is correct or not, case-insensitive
	 * @param response the users answer
	 * @return true if the user's response is corect, or false if they were wrong
	 */
	public boolean checkAnswerCorrect(String response) {
		this.isAvailable = false;
		String correctAnswer= answer.trim().toLowerCase();
		if(correctAnswer.equals(response.trim().toLowerCase())) {
			return true;
		}
		else {
			return false;
		}
	}
	
	/**
	 * displays question to the console, used for debugging purposes
	 */
	public void display() {
		System.out.println(value + ","+question+","+answer+",");
	}
}

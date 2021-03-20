package application;

import javafx.scene.Scene;

/**
 * JeopardyEvents are read by the main file and allow for control over the program
 * @author se2062020
 *
 */
public class JeopardyEvent {
	
	//current jeopardy events that can be sent out
	public enum EventType {QuestionAnswer,QuestionSelect, SceneChange}
	
	private EventType _type;
	private Scene _scene;
	private JeopardyModel _operand;
	private Question _question;
	private String _answer;
	
	/**
	 * create and fire an event for when a question has been answered
	 * @param model - the jeopardy model in control
	 * @param question - the question that has been answered
	 * @param answer - the users answer
	 * @return a new jeopardy event
	 */
	public static JeopardyEvent makeQuestionAnswerJeopardyEvent(JeopardyModel model,
			Question question, String answer) {
		return new JeopardyEvent(EventType.QuestionAnswer,model,question,answer);
	}
	
	/**
	 * create and fire an event for when a scene needs to be changed
	 * @param model - the jeopardy model in control
	 * @param scene the scene to change to
	 * @return a new jeopardy event
	 */
	public static JeopardyEvent makeSceneChangeEvent(JeopardyModel model,
			Scene scene) {
		return new JeopardyEvent(EventType.SceneChange,model,scene);
	}
	
	/**
	 * create and fire an event for when a question has been selected
	 * @param model - the jeopardy model in control
	 * @param question - the info on the question that has been selected
	 * @return a new jeopardy event
	 */
	public static JeopardyEvent makeQuestionSelectedEvent(JeopardyModel model,
			Question question) {
		return new JeopardyEvent(EventType.QuestionSelect,model,question);
	}
	
	/**
	 * constructor for jeopardy event
	 * @param type
	 * @param model
	 * @param question
	 * @param answer
	 */
	private JeopardyEvent(EventType type, JeopardyModel model,
			Question question, String answer) {
		_type=type;
		_operand=model;
		_question=question;
		_answer=answer;
	}
	
	/**
	 * constructor for jeopardy event
	 * @param type
	 * @param model
	 * @param scene
	 */
	private JeopardyEvent(EventType type, JeopardyModel model, Scene scene) {
		_type=type;
		_operand=model;
		_scene=scene;
	}
	/**
	 * constructor for jeopardy event
	 * @param type
	 * @param model
	 * @param question
	 */
	private JeopardyEvent(EventType type, JeopardyModel model, Question question) {
		_type=type;
		_operand=model;
		_question=question;
	}
	
	/**
	 * return event type
	 * @return
	 */
	public EventType eventType() {
		return _type;
	}
	
	/**
	 * return scene contained in event
	 * @return
	 */
	public Scene scene() {
		return _scene;
	}
	
	/**
	 * return the model controlling the event
	 * @return
	 */
	public JeopardyModel operand() {
		return _operand;
	}
	
	/**
	 * return the question contained in event
	 * @return
	 */
	public Question question() {
		return _question;
	}
	
	/**
	 * return the question answer contained in event
	 * @return
	 */
	public String answer() {
		return _answer;
	}
}

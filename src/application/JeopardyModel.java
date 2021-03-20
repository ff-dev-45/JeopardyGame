package application;

import java.util.ArrayList;

import java.util.List;
import javafx.scene.Scene;

/**
 * JeopardyModel that helps represent the program and assists in controlling the logic/function
 * @author Flynn Fromont
 *
 */
public class JeopardyModel {
	private Scene _main;
	private List<JeopardyModelListener> _listeners = new ArrayList<JeopardyModelListener>();
	
	/**
	 * create a jeopardyModel with a given main menu scene
	 * @param mainMenu - the main menu scene for the program
	 */
	public JeopardyModel(Scene mainMenu) {
		_main=mainMenu;
	}
	
	/**
	 * fire an event to listeners that a question has been selected
	 * @param question - the question that has been selected
	 */
	public void questionSelect(Question question) {
		fire(JeopardyEvent.makeQuestionSelectedEvent(this, question));
	}
	
	/**
	 * fire an event to listeners that a question has been answered
	 * @param question - the question that has been answered
	 * @param answer - the users answer for the question
	 */
	public void questionAnswer(Question question, String answer) {
		fire(JeopardyEvent.makeQuestionAnswerJeopardyEvent(this, question, answer));
	}
	
	/**
	 * fire an event to listeners that the scene is being changed
	 * @param sceneChange - the scene being changed to
	 */
	public void changeScene(Scene sceneChange) {
		fire(JeopardyEvent.makeSceneChangeEvent(this, sceneChange));
	}
	
	/**
	 * fire an event to listeners to return to the main menu
	 */
	public void mainMenu() {
		fire(JeopardyEvent.makeSceneChangeEvent(this, _main));
	}
	
	/**
	 * add a listener to this model to be updated for events
	 * @param listener
	 */
	public void addModelListener(JeopardyModelListener listener) {
		_listeners.add(listener);
	}
	
	/**
	 * whenever an event is created, fire is used to update all listeners with the event
	 * @param event the jeopardyEvent being sent by the model
	 */
	public void fire(JeopardyEvent event) {
		for(JeopardyModelListener listener: _listeners) {
			listener.update(event);
		}
	}
	
}

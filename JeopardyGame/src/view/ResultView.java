package view;

import application.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import application.JeopardyModel;

/**
 * The GUI view for the final result of an answered question
 * @author Flynn Fromont
 *
 */
public class ResultView {
	
	private JeopardyModel _model;
	private Question _question;
	private Button _btnReturnMenu;
	private Text _correctAnswer;
	private Text _valueChange;
	private VBox _panel;
	private Scene _resultScene;
	
	/**
	 * creates a view to be used/controlled by the jeopardy model
	 * @param model
	 */
	public ResultView(JeopardyModel model) {
		_model = model;
		
		_panel = new VBox();
		_panel.setSpacing(10);
		_resultScene = new Scene(_panel, 500, 150);
		_btnReturnMenu = new Button("Return to Menu!");
		
		//button to return to main menu
		_btnReturnMenu.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent arg0) {
				_model.mainMenu();
			}	
		});

		_correctAnswer = new Text();
		_correctAnswer.setFont(Font.font("Tahoma", 20));
		_valueChange = new Text();
		_valueChange.setFont(Font.font("Tahoma", 20));
	}
	
	/**
	 * Creates a scene specific to the result of an answered question
	 * @param info - the question that was answered by the user
	 * @param isCorrect - the result, either true=correct or false=incorrect
	 * @return the scene to be displayed
	 */
	public Scene setResultScene(Question info, boolean isCorrect) {
		_question = info;
		_panel.getChildren().clear();
		
		//if the user answered correctly create this versio
		if(isCorrect) {
			_correctAnswer.setText("Correct! ");
			_valueChange.setText("Your score has increased by "+_question.getStringValue()+
					" points!");
			
			_panel.getChildren().addAll(_correctAnswer,_valueChange,_btnReturnMenu);
			voiceResult();//uses festival to say correct!
		} else {//if the user answered incorrectly
			_correctAnswer.setText("Incorrect, the correct answer was "+_question.getAnswer());//displays correct answer
			_valueChange.setText("Your score has decreased by "+_question.getStringValue()+
					" points :(");
			_panel.getChildren().addAll(_correctAnswer,_valueChange,_btnReturnMenu);
			voiceResult();//uses festival to say the user is incorrect and tell them the correct answer
		}
		
		
		return _resultScene;
	}
	
	/**
	 * method used to read out the correct/incorrect text using festival bash process
	 */
	private void voiceResult() {
		try {
			String command = "echo "+_correctAnswer.getText()+" | festival --tts";
			ProcessBuilder pb = new ProcessBuilder("bash","-c", command);
			@SuppressWarnings("unused")
			Process process = pb.start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
	}
}

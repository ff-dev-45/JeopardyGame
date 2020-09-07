package view;

import application.JeopardyModel;
import application.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * the GUI view for the questions view
 * @author se2062020
 *
 */
public class QuestionView {
	protected Question _currentQuestion;
	private JeopardyModel _model;
	private TextField _answer;
	private Text _question;
	private Text _value;
	private Button _btnSubmit;
	private VBox _panel;
	private Scene _questionScene;
	private Process process;
	
	/**
	 * create a specific view for the questions to be used/controlled by the JeopardyModel
	 * @param model
	 */
	public QuestionView(JeopardyModel model) {
		_panel=new VBox();
		_questionScene= new Scene(_panel, 500,150);
		_answer= new TextField();
		_question = new Text();
		_value = new Text();
		_btnSubmit = new Button("Submit");
		
		_model=model;
		_value.setTranslateX(230);
		_value.setTranslateY(5);
		
		_panel.getChildren().addAll(_value,_question,_answer,_btnSubmit);
		_panel.setSpacing(10);
	}
	
	/**
	 * Sets the specific question that is being asked and returns the scene for it
	 * @param info - the specific question currently being asked 
	 * @return the scene representing the current question being asked
	 */
	public Scene setQuestion(Question info) {
		_currentQuestion = info;
		_question.setText(info.getQuestion());
		
		_answer.clear();
		
		Integer infoValue = info.getValue();
		_value.setText(infoValue.toString());
		
		voiceQuesiton();//read out the question
		
		//handler that will fire a question answer event for it to be evaluated elsewhere
		_btnSubmit.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent event) {
				_model.questionAnswer(_currentQuestion, _answer.getText());
				process.destroyForcibly();//destroys the speaking process when clicked
			}
		});
		
		
		
		return _questionScene;
	}
	
	/**
	 * method uses festival process so that the current question can be read out for the user to hear
	 */
	private void voiceQuesiton() {
		if (_currentQuestion != null) {
			try {
			String command = "echo Your Question for the value of "+_currentQuestion.getValue()+	
			_currentQuestion.getQuestion()+" | festival --tts";
			ProcessBuilder pb = new ProcessBuilder("bash","-c", command);
			process = pb.start();
			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

}

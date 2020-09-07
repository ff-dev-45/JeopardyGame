package application;

import java.io.File;

import java.util.ArrayList;
import java.util.List;

import application.JeopardyEvent.EventType;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import view.BoardView;
import view.QuestionView;
import view.ResultView;

/**
 * Main class and application for the Jeopardy game
 * @author Flynn Fromont
 *
 */
public class Jeopardy extends Application implements JeopardyModelListener{
	
	//These are for nodes of the main menu scene
	private Button _btnShow;
	private Button _btnQuestion;
	private Button _btnReset;
	private Text _title = new Text("Jeopardy");
	private Text _score = new Text("Score: 0");
	private int _scoreValue = 0;
	private BorderPane _root = new BorderPane();
	private Scene _main = new Scene(_root, 1000,700);
	private Stage _stage;
	private List<Category> _categories = new ArrayList<Category>();
	
	//These are all the views that are used
	private JeopardyModel _model= new JeopardyModel(_main);
	private QuestionView qView= new QuestionView(_model);
	private BoardView bView = new BoardView(_model);
	private ResultView rView = new ResultView(_model);
	
	/**
	 * This is the start of the application where the main stage gets passed in and
	 * the main menu is assigned as the scene and displays
	 */
	@Override
	public void start(Stage primaryStage) {
		_stage=primaryStage;
		_stage.setTitle("Jeopardy");
		intialise();//intilialise sets up the data for the program
		
		//This class is a listener of the JeopardyModel and reacts to the events that it fires
		_model.addModelListener(this);
		
		try {
			setup();//setup the gui for the main menu
			setupEventHandlers();//setup the event handlers for all the buttons
			_stage.setScene(_main);
			_stage.show();
			
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method is used to setup the GUI for the main menu page, and can be edited to 
	 * change how the main menu looks, it is very basic currently
	 */
	private void setup() {
		VBox btnPanel = new VBox();
		_btnShow = new Button("Display Questions");
		_btnQuestion = new Button("Answer a Question");
		_btnReset = new Button("Reset Game");
		
		_btnShow.setPrefWidth(200);
		_btnQuestion.setPrefWidth(200);
		_btnReset.setPrefWidth(200);
		
		btnPanel.setPadding(new Insets(15,12,14,12));
		btnPanel.setSpacing(10);
		btnPanel.getChildren().addAll(_btnShow, _btnQuestion, _btnReset);
		btnPanel.setAlignment(Pos.CENTER);
		
		_title.setFont(Font.font("Verdana",120));
		_title.setTranslateX(250);
		_title.setTranslateY(100);
		
		_score.setFont(Font.font("Tahoma", 80));
		_score.setTranslateX(300);
		_score.setTranslateY(-100);
		
		_root.setTop(_title);
		_root.setCenter(btnPanel);
		_root.setBottom(_score);
	
	}
	
	/**
	 * this method sets up all the event handlers for the different buttons so that
	 * they will respond with the correct event when selected
	 */
	private void setupEventHandlers() {
		//The handler for the display of questions, questions cannoy be selected while in this view
		_btnShow.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				boolean isQuestionable = false;
				_model.changeScene(bView.showQuestionBoard(_categories,isQuestionable));
			}
		});
		
		//The handler for moving to the board view where questions can be selected to attempt an answer
		_btnQuestion.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				boolean isQuestionable = true;
				_model.changeScene(bView.showQuestionBoard(_categories,isQuestionable));
			}
		});
		
		//The handler for the reset button, this causes the questions to be reset and data to be reset to 0
		_btnReset.setOnAction(new EventHandler<ActionEvent>() { 
			@Override
			public void handle(ActionEvent event) {
				ProgramSetup.deleteUserData();
				intialise();//calls intialise to setup the program from the start
				_scoreValue=0;
				Integer stringValue = _scoreValue;
				_score.setText("Score: "+stringValue.toString());
				_model.mainMenu();//calls a change back to main menu to update score node
			}
		});
	}
	
	/**
	 * intialise is run at the very start of the program and sets up the data for the program,
	 * if the directory './.userData' already exists then it will read the data to setup the pre-existing user data
	 * if the directory does not exist then it will call the static programSetup functions to create the userData directories
	 * so that the program is able to save the data whenever changes happen to the data to save the changes
	 */
	private void intialise() {
		String path="./.userData/";
		File data = new File(path);
		//if the directory exists, choose to read from the files within
		if(data.exists()) {
			//reads the players score previously
			Integer score = ProgramSetup.readScore();
			_scoreValue=score;
			_score.setText("Score: "+score.toString());
			//loads the current categories and questions that havent been answered
			_categories = ProgramSetup.loadCurrentCategories();
		} else {//if the user data directory does not exist create it using .setupData()
		ProgramSetup.setupData();//creates the required directory structure for the program
		_categories = ProgramSetup.loadCurrentCategories();//loads the categories into a readable form
		}
		
	}
	/**
	 * update responds to any JeopardyEvents fired from the jeopardyModel
	 * The program will respond to the events and cause different scenes to be displayed when required
	 * and manipulate data.
	 */
	@Override
	public void update(JeopardyEvent event) {
		//When a question has been answered the program checks if the user is correct or incorrect
		if (event.eventType()==EventType.QuestionAnswer) {
			String answer = event.answer();
			Question question = event.question();
			int value = question.getValue();
			boolean isCorrect;
			if(question.checkAnswerCorrect(answer)) {
				//if the user is correct add to the score
				_scoreValue += value;
				isCorrect=true;
				
			} else {
				//if the user is incorrect, remove from their score
				_scoreValue -= value;
				isCorrect=false;
			}
			//update the score counter on the main menu
			Integer stringValue = _scoreValue;
			_score.setText("Score: "+stringValue.toString());
			
			//save the score into the user data
			ProgramSetup.saveScore(stringValue.toString());
			//save into the categories that the question has been answered
			ProgramSetup.saveQuestion(question);
			//change the scene to display the result scene
			_model.changeScene(rView.setResultScene(event.question(),isCorrect));
		}
		else if(event.eventType()==EventType.SceneChange) {
			//change the scene to whichever scene is given in the event
			_stage.setScene(event.scene());
		}
		else if(event.eventType()==EventType.QuestionSelect) {
			//change the scene the selected question scene
			_model.changeScene(qView.setQuestion(event.question()));
		}
	}

	/*
	 * main method for launching the program
	 */
	public static void main(String[] args) {
		launch(args);
	}


}

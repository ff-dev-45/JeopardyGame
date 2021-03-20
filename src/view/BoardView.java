package view;


import java.util.List;
import application.Category;
import application.JeopardyModel;
import application.Question;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

/**
 * Class that represents the question board view GUI
 * @author Flynn Fromont
 *
 */
public class BoardView {
	private JeopardyModel _model;
	private VBox _mainPane;
	private GridPane _gridPane;
	private Scene _boardScene;
	private Text _selectable;
	private Button _btnReturnMenu;
	
	/**
	 * construct a view object that will be used/controlled by the JeopardyModel
	 * @param model
	 */
	public BoardView(JeopardyModel model) {
		_model=model;
		
		_mainPane = new VBox();
		_gridPane = new GridPane();
		
		_gridPane.setHgap(20);
		_gridPane.setVgap(10);
		
		_mainPane.setSpacing(30);
		
		_btnReturnMenu = new Button("Return to menu");
		_btnReturnMenu.setOnAction(new EventHandler<ActionEvent>() {
			//when the main menu button is clicked change back to the main menu
			@Override
			public void handle(ActionEvent arg0) {
				_model.mainMenu();
			}
			
		});
		
		_selectable = new Text("Click a button with the value on it to attempt to answer the question:)");
		_mainPane.getChildren().addAll(_selectable,_gridPane,_btnReturnMenu);
		
		_boardScene = new Scene(_mainPane, 500,700);
		
		
	}
	/**
	 * showQuestionBoard will return the question board scene with all the given questions that are currently
	 * available from the categories, the board will be in view mode or question mode depending on the button used to call the view
	 * @param categories - the list of categories currently in play for the program
	 * @param isQuestionable - a boolean value representing if the board is in view mode or question mode
	 * @return the QuestionBoard scene to be displayed
	 */
	public Scene showQuestionBoard(List<Category> categories, boolean isQuestionable) {
		//runs a check to see if all the questions have been asked
		boolean isComplete = checkCompletion(categories);
		int i=0;
		int j =0;
		_gridPane.getChildren().clear();
		for(Category ctg : categories) {
			Text category = new Text(ctg.getName());//category name to be displayed above the questions
			j=0;
			_gridPane.add(category, i, j);
			j++;
			//if the category is complete, display a text saying so
			if(ctg.isComplete()) {
				Text completed = new Text("completed!");
				_gridPane.add(completed, i, j);
			}
			j++;
			//as long as there are questions that have not been asked run through to display available questions
			if(isComplete==false) {
				for(Question question : ctg.getQuestions()) {
					//if the question is available, proceed to create a button for it
					if(question.isAvailable()) {
						Button btnQuestion = new Button(question.getStringValue());
						//if the board is in question mode, create a handler for the button
						//if the board is in view mode, do not create a handler
						if(isQuestionable) {
							btnQuestion.setOnAction(new EventHandler<ActionEvent>() {
								@Override
								public void handle(ActionEvent event) {
									//when the question is selected proceed to the question scene
									//with the question this button represents as the data to be displayed
									_model.questionSelect(question);
								}

							});
						}
						btnQuestion.setPrefSize(100, 80);
						_gridPane.add(btnQuestion, i, j);
					}
					j++;
				}
				i++;
			} else {
				i++;
			}
		}
		if(isComplete==true) {
			_selectable.setText("You have answered all the questions, please reset the game :)");
		} else {
			_selectable.setText("Click a button with the value on it to attempt to answer the question:)");
		}
		return _boardScene;
	}
	
	/**
	 * method that runs through all the categories and questions to see if any questions remain,
	 * returns true if all the questions have been asked, false if a question remains
	 * @param categories - the categories currently in play
	 * @return boolean true/false if questions are all done or not
	 */
	private boolean checkCompletion(List<Category> categories) {
		boolean _isComplete=true;
		
		for(Category ctg: categories) {
			if(!ctg.isComplete()) {
				_isComplete=false;
			}
		}
		return _isComplete;
	}
}

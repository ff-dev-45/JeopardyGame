# Jeopardy Game

Hello! 
This game was made to run in a linux ubuntu system and uses process builder to run Bash commands for 'festival' (a text-to-speech library in the ubuntu system used).
The game also requires javaFX 11.0.2 and requires the pathway in the Jeopardy.sh file to locate the jar and use it.

festival found here (http://www.cstr.ed.ac.uk/projects/festival/manual/festival_6.html)
JavaFX found here (https://gluonhq.com/products/javafx/)

To run this jar and the program
1. Change the pathway for the Jeopardy.sh script file to where JavaFx 11.0.2 jar is located

2. Please make sure you have the ./categories folder that contains the category with question files in the same directory
as the Jeopardy.jar and Jeopardy.sh

3. Make sure you have executable permission for Jeopardy.sh

4. Run Jeopardy.sh in the command line in the directory that contains './categories/','Jeopardy.jar' & 'Jeopardy.sh'

5. After a few seconds a gui should pop up where you can then start playing the game,
	- show question board will bring up a view-only board of the questions
	- answer a question will bring up the same board where you are also able to select questions to try and answer
	- reset will reset the userData for that session and the score/questions will all be reset
	
6. Questions/categories should be in the original format that was given in the first assignment, and the program will use the files contained within ./categories/ to create its own set that it can read/edit for the program

-ff-dev-45



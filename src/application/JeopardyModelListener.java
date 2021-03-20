package application;

/**
 * interface representing the required method needed for any jeopardyEvent listeneres
 * @author se2062020
 *
 */
public interface JeopardyModelListener {
	
		/**
		 * update, the listener must implement this method so that is will be notified
		 * whenever a jeopardyEvent is sent out
		 * @param event - the jeopardy event being sent out
		 */
		public void update(JeopardyEvent event);
}

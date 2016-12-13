package tr.gov.tuik.activitilib.listener;

import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.TaskListener;

public abstract class TUIKAbstractTaskListener implements TaskListener 
{

	private static final long serialVersionUID = -1374095352237404240L;

	/** Returns when the listener will be executed and should be one of the followings
	 *  TaskListener.EVENTNAME_CREATE
	 *  TaskListener.EVENTNAME_ASSIGNMENT
	 *  TaskListener.EVENTNAME_COMPLETE
	 *  TaskListener.EVENTNAME_DELETE
	 * @return
	 */
	public abstract List<String> getEventTypes();
	
	
	/**
	 * Set of task definition keys for adding the task listener
	 * empty value means adding all tasks of the process
	 * @return
	 */
	public abstract Set<String> getTaskDefinitionKeys();

	/**
	 * Set of process definition keys for adding the task listener
	 * empty value means adding all process deployments
	 * @return
	 */
	public abstract Set<String> getProcessDefinitionKeys();

}

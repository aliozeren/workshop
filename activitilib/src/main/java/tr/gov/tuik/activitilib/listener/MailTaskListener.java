package tr.gov.tuik.activitilib.listener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

public class MailTaskListener extends TUIKAbstractTaskListener
{

	private static final long serialVersionUID = 5898277359770033930L;
	
	private static List<String> EVENT_TYPES = new ArrayList<String>();
	static {
		EVENT_TYPES.add(TaskListener.EVENTNAME_CREATE);
	}

	private static Set<String> PROCESSES = null;

	private static Set<String> TASKS = new HashSet<String>();
	
	
	public void notify(DelegateTask delegateTask) 
	{
		System.out.println("Sending mail to " + delegateTask.getAssignee());
	}

	@Override
	public List<String> getEventTypes() 
	{
		return MailTaskListener.EVENT_TYPES;
	}

	@Override
	public Set<String> getTaskDefinitionKeys() 
	{
		return MailTaskListener.TASKS;
	}

	@Override
	public Set<String> getProcessDefinitionKeys() 
	{
		return MailTaskListener.PROCESSES;
	}

}

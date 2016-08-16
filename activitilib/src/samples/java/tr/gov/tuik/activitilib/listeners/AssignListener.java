package tr.gov.tuik.activitilib.listeners;


import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.log4j.Logger;

public class AssignListener implements TaskListener
{

	private static final long serialVersionUID = -6131745264843448379L;
	
	private final static Logger logger = Logger.getLogger(AssignListener.class);


	public void notify(DelegateTask delegateTask) {
		logger.debug("Assign Listener....");
		System.out.println("Assign Listener. sysout...");
	}

}

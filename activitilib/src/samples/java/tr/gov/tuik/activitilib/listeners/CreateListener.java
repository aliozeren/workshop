package tr.gov.tuik.activitilib.listeners;


import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.log4j.Logger;

public class CreateListener implements TaskListener
{

	private static final long serialVersionUID = -6131745264843448379L;
	
	private final static Logger logger = Logger.getLogger(CreateListener.class);


	public void notify(DelegateTask delegateTask) {
		logger.debug("Create Listener....");
		System.out.println("Create Listener. sysout...");
	}

}

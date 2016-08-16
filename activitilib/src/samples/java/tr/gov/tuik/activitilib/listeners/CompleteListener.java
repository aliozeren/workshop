package tr.gov.tuik.activitilib.listeners;


import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;
import org.apache.log4j.Logger;

public class CompleteListener implements TaskListener
{

	private static final long serialVersionUID = -6131745264843448379L;
	
	private final static Logger logger = Logger.getLogger(CompleteListener.class);


	public void notify(DelegateTask delegateTask) {
		
		logger.debug("Complete Listener....");
		
		System.out.println("Complete Listener. sysout...");
		
		delegateTask.setVariable("degisken", "H");

		System.out.println("Variable is set... sysout...");
		
	}

}

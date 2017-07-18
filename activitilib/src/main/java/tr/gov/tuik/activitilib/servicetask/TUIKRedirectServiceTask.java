package tr.gov.tuik.activitilib.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKFormService;

/**
 * The class is a ready-to-use service task which redirects the execution to the specified 
 * task (redirectTo) if the the given condition (redirectCondition) is true   
 * See :TUIKServiceTaskBpmnParseHandler class for the implementation 
 */
public class TUIKRedirectServiceTask implements JavaDelegate 
{
	
	private final static Logger logger = Logger.getLogger(TUIKFormService.class);

	private Expression redirectTo;

	private Expression redirectCondition;

	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{
		Boolean redirectConditionLocal = (Boolean) redirectCondition.getValue(execution);

		if (redirectConditionLocal) {
			String redirectToLocal = (String) redirectTo.getValue(execution);
			logger.info("Redirecting the process to " + redirectToLocal );
		}
	}

}

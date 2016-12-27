package tr.gov.tuik.activitilib.servicetask;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.Expression;
import org.activiti.engine.delegate.JavaDelegate;
import org.activiti.engine.impl.ProcessEngineImpl;

import tr.gov.tuik.activitilib.TUIKProcessEngine;
import tr.gov.tuik.activitilib.utils.TUIKSetActivitiIdCmd;

public class TUIKRedirectServiceTask implements JavaDelegate 
{

	private Expression redirectTo;
	private Expression redirectCondition;

	@Override
	public void execute(DelegateExecution execution) throws Exception 
	{

		Boolean redirectConditionLocal = (Boolean) redirectCondition.getValue(execution);

		if (redirectConditionLocal) {

			String redirectToLocal = (String) redirectTo.getValue(execution);

			
//			will be uncommented after tests			
			((ProcessEngineImpl)TUIKProcessEngine.getInstance().getProcessEngine())
			.getProcessEngineConfiguration()
			.getCommandExecutor()
			.execute(new TUIKSetActivitiIdCmd(execution.getId(), redirectToLocal));

		}
	}

}

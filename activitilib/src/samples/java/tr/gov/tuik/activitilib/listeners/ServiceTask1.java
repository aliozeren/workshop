package tr.gov.tuik.activitilib.listeners;

import org.activiti.engine.delegate.BpmnError;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

public class ServiceTask1 implements JavaDelegate {

	public void execute(DelegateExecution execution) throws Exception 
	{
		System.out.println("Service Task 1 completed...");
		
		 throw new BpmnError("serviceTask1");
	}

}

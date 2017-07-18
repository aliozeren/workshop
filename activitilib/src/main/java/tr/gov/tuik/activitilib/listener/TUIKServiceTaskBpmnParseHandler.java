package tr.gov.tuik.activitilib.listener;

import java.util.List;

import org.activiti.bpmn.model.FieldExtension;
import org.activiti.bpmn.model.ServiceTask;
import org.activiti.engine.impl.Condition;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.SequenceFlowParseHandler;
import org.activiti.engine.impl.bpmn.parser.handler.ServiceTaskParseHandler;
import org.activiti.engine.impl.el.UelExpressionCondition;
import org.activiti.engine.impl.pvm.process.ActivityImpl;
import org.activiti.engine.impl.pvm.process.TransitionImpl;
import org.apache.commons.lang3.StringUtils;

/**
 * @alio
 * 
 * if the service task is the implementation of TUIKRedirectServiceTask;
 * 	 Parser adds a flow to the the task with the id "redirectTo" (service task field)
 * 	 with the given condition (if any) with the field "redirectCondition" from the service task
 */
public class TUIKServiceTaskBpmnParseHandler extends ServiceTaskParseHandler 
{

  protected String serviceTaskName = "tr.gov.tuik.activitilib.servicetask.TUIKRedirectServiceTask";
  
  protected String taskFieldName = "redirectTo";
  
  protected String conditionFieldName = "redirectCondition";
	
	
  protected void executeParse(BpmnParse bpmnParse, ServiceTask serviceTask) 
  {

	assert( this.serviceTaskName != null && this.taskFieldName != null && this.conditionFieldName != null );
	
    if (this.serviceTaskName.equals(serviceTask.getImplementation())) {
    	
    	ActivityImpl activity = findActivity(bpmnParse, serviceTask.getId());

    	List<FieldExtension> fext = serviceTask.getFieldExtensions();
    	
    	String redirectTo = null; 
    	String redirectCondition = null;
    	
    	for ( FieldExtension fe: fext) {
    		if (this.taskFieldName.equals(fe.getFieldName())) {
    			redirectTo = fe.getStringValue();
    		}
    		if (this.conditionFieldName.equals(fe.getFieldName())) {
    			redirectCondition = fe.getExpression();
    		}
    	}
    	
    	if (redirectTo != null) {
    		
    		String transitionId = serviceTask.getId() + "_" + redirectTo;
    		
    		// check if the transition is created before 
    		if (activity.findOutgoingTransition(transitionId) == null) {
    			
    			// create the transition to the task
    			TransitionImpl transition = activity.createOutgoingTransition(transitionId);
    			transition.setDestination(findActivity(bpmnParse, redirectTo));

    			// set the transition condition if given 
    			if (redirectCondition != null) {
    				Condition expressionCondition = new UelExpressionCondition(redirectCondition);
    				transition.setProperty(SequenceFlowParseHandler.PROPERTYNAME_CONDITION_TEXT, redirectCondition);
    				transition.setProperty(SequenceFlowParseHandler.PROPERTYNAME_CONDITION, expressionCondition);    
    			}
    		}
    		
    	}
    }
    
  }


	public void setServiceTaskName(String serviceTaskName) 
	{
		this.serviceTaskName = StringUtils.trimToNull(serviceTaskName);
	}
	
	
	public void setTaskFieldName(String taskFieldName) 
	{
		this.taskFieldName = StringUtils.trimToNull(taskFieldName);
	}
	
	
	public void setConditionFieldName(String conditionFieldName) 
	{
		this.conditionFieldName = StringUtils.trimToNull(conditionFieldName);
	}

}
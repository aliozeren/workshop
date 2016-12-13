package tr.gov.tuik.activitilib.listener;

import java.util.List;

import org.activiti.bpmn.model.BaseElement;
import org.activiti.bpmn.model.UserTask;
import org.activiti.engine.impl.bpmn.parser.BpmnParse;
import org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler;
import org.activiti.engine.impl.persistence.entity.ProcessDefinitionEntity;
import org.activiti.engine.impl.task.TaskDefinition;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngineException;
import tr.gov.tuik.activitilib.utils.TUIKUtils;

public class TUIKUserTaskBpmnParseHandler extends AbstractBpmnParseHandler<UserTask> 
{

	private final static Logger logger = Logger.getLogger(TUIKUserTaskBpmnParseHandler.class);
	
	protected List<String> customTaskListeners; 

	public TUIKUserTaskBpmnParseHandler() 
	{
	}

	@Override
	protected Class< ? extends BaseElement> getHandledType()
	{
		return UserTask.class;
	}

	/* (non-Javadoc)
	 * @see org.activiti.engine.impl.bpmn.parser.handler.AbstractBpmnParseHandler#executeParse(org.activiti.engine.impl.bpmn.parser.BpmnParse, org.activiti.bpmn.model.BaseElement)
	 */
	@Override
	protected void executeParse(BpmnParse bpmnParse, UserTask element) 
	{
		if (this.customTaskListeners != null && this.customTaskListeners.size() > 0) {
			
			String taskDefinitionKey = element.getId();
			
			ProcessDefinitionEntity process = (ProcessDefinitionEntity) bpmnParse.getCurrentScope().getProcessDefinition();

			String processDefinitionKey= process.getKey();
			TaskDefinition taskDefinition = process.getTaskDefinitions().get(taskDefinitionKey);
			
			for (String taskListenerClass : this.customTaskListeners) {
				
				try {
					
					Object obj= Class.forName(taskListenerClass).newInstance();
					
					if (obj instanceof TUIKAbstractTaskListener) {
						
						TUIKAbstractTaskListener listener = (TUIKAbstractTaskListener) obj;
						
						// if parsed process is not related to task listener, ignore it (empty set means add listener to all processes)
						if (listener.getProcessDefinitionKeys() != null 
								&& listener.getProcessDefinitionKeys().size() > 0 
								&& !listener.getProcessDefinitionKeys().contains(processDefinitionKey)) {
							continue;
						}
						
						// if parsed task is not related to task listener, ignore it (empty set means add listener to all tasks)
						if (listener.getTaskDefinitionKeys() != null 
								&& listener.getTaskDefinitionKeys().size() > 0 
								&& !listener.getTaskDefinitionKeys().contains(taskDefinitionKey)) {
							continue;
						}

						for (String type : listener.getEventTypes()) {
							taskDefinition.addTaskListener(type, listener);
						}
						
					} else {
						throw new TUIKProcessEngineException("Listener class should be subclass of  TUIKAbstractTaskListener : " + taskListenerClass);
					}
					
				} catch (InstantiationException|IllegalAccessException|ClassNotFoundException e) {
					TUIKUtils.getInstance().logError(logger, e);
					throw new TUIKProcessEngineException("Cannot create object for class named : " + taskListenerClass);
				}
				
				
			}
		}
	}


	public void setCustomTaskListeners(List<String> customTaskListeners) 
	{
		this.customTaskListeners = customTaskListeners;
	}
}
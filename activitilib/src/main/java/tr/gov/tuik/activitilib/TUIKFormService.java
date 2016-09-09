package tr.gov.tuik.activitilib;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.apache.log4j.Logger;

public class TUIKFormService 
{
private final static Logger logger = Logger.getLogger(TUIKFormService.class);
	
	private static TUIKFormService instance;

	private TUIKFormService() {
	}

	public static TUIKFormService getInstance() 
	{
		if (instance == null) {
			logger.debug("TUIKFormService instance is created...");
			instance= new TUIKFormService();
		}
		return instance;
	}

	private ProcessEngine getProcessEngine()
	{
		return TUIKProcessEngine.getInstance().getProcessEngine();
	}
	
	public FormService getFormService() 
	{
		return getProcessEngine().getFormService();
	}
	
	public TaskFormData getTaskForm(String taskId)
	{
		return getFormService().getTaskFormData(taskId);
	}
	
	public StartFormData getStartForm(String processDefinitionKey)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.orderByDeploymentId()
				.desc()
				.list()
				.get(0);
		
		return getFormService().getStartFormData(definition.getId());
	}

	public Object getRenderedStartForm(String processDefinitionKey)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.orderByDeploymentId()
				.desc()
				.list()
				.get(0);

		
		return getFormService().getRenderedStartForm(definition.getId());
		
	}
	
	public Object getRenderedTaskForm(String taskId)
	{
		return getFormService().getRenderedTaskForm(taskId);
	}
	
	
}

package tr.gov.tuik.activitilib;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.types.ActivitiFormTypeInterface;
import tr.gov.tuik.activitilib.types.FormToMapConverter;

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
	

	/**
	 * @param taskId
	 * @return
	 */
	public TaskFormData getTaskForm(String taskId)
	{
		return getFormService().getTaskFormData(taskId);
	}

	/**
	 * @param taskId
	 * @return
	 */
	public List<?> getRenderedTaskForm(String taskId)
	{
		
		TaskFormData taskForm = getFormService().getTaskFormData(taskId);
		
		assert(taskForm != null);
		
		Map<String, Object> variables = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService().getVariables(taskId);
		
		return renderProperties(taskForm.getFormProperties(), variables);
	}
	
	/**
	 * @param processDefinitionKey
	 * @return
	 */
	public StartFormData getStartForm(String processDefinitionKey)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();

		return getFormService().getStartFormData(definition.getId());
	}

	/**
	 * @param processDefinitionId
	 * @return
	 */
	public StartFormData getStartFormByProcessId(String processDefinitionId)
	{
		return getFormService().getStartFormData(processDefinitionId);
	}

	/**
	 * @param processDefinitionKey
	 * @return
	 */
	public List<?> getRenderedStartForm(String processDefinitionKey)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();

		StartFormData startForm = getFormService().getStartFormData(definition.getId());
		
		return renderProperties(startForm.getFormProperties(), null);
		
	}
	
	/**
	 * @param processDefinitionId
	 * @return
	 */
	public List<?> getRenderedStartFormByProcessId(String processDefinitionId)
	{
		StartFormData startForm = getFormService().getStartFormData(processDefinitionId);
		
		return renderProperties(startForm.getFormProperties(), null);
		
	}	

	/**
	 * @param processDefinitionKey
	 * @param converter
	 * @param properties
	 * @return
	 */
	public ProcessInstance submitStartFormData(String processDefinitionKey, FormToMapConverter converter, Object properties)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();
		
		
		return getFormService().submitStartFormData(definition.getId(), converter.formToMap(properties));
	}
	
	
	/**
	 * @param processDefinitionId
	 * @param converter
	 * @param properties
	 * @return
	 */
	public ProcessInstance submitStartFormDataByProcessId(String processDefinitionId, FormToMapConverter converter, Object properties)
	{
		return getFormService().submitStartFormData(processDefinitionId, converter.formToMap(properties));
	}
	
	
	/**
	 * @param taskId
	 * @param converter
	 * @param properties
	 */
	public void submitTaskFormData(String taskId, FormToMapConverter converter, Object properties)
	{
		getFormService().submitTaskFormData(taskId, converter.formToMap(properties));
	}

	
	/**
	 * @param taskId
	 * @param converter
	 * @param properties
	 */
	public void saveFormData(String taskId, FormToMapConverter converter, Object properties)
	{
		getFormService().saveFormData(taskId, converter.formToMap(properties));
	}

	
	/**
	 * @param list
	 * @param variables 
	 * @return
	 */
	public List<?> renderProperties(List<FormProperty> list, Map<String, Object> variables)
	{
		
		List<Object> renderedForm= new ArrayList<Object>();
		
		if (list != null) {
			for (FormProperty property : list) {
				if (property.getType() instanceof ActivitiFormTypeInterface) {
					ActivitiFormTypeInterface prop= (ActivitiFormTypeInterface) property.getType();
					Object variable = null;
					
					if (variables != null && !variables.isEmpty() && prop.getVariable() != null) {
						variable = variables.get(variable);
						
					}
					renderedForm.add(prop.renderInput(property, variable != null ? variable.toString() : null));
				}
			}
		}
		return renderedForm;
	}

}

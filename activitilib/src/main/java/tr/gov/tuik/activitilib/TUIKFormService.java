package tr.gov.tuik.activitilib;

import java.util.List;

import org.activiti.engine.FormService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.activiti.engine.form.TaskFormData;
import org.activiti.engine.impl.ProcessEngineImpl;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.utils.TUIKFormToMapConverterInterface;
import tr.gov.tuik.activitilib.utils.TUIKFormValuesResolveCmd;

/**
 * The class is defined in order to simplfy the usage of the form services in activiti process engine.
 * @author alio
 *
 */
public class TUIKFormService 
{
	private final static Logger logger = Logger.getLogger(TUIKFormService.class);

	private static TUIKFormService instance;

	private TUIKFormService() {
	}

	/**
	 * 
	 * Returns the singleton instance of the class
	 */
	public static TUIKFormService getInstance() 
	{
		if (instance == null) {
			logger.debug("TUIKFormService instance is created...");
			instance= new TUIKFormService();
		}
		return instance;
	}

	/**
	 * Returns the activiti process engine
	 */
	private ProcessEngine getProcessEngine()
	{
		return TUIKProcessEngine.getInstance().getProcessEngine();
	}

	/**
	 * Returns the activiti form service
	 */
	public FormService getFormService() 
	{
		return getProcessEngine().getFormService();
	}


	/**
	 * Returns the form definition of the task with the given id
	 * @param taskId
	 * @return
	 */
	public TaskFormData getTaskForm(String taskId)
	{
		return getFormService().getTaskFormData(taskId);
	}

	/**
	 * Returns the rendered form of the task with the given id.
	 * @param taskId
	 * @return
	 */
	public List<?> getRenderedTaskForm(String taskId)
	{

		TaskFormData taskForm = getFormService().getTaskFormData(taskId);

		assert(taskForm != null);

		return renderProperties(taskForm.getFormProperties(), taskId);
	}

	/**
	 * Returns the form definition of the start task of the given process
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
	 * Returns the form definition of the start task of the given process
	 * @param processDefinitionId
	 * @return
	 */
	public StartFormData getStartFormByProcessId(String processDefinitionId)
	{
		return getFormService().getStartFormData(processDefinitionId);
	}

	/**
	 * Returns the rendered form of the start task of the given process
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
	 * Returns the rendered form of the start task of the given process
	 * @param processDefinitionId
	 * @return
	 */
	public List<?> getRenderedStartFormByProcessId(String processDefinitionId)
	{
		StartFormData startForm = getFormService().getStartFormData(processDefinitionId);

		return renderProperties(startForm.getFormProperties(), null);

	}	

	/**
	 * Starts the process by submitting the start form data
	 * @param processDefinitionKey
	 * @param converter - converts the form data object properties to the activiti variable map type (Map<String, String>)
	 * @param properties 
	 * @return
	 */
	public ProcessInstance submitStartFormData(String processDefinitionKey, TUIKFormToMapConverterInterface converter, Object properties)
	{
		ProcessDefinition definition = TUIKProcessEngine.getInstance().getProcessEngine().getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();


		return getFormService().submitStartFormData(definition.getId(), converter.formToMap(properties));
	}


	/**
	 * Starts the process by submitting the start form data
	 * @param processDefinitionId
	 * @param converter - converts the form data object properties to the activiti variable map type (Map<String, String>)
	 * @param properties 
	 * @return
	 */
	public ProcessInstance submitStartFormDataByProcessId(String processDefinitionId, TUIKFormToMapConverterInterface converter, Object properties)
	{
		return getFormService().submitStartFormData(processDefinitionId, converter.formToMap(properties));
	}


	/**
	 * Submits and completes the task
	 * @param taskId
	 * @param converter - converts the form data object properties to the activiti variable map type (Map<String, String>)
	 * @param properties
	 */
	public void submitTaskFormData(String taskId, TUIKFormToMapConverterInterface converter, Object properties)
	{
		getFormService().submitTaskFormData(taskId, converter.formToMap(properties));
	}


	/**
	 * Saves the task (not complete)
	 * @param taskId
	 * @param converter - converts the form data object properties to the activiti variable map type (Map<String, String>)
	 * @param properties
	 */
	public void saveFormData(String taskId, TUIKFormToMapConverterInterface converter, Object properties)
	{
		getFormService().saveFormData(taskId, converter.formToMap(properties));
	}


	/**
	 * Renderes the form properties (inputs) in the given task instance
	 * @param list
	 * @param taskId
	 * @return
	 */
	public List<Object> renderProperties(List<FormProperty> list, String taskId) 
	{
		return ((ProcessEngineImpl)TUIKProcessEngine.getInstance().getProcessEngine())
				.getProcessEngineConfiguration()
				.getCommandExecutor()
				.execute(new TUIKFormValuesResolveCmd(list, taskId));
	}

}

package tr.gov.tuik.activitilib;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.imageio.ImageIO;

import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.DiagramLayout;
import org.activiti.engine.repository.DiagramNode;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;

public class TUIKProcessEngine 
{

	private final static Logger logger = Logger.getLogger(TUIKProcessEngine.class);

	private static TUIKProcessEngine instance;

	private static ProcessEngineConfiguration pec;
	private static ProcessEngine processEngine;

	private TUIKProcessEngine() {
	}

	public static TUIKProcessEngine getInstance() 
	{
		if (instance == null) {

			instance= new TUIKProcessEngine();
			ProcessEngines.init();
			pec = ProcessEngineConfiguration.createProcessEngineConfigurationFromResourceDefault();
			pec.setDatabaseSchemaUpdate("none");
			processEngine = pec.buildProcessEngine();
			logger.debug("TUIKProcessEngine instance is created...");

		}
		return instance;
	}

	public static void destroy() 
	{
		if (instance != null) {
			ProcessEngines.unregister(processEngine);
			ProcessEngines.destroy();
			instance = null;
		}
	}

	public ProcessEngine getProcessEngine() 
	{
		return TUIKProcessEngine.processEngine;
	}

	/**
	 * Deploys the model definition in the given path with the given name
	 * @param name
	 * @param resourcePath
	 */
	public ProcessDefinition deployModel(String name, String resourcePath)
	{
		Deployment deployment = getProcessEngine().getRepositoryService()
				.createDeployment()
				.name(name)
				.addClasspathResource(resourcePath)
				.deploy();

		if (deployment != null) {
			ProcessDefinition process = getProcessEngine().getRepositoryService().
					createProcessDefinitionQuery().deploymentId(deployment.getId()).singleResult();

			logger.info(deployment.getName() + " model has been deployed with the key " + process.getKey());
			return process;
		} else {
			logger.error("Unable to deploy resource with path " + resourcePath);
			throw new TUIKProcessEngineException("Unable to deploy resource with path " + resourcePath);
		}
	}


	/**
	 * @param name
	 * @param resourceName
	 * @param inputStream
	 */
	public void deployModel(String name, String resourceName, String inputStream)
	{

		Deployment x = getProcessEngine().getRepositoryService()
				.createDeployment()
				.name(name)
				.addString(resourceName, inputStream)
				.deploy();

		if (x != null) {
			logger.debug(x.getName() + " model has been deployed");
		} else {
			logger.error("Unable to deploy resource with path " + resourceName);
		}

	}	
	
	
	/**
	 * Starts a process instance with the given process key
	 * @param processKey
	 * @return
	 */
	public ProcessInstance startProcessInstance(String processKey)
	{
		return this.startProcessInstance(processKey, null);
	}

	/**
	 * Starts a process instance with the given process key & the variables
	 * @param processKey
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessInstance(String processKey, Map<String, Object> variables) 
	{
		if (variables != null) {
			return getProcessEngine().getRuntimeService().startProcessInstanceByKey(processKey, variables);
		} else {
			return getProcessEngine().getRuntimeService().startProcessInstanceByKey(processKey);
		}
	}

	/**
	 * Starts a process instance which is listening to the given message
	 * @param message
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessInstanceByMessage(String message, Map<String, Object> variables) 
	{
		if (variables != null) {
			return getProcessEngine().getRuntimeService(). startProcessInstanceByMessage(message, variables);
		} else {
			return getProcessEngine().getRuntimeService().startProcessInstanceByMessage(message);
		}
	}

	/**
	 * Claims the specified task and completes it.
	 * @param taskId
	 * @param variables
	 * @param claim
	 * @param claimUsername
	 * @param localVariables
	 */
	public void completeTask(String taskId, Map<String, Object> variables, boolean claim, String claimUsername, boolean localVariables) 
	{
		if (claim) {
			TUIKProcessEngine.processEngine.getTaskService()
			.claim(taskId, TUIKUtils.getInstance().isEmpty(claimUsername) ? 
					TUIKUtils.getInstance().getCurrentUser() : claimUsername);
		}

		if (variables != null) {
			TUIKProcessEngine.processEngine.getTaskService().complete(taskId, variables, localVariables);
		} else {
			TUIKProcessEngine.processEngine.getTaskService().complete(taskId);
		}
	}

	/**
	 * 
	 * @param taskId
	 * @param variables
	 * @param claim
	 * @param claimUsername
	 */
	public void completeTask(String taskId, Map<String, Object> variables, boolean claim, String claimUsername) 
	{
		this.completeTask(taskId, variables, claim, claimUsername, false);
	}

	/**
	 * 
	 * @param taskId
	 * @param variables
	 * @param localVariables
	 */
	public void completeTask(String taskId, Map<String, Object> variables, boolean localVariables) 
	{
		this.completeTask(taskId, variables, true, null, localVariables);
	}

	/**
	 * 
	 * @param taskId
	 * @param variables
	 */
	public void completeTask(String taskId, Map<String, Object> variables) 
	{
		this.completeTask(taskId, variables, true, null, false);
	}

	/**
	 * 
	 * @param taskId
	 */
	public void completeTask(String taskId) 
	{
		this.completeTask(taskId, null, true, null, false);
	}


	/**
	 * @param processKey
	 * @param queryVariables
	 * @param completeVariables
	 * @param username
	 */
	public void completeTaskByProcessKey(String processKey,  Map<String, Object> queryVariables, Map<String, Object> completeVariables)
	{
		TaskQuery taskquery = processEngine.getTaskService().createTaskQuery();
		taskquery.processDefinitionKey(processKey);
		if (queryVariables != null) {
			for (String key : queryVariables.keySet()) {
				taskquery = taskquery.processVariableValueEquals(key, queryVariables.get(key));
			}
		}

		taskquery.active();

		Task task = taskquery.singleResult();

		if (task != null) {
			completeTask(task.getId(),completeVariables, true, null);
		}
	}

	/**
	 * 
	 * @param taskId
	 * @param claimUsername
	 */
	public void claim(String taskId, String claimUsername) 
	{
		TUIKProcessEngine.processEngine.getTaskService().claim(taskId, claimUsername);
	}

	/**
	 * Queries processes by process key and unfinished/finished status
	 * @param processKey
	 * @param unfinished
	 * @param finished
	 * @return 
	 */
	private List<HistoricProcessInstance> queryProcesses(String processKey, boolean unfinished, boolean finished) 
	{
		HistoryService historyService = pec.getHistoryService();
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().includeProcessVariables();
		if (!TUIKUtils.getInstance().isEmpty(processKey)) {
			query.processDefinitionKey(processKey);
		}

		if (finished) {
			query.finished();
		}

		if (unfinished) {
			query.unfinished();
		}

		List<HistoricProcessInstance> lp = query.orderByProcessInstanceStartTime().desc().list();
		return lp;
	}

	/**
	 * Queries unfinished processes by specified process key
	 * @param processKey
	 * @return
	 */
	public List<HistoricProcessInstance> queryActiveProcessesByKey(String processKey) 
	{
		return queryProcesses(processKey, true, false);
	}

	/**
	 * Queries finished processes by specified process key
	 * @param processKey
	 * @return
	 */
	public List<HistoricProcessInstance> queryFinishedProcessesByKey(String processKey) 
	{
		return queryProcesses(processKey, false, true);
	}	

	/**
	 * Queries all (finished and unfinished) processes by specified process key
	 * @param processKey
	 * @return
	 */
	public List<HistoricProcessInstance> queryAllProcessesByKey(String processKey) 
	{
		return queryProcesses(processKey, false, false);
	}	


	/**
	 * Queries all active processes in activiti repository
	 * @return
	 */

	public List<HistoricProcessInstance> queryAllActiveProcesses() 
	{
		return queryProcesses(null, true, false);
	}


	/**
	 * Queries all finished processes in activiti repository
	 * @return
	 */
	public List<HistoricProcessInstance> queryAllFinishedProcesses() 
	{
		return queryProcesses(null, false, true);
	}		


	/**
	 * Queries all processes (finished and unfinished) from activiti repository
	 * @return
	 */
	public List<HistoricProcessInstance> queryAllProcesses() 
	{
		return queryProcesses(null, false, false);
	}


	/**
	 * Queries processes by process key with the following criteria.
	 * None of the criteria are required.
	 * @param processKey - use if query for a specific process 
	 * @param unfinished - use if query unfinished processes (true/false)
	 * @param finished	 - use if query finished processes (true/false)
	 * @param username	 - use if query for a specific user
	 * @param beginStartDate  - use if processes started after a date
	 * @param endStartDate	 - use if processes started before a date
	 * @return
	 */
	private List<HistoricProcessInstance> queryProcesses(
			String processKey, 
			boolean unfinished, 
			boolean finished,
			String username,
			Date beginStartDate,
			Date endStartDate) 
			{
		HistoryService historyService = pec.getHistoryService();
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery().includeProcessVariables();

		if (!TUIKUtils.getInstance().isEmpty(processKey)) {
			query.processDefinitionKey(processKey);
		}

		if(!TUIKUtils.getInstance().isEmpty(username)){
			query.involvedUser(username);
		}

		if(!TUIKUtils.getInstance().isEmpty(beginStartDate)){
			query.startedAfter(beginStartDate);
		}

		if(!TUIKUtils.getInstance().isEmpty(endStartDate)){
			query.startedBefore(endStartDate);
		}

		if (finished) {
			query.finished();
		}

		if (unfinished) {
			query.unfinished();
		}

		List<HistoricProcessInstance> lp = query.orderByProcessInstanceStartTime().desc().list();

		return lp;
			}

	public List<HistoricProcessInstance> queryAllActiveProcessesByInvolvedUser(String username,Date beginDate,Date endDate) 
	{
		return queryProcesses(null, true, false,username,beginDate,endDate);
	}

	public List<HistoricProcessInstance> queryAllFinishedProcessesByInvolvedUser(String username,Date beginDate,Date endDate) 
	{
		return queryProcesses(null, false, true,username,beginDate,endDate);
	}		

	public List<HistoricProcessInstance> queryAllProcessesByInvolvedUser(String username,Date beginDate,Date endDate) 
	{
		return queryProcesses(null, false, false,username,beginDate,endDate);
	}


	public List<HistoricTaskInstance> queryProcessesTaskInstances(String processId)
	{
		return pec.getHistoryService().createHistoricTaskInstanceQuery()
				.processInstanceId(processId).includeProcessVariables().orderByTaskCreateTime().asc().list();
	}

	public List<HistoricIdentityLink> getTaskIdentity(String taskId)
	{
		return pec.getHistoryService().getHistoricIdentityLinksForTask(taskId);
	}

	/**
	 * Returns a liste of usernames or roles whom involved the task
	 * @param taskId
	 * @return
	 */
	public List<String> getTaskIdentityStr(String taskId)
	{
		List<String> retVal = new ArrayList<String>();
		HistoryService historyService = pec.getHistoryService();
		List<HistoricIdentityLink> ident = historyService.getHistoricIdentityLinksForTask(taskId);
		String name = null;
		for (HistoricIdentityLink i: ident) {
			if (i.getGroupId() != null) {
				name= i.getGroupId();
			} else if (i.getUserId() != null) {
				name= i.getUserId();
			}
			if (name != null) {
				retVal.add(name);
				name = null;
			}
		}
		return retVal;
	}

	/**
	 * returns task object with the given id and the variables.
	 * as far as the task id is unique, variables can be put for verification
	 * @param taskId
	 * @param variables
	 * @return
	 */
	public Task getTask(String taskId, Map<String, Object> variables)
	{
		TaskQuery taskquery = processEngine.getTaskService().createTaskQuery();

		taskquery= taskquery.taskId(taskId).includeProcessVariables().includeTaskLocalVariables();

		if (variables != null) {
			for (String key : variables.keySet()) {
				taskquery = taskquery.processVariableValueEquals(key, variables.get(key));
			}
		}

		return taskquery.singleResult();
	}

	/**
	 * Returns task object by id
	 * @param taskId
	 * @return
	 */
	public Task getTask(String taskId)
	{
		return getTask(taskId, null);
	}


	/**
	 * Returns the tasks assigned to a user
	 * @param username - name of the user
	 * @return
	 */
	public List<Task> getUserTasks(String username) 
	{
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
		taskQuery.taskAssignee(username);
		taskQuery.includeTaskLocalVariables();
		return taskQuery.list();
	}

	/**
	 * Returns the tasks which the user is candidate for 
	 * @param username - name of the user
	 * @return
	 */
	public List<Task> getUserCandidateTasks(String username) 
	{
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
		taskQuery.taskCandidateUser(username);
		taskQuery.includeTaskLocalVariables();
		return taskQuery.list();
	}

	/**
	 * Returns the tasks assigned to a group (role)
	 * @param groupName - name of the group (role)
	 * @return
	 */
	public List<Task> getGroupTasks(String groupName) 
	{
		if (groupName != null) {
			TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
			taskQuery.taskCandidateGroup(groupName);
			taskQuery.includeTaskLocalVariables();
			return taskQuery.list();
		}

		return null;
	}

	/**
	 * Returns process instance specified by key and variables 
	 * @param processKey
	 * @param variables
	 * @return
	 */
	public ProcessInstance getProcessInstance(String processKey, Map<String, Object> variables) 
	{
		List<ProcessInstance> list = getProcessInstances(processKey, variables);
		return list != null && list.size() > 0 ? list.get(0) : null;
	}

	/**
	 * Returns process instance(s) specified by key and variables 
	 * @param processKey
	 * @param variables
	 * @return
	 */
	public List<ProcessInstance> getProcessInstances(String processKey, Map<String, Object> variables) {
		if (TUIKUtils.getInstance().isEmpty(processKey)) {
			throw new TUIKProcessEngineException("Process key cannot be empty!");
		}
		try {
			ProcessInstanceQuery processQuery = getProcessEngine().getRuntimeService().createProcessInstanceQuery();
			processQuery = processQuery.processDefinitionKey(processKey).includeProcessVariables();
			for (Entry<String, Object> entry : variables.entrySet()) {
				processQuery = processQuery.variableValueEquals(entry.getKey(), entry.getValue());
			}
			return processQuery.active().list();
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TUIKProcessEngineException(e);
		}
	}

	/**
	 * Deletes process instance specified by key and variables
	 * @param processKey
	 * @param variables
	 * @param deleteReason
	 */
	public void deleteProcessInstance(String processKey, Map<String, Object> variables, String deleteReason) {
		if (TUIKUtils.getInstance().isEmpty(processKey)) {
			throw new TUIKProcessEngineException("Process key cannot be empty!");
		}
		try {
			ProcessInstance process = getProcessInstance(processKey, variables);
			if (process != null) {
				getProcessEngine().getRuntimeService().deleteProcessInstance(process.getProcessInstanceId(), deleteReason);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TUIKProcessEngineException(e);
		}
	}

	/**
	 * Deletes process instances specified by key and variables
	 * @param processKey
	 * @param variables
	 * @param deleteReason
	 */
	public void deleteProcessInstances(String processKey, Map<String, Object> variables, String deleteReason) 
	{
		if (TUIKUtils.getInstance().isEmpty(processKey)) {
			throw new TUIKProcessEngineException("Process key cannot be empty!");
		}
		try {
			List<ProcessInstance> processes = getProcessInstances(processKey, variables);
			if (processes != null) {
				for (ProcessInstance p: processes) {
					getProcessEngine().getRuntimeService().deleteProcessInstance(p.getProcessInstanceId(), deleteReason);
				}
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TUIKProcessEngineException(e);
		}
	}	

	/**
	 * Deletes process instance of a given task
	 * @param taskId
	 * @param deleteReason 
	 */
	public void deleteProcessInstanceByTaskId(String taskId,  Map<String, Object> variables, String deleteReason) 
	{
		if (TUIKUtils.getInstance().isEmpty(taskId)) {
			throw new TUIKProcessEngineException("Task ID key cannot be empty!");
		}
		try {
			Task task = getTask(taskId, variables);
			if (task != null) {
				getProcessEngine().getRuntimeService().deleteProcessInstance(task.getProcessInstanceId(), deleteReason);
			}
		} catch (Exception e) {
			logger.error(e.getMessage());
			throw new TUIKProcessEngineException(e);
		}
	}

	/**
	 * Returns true if process instance specified by key and variables exists
	 * @param processKey
	 * @param variables
	 * @return
	 */
	public boolean processExists(String processKey, Map<String, Object> variables) 
	{
		if (TUIKUtils.getInstance().isEmpty(processKey)) {
			throw new TUIKProcessEngineException("Process key cannot be empty!");
		}		
		return getProcessInstance(processKey, variables) == null ? false : true;
	}

	public Task getTaskWithVariables(String processDefinitionKey, String taskDefinitionKey, Map<String, Object> variables) 
	{
		return getTaskWithVariables(processDefinitionKey, taskDefinitionKey, variables, null, null);
	}

	/**
	 * Get the tasks with variables 
	 * @param processDefinitionKey - process definition key (id)
	 * @param taskDefinitionKey - task definition key (id)
	 * @param variables	- variables equals to (name & value pair - optional)
	 * @param candidateUser - task which the user is candidate for (optional)
	 * @param assignee - task which the user is assigned (optional)
	 * @return
	 */
	public Task getTaskWithVariables(String processDefinitionKey, String taskDefinitionKey, Map<String, Object> variables, String candidateUser, String assignee) 
	{
		try{
			TaskQuery taskquery = processEngine.getTaskService().createTaskQuery();
			taskquery = taskquery.processDefinitionKey(processDefinitionKey);
			taskquery = taskquery.taskDefinitionKey(taskDefinitionKey);

			for (String key : variables.keySet()) {
				taskquery = taskquery.processVariableValueEquals(key, variables.get(key));
			}
			if (candidateUser != null) {
				taskquery= taskquery.taskCandidateUser(candidateUser);
			}
			if (assignee != null) {
				taskquery= taskquery.taskAssignee(assignee);
			}
			if(taskquery.list().size() == 1){
				return taskquery.active().list().get(0);
			}
			return null;
		} catch (Exception e) {
			throw new TUIKProcessEngineException("Unexpected error");
		}
	}

	/**
	 * Returns true if process has been finished or terminated.
	 * @param processId
	 * @return
	 */
	public boolean isProcessFinished(String processId)
	{
		List<ProcessInstance> processList = processEngine.getRuntimeService()
				.createProcessInstanceQuery()
				.processInstanceId(processId)
				.active()
				.list();

		return processList == null || processList.size() == 0 ? true : false;
	}


	/**
	 * Returns process even if it is active or finished.
	 * @param processId
	 * @return
	 */
	public HistoricProcessInstance getHistoricProcessInstance(String processId)
	{
		List<HistoricProcessInstance> processes = processEngine.getHistoryService()
				.createHistoricProcessInstanceQuery()
				.processInstanceId(processId)
				.includeProcessVariables()
				.list();

		if(processes.size() <= 0) {
			logger.error("No Historic Process Exists with id : " + processId);
			throw new TUIKProcessEngineException("No Historic Process Exists with id : " + processId);
		}

		return processes.get(0);
	}


	/**
	 * Returns the png image of the process diagram with the given process key
	 * @param processDefinitionKey
	 * @param out
	 */
	public void getProcessDiagram(String processDefinitionKey, OutputStream out)
	{
		ProcessDefinition definition = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();

		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(definition.getId());
		try {
			BufferedImage diagramImage = ImageIO.read(diagramInputStream);
			ImageIO.write(diagramImage, "PNG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

	/**
	 * Returns the png image of the process diagram with the given process key and draws the active tasks
	 * in the process with the given process id.
	 * @param processDefinitionKey
	 * @param processId
	 * @param out
	 */
	public void getProcessDiagramForInstance(String processDefinitionKey, String processId, OutputStream out)
	{
		ProcessDefinition definition = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();

		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(definition.getId());

		DiagramLayout diagramLayout = processEngine.getRepositoryService().getProcessDiagramLayout(definition.getId());

		try {
			BufferedImage diagramImage = ImageIO.read(diagramInputStream);
			Graphics2D graphics = (Graphics2D) diagramImage.getGraphics();

			List<Execution> executions = processEngine.getRuntimeService().createExecutionQuery().processInstanceId(processId).list();
			graphics.setColor(Color.red);
			graphics.setStroke(new BasicStroke(3));
			for (Execution execution : executions) {
				DiagramNode node = diagramLayout.getNode(execution.getActivityId());
				if (node != null) {
					graphics.drawRoundRect(node.getX().intValue(), node.getY().intValue(), node.getWidth().intValue(), node.getHeight().intValue(), 5, 5);
				}
			}

			ImageIO.write(diagramImage, "PNG", out);
		} catch (IOException e) {
			e.printStackTrace();
		}		

	}

}
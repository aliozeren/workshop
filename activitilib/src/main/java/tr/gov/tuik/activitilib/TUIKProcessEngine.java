package tr.gov.tuik.activitilib;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import javax.imageio.ImageIO;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.GraphicInfo;
import org.activiti.engine.HistoryService;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngineConfiguration;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.history.HistoricIdentityLink;
import org.activiti.engine.history.HistoricProcessInstance;
import org.activiti.engine.history.HistoricProcessInstanceQuery;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.impl.identity.Authentication;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.runtime.ProcessInstanceQuery;
import org.activiti.engine.task.Comment;
import org.activiti.engine.task.Event;
import org.activiti.engine.task.IdentityLink;
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
	 * Starts a process instance with the given process definition Id & the variables
	 * @param processDefinitionId
	 * @param variables
	 * @return
	 */
	public ProcessInstance startProcessInstanceById(String processDefinitionId, Map<String, Object> variables) 
	{
		if (variables != null) {
			return getProcessEngine().getRuntimeService().startProcessInstanceById(processDefinitionId, variables);
		} else {
			return getProcessEngine().getRuntimeService().startProcessInstanceById(processDefinitionId);
		}
	}

	/**
	 * Starts a process instance with the given process definition Id
	 * @param processDefinitionId
	 * @return
	 */
	public ProcessInstance startProcessInstanceById(String processDefinitionId) 
	{
		return this.startProcessInstanceById(processDefinitionId, null);
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

	/**
	 * Returns the identity links for a task, users, groups etc with identity link type
	 * @param taskId
	 * @return
	 */
	public List<IdentityLink> getTaskIdentityLinks(String taskId) 
	{
		return processEngine.getTaskService().getIdentityLinksForTask(taskId);
	}
	
	public List<HistoricIdentityLink> getTaskHistoricIdentityLinks(String taskId)
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
	 * Returns the tasks which the user is related for 
	 * @param username - name of the user
	 * @return
	 */
	public List<Task> getUserRelatedTasks(String username) 
	{
		TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
		taskQuery.taskInvolvedUser(username);
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
	 * Returns the tasks assigned to a group (role)
	 * @param groupName - name of the group (role)
	 * @return
	 */
	public List<Task> getGroupTasks(List<String> groups) 
	{
		if (groups != null && groups.size() > 0) {
			TaskQuery taskQuery = processEngine.getTaskService().createTaskQuery();
			taskQuery.taskCandidateGroupIn(groups);
			taskQuery.includeTaskLocalVariables();
			return taskQuery.list();
		}

		return null;
	}


	private List<Task> addToList(List<Task> mainList, List<Task> listToAdd) 
	{
		if (mainList == null || mainList.size() == 0 ) {
			return listToAdd;
		} else if (listToAdd != null && listToAdd.size() > 0){
			mainList.addAll(listToAdd);
		}
		return mainList;
	}	

	/**
	 * @param username
	 * @param groups
	 * @return
	 */
	public List<Task> getTasksInvolved(String username, List<String> groups) 
	{

		List<Task> tlist = null;

		tlist = this.addToList(tlist, getUserTasks(username));
		tlist = this.addToList(tlist, getUserCandidateTasks(username));
		tlist = this.addToList(tlist, getGroupTasks(groups));

		if (tlist != null) {
			Collections.sort(tlist, new Comparator<Task>() {

				public int compare(Task f1, Task f2) {
					return f1.getCreateTime().compareTo(f2.getCreateTime());
				}
			});		
		}

		return tlist; 
	}

	/**
	 * @param username
	 * @param groups
	 * @return
	 */
	public List<String> getTaskIdsInvolved(String username, List<String> groups) 
	{

		List<String> result = null;

		List<Task> tlist = null;

		tlist = this.addToList(tlist, getUserTasks(username));
		tlist = this.addToList(tlist, getUserCandidateTasks(username));
		tlist = this.addToList(tlist, getGroupTasks(groups));

		if (tlist != null) {
			Collections.sort(tlist, new Comparator<Task>() {

				public int compare(Task f1, Task f2) {
					return f1.getCreateTime().compareTo(f2.getCreateTime());
				}
			});	
			result= new ArrayList<String>();
			for (Task t : tlist) {
				result.add(t.getId());
			}
		}

		return result; 
	}	


	/**
	 * involves the user to the task with the given identity
	 * 
	 * @param taskId
	 * @param username
	 * @param identityLinkType
	 * Activiti native identity types are;
	 *		public static final String ASSIGNEE = "assignee"
	 *		public static final String CANDIDATE = "candidate";
	 *		public static final String OWNER = "owner";
	 *		public static final String STARTER = "starter";
	 *		public static final String PARTICIPANT = "participant";
	 */
	public void involveUser(String taskId, String username, String identityLinkType) 
	{
		processEngine.getTaskService().addUserIdentityLink(taskId, username, identityLinkType);
	}
	
	/**
	 * involves the group to the task with the given identity
	 * 
	 * @param taskId
	 * @param username
	 * @param identityLinkType
	 * Activiti native identity types are;
	 *		public static final String ASSIGNEE = "assignee"
	 *		public static final String CANDIDATE = "candidate";
	 *		public static final String OWNER = "owner";
	 *		public static final String STARTER = "starter";
	 *		public static final String PARTICIPANT = "participant";
	 */	
	public void involveGroup(String taskId, String group, String identityLinkType) 
	{
		processEngine.getTaskService().addGroupIdentityLink(taskId, group, identityLinkType);
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
			throw new TUIKProcessEngineException("e");
		}		
	}

	/**
	 * @param processDefinitionKey
	 * @param out
	 */
	public void getProcessDiagramById(String processDefinitionId, OutputStream out)
	{
		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
		try {
			BufferedImage diagramImage = ImageIO.read(diagramInputStream);
			ImageIO.write(diagramImage, "PNG", out);
		} catch (IOException e) {
			e.printStackTrace();
			throw new TUIKProcessEngineException("e");
		}		
	}

	/**
	 * Returns the png image of the process diagram with the given process key
	 * @param processDefinitionKey
	 * @param out
	 */
	public InputStream getProcessDiagram(String processDefinitionKey)
	{
		ProcessDefinition definition = processEngine.getRepositoryService()
				.createProcessDefinitionQuery()
				.processDefinitionKey(processDefinitionKey)
				.latestVersion()
				.singleResult();

		return processEngine.getRepositoryService().getProcessDiagram(definition.getId());
	}

	/**
	 * Returns the png image of the process diagram with the given process definition id
	 * @param processDefinitionId
	 * @param out
	 */
	public InputStream getProcessDiagramById(String processDefinitionId)
	{
		return processEngine.getRepositoryService().getProcessDiagram(processDefinitionId);
	}	

	/**
	 * writes PNG image of the process diagram with the given process id in the output stream and draws the active tasks
	 * @param processId
	 * @param out
	 * @param ownedColor - the color of the current user tasks
	 * @param ownedTaskIdList - the task id list of the user tasks
	 * @param onlyOwnedTasks - only highlight user tasks if true
	 */
	public void getProcessDiagramForInstance(String processId, OutputStream out, Color ownedColor, List<String> ownedTaskIdList, boolean onlyOwnedTasks)
	{
		ProcessInstance process = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId).singleResult();
		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(process.getProcessDefinitionId());
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel(process.getProcessDefinitionId());
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();

		try {

			BufferedImage diagramImage = ImageIO.read(diagramInputStream);

			Graphics2D graphics = (Graphics2D) diagramImage.getGraphics();
			graphics.setStroke(new BasicStroke(3));

			boolean highlightTask;

			for (Task task : tasks) {

				if (ownedTaskIdList != null && ownedTaskIdList.contains(task.getId())) {
					highlightTask = true;
					graphics.setColor(ownedColor == null ? Color.blue : ownedColor);
				}else if (!onlyOwnedTasks) {
					highlightTask = true;
					graphics.setColor(Color.red);
				} else {
					highlightTask = false;
				}

				if (highlightTask) {
					GraphicInfo x = model.getLocationMap().get(task.getTaskDefinitionKey());
					if (x != null) {
						graphics.drawRoundRect((int)x.getX(), (int)x.getY(), (int)x.getWidth(), (int)x.getHeight(), 5, 5);
					}
				}
			}

			ImageIO.write(diagramImage, "PNG", out);

		} catch (IOException e) {
			e.printStackTrace();
			throw new TUIKProcessEngineException(e);

		}	
	}

	/**
	 * Returns the imagemap of the active tasks and writes the PNG image of the process diagram with the given process id in the output stream and draws the active tasks
	 * @param processId
	 * @param out
	 * @param ownedColor - the color of the current user tasks
	 * @param ownedTaskIdList - the task id list of the user tasks
	 * @param onlyOwnedTasks - only highlight user tasks if true
	 * @return  HashMap 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */
	public Map<String, String> getProcessDiagramForInstanceWithMap(String processId, OutputStream out, Color ownedColor, List<String> ownedTaskIdList, boolean onlyOwnedTasks)
	{

		Map<String, String> imagemap = new HashMap<String, String>();

		ProcessInstance process = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId).singleResult();
		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(process.getProcessDefinitionId());
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel(process.getProcessDefinitionId());
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();

		try {

			BufferedImage diagramImage = ImageIO.read(diagramInputStream);

			Graphics2D graphics = (Graphics2D) diagramImage.getGraphics();
			graphics.setStroke(new BasicStroke(3));

			boolean highlightTask;

			for (Task task : tasks) {

				if (ownedTaskIdList != null && ownedTaskIdList.contains(task.getId())) {
					highlightTask = true;
					graphics.setColor(ownedColor == null ? Color.blue : ownedColor);
				}else if (!onlyOwnedTasks) {
					highlightTask = true;
					graphics.setColor(Color.red);
				} else {
					highlightTask = false;
				}

				if (highlightTask) {
					GraphicInfo x = model.getLocationMap().get(task.getTaskDefinitionKey());
					if (x != null) {
						graphics.drawRoundRect((int)x.getX(), (int)x.getY(), (int)x.getWidth(), (int)x.getHeight(), 5, 5);
						imagemap.put(task.getId(), (int)x.getX() + "," + (int)x.getY() + "," + (int)(x.getX() + x.getWidth()) + "," + (int)(x.getY() + x.getHeight()));

					}
				}
			}

			ImageIO.write(diagramImage, "PNG", out);

			return imagemap;

		} catch (IOException e) {
			e.printStackTrace();
			throw new TUIKProcessEngineException(e);

		}	
	}


	/**
	 * Returns the imagemap of the active tasks of the user and writes the PNG image of the process diagram with the given process id in the output stream
	 * @param processId
	 * @param out
	 * @return  HashMap 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */
	public Map<String, String> getProcessDiagramForInstanceWithMapAll(String processId, OutputStream out, String username, List<String> groups)
	{

		List<String> taskIds = this.getTaskIdsInvolved(username, groups);

		return this.getProcessDiagramForInstanceWithMap(processId, out, null, taskIds, false);

	}

	/**
	 * Returns the imagemap of the active tasks and writes the PNG image of the process diagram with the given process id in the output stream and highlights the active tasks
	 * of the given user and its assigned groups
	 * @param processId
	 * @param out
	 * @return  HashMap 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */
	public Map<String, String> getProcessDiagramForInstanceWithMapInvolved(String processId, OutputStream out, String username, List<String> groups)
	{

		List<String> taskIds = this.getTaskIdsInvolved(username, groups);

		return this.getProcessDiagramForInstanceWithMap(processId, out, null, taskIds, true);

	}	

	/**
	 * Returns the image of the process diagram with the given process id and draws the active tasks
	 * @param processId
	 * @param color
	 * @param ownedColor - the color of the current user tasks
	 * @param ownedTaskIdList - the task id list of the user tasks
	 * @param onlyOwnedTasks - only highlight user tasks if true 
	 * @return BufferedImage 
	 */
	public BufferedImage getProcessDiagramForInstance(String processId, Color ownedColor, List<String> ownedTaskIdList, boolean onlyOwnedTasks)
	{

		ProcessInstance process = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId).singleResult();
		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(process.getProcessDefinitionId());
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel(process.getProcessDefinitionId());
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();

		try {
			BufferedImage diagramImage = ImageIO.read(diagramInputStream);

			Graphics2D graphics = (Graphics2D) diagramImage.getGraphics();
			graphics.setStroke(new BasicStroke(3));

			boolean highlightTask;

			for (Task task : tasks) {
				if (ownedTaskIdList != null && ownedTaskIdList.contains(task.getId())) {
					highlightTask = true;
					graphics.setColor(ownedColor == null ? Color.blue : ownedColor);
				}else if (!onlyOwnedTasks) {
					highlightTask = true;
					graphics.setColor(Color.red);
				} else {
					highlightTask = false;
				}

				if (highlightTask) {
					GraphicInfo x = model.getLocationMap().get(task.getTaskDefinitionKey());
					if (x != null) {
						graphics.drawRoundRect((int)x.getX(), (int)x.getY(), (int)x.getWidth(), (int)x.getHeight(), 5, 5);
					}
				}
			}

			return diagramImage;

		} catch (IOException e) {
			e.printStackTrace();
			throw new TUIKProcessEngineException(e);
		}	
	}	

	/**
	 * Returns the image and the image map (active tasks) of the process diagram with the given process id and draws the active tasks
	 * @param processId
	 * @param color
	 * @param ownedColor - the color of the current user tasks
	 * @param ownedTaskIdList - the task id list of the user tasks
	 * @param onlyOwnedTasks - only highlight user tasks if true 
	 * @return HashMap
	 * 	Keys: "image" [class : BufferedImage] is the diagram of the process
	 * 		  "imagemap" [class : HashMap] ; 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */
	public Map<String, Object> getProcessDiagramForInstanceWithMap(String processId, Color ownedColor, List<String> ownedTaskIdList, boolean onlyOwnedTasks)
	{

		Map<String, Object> result = new HashMap<String, Object>(); 
		Map<Task, String> imagemap = new HashMap<Task, String>();

		ProcessInstance process = processEngine.getRuntimeService().createProcessInstanceQuery().processInstanceId(processId).singleResult();
		InputStream diagramInputStream = processEngine.getRepositoryService().getProcessDiagram(process.getProcessDefinitionId());
		BpmnModel model = processEngine.getRepositoryService().getBpmnModel(process.getProcessDefinitionId());
		List<Task> tasks = processEngine.getTaskService().createTaskQuery().processInstanceId(process.getProcessInstanceId()).list();

		try {
			BufferedImage diagramImage = ImageIO.read(diagramInputStream);

			Graphics2D graphics = (Graphics2D) diagramImage.getGraphics();
			graphics.setStroke(new BasicStroke(3));

			boolean highlightTask;

			for (Task task : tasks) {
				if (ownedTaskIdList != null && ownedTaskIdList.contains(task.getId())) {
					highlightTask = true;
					graphics.setColor(ownedColor == null ? Color.blue : ownedColor);
				}else if (!onlyOwnedTasks) {
					highlightTask = true;
					graphics.setColor(Color.red);
				} else {
					highlightTask = false;
				}

				if (highlightTask) {
					GraphicInfo x = model.getLocationMap().get(task.getTaskDefinitionKey());
					if (x != null) {
						graphics.drawRoundRect((int)x.getX(), (int)x.getY(), (int)x.getWidth(), (int)x.getHeight(), 5, 5);
						imagemap.put(task, (int)x.getX() + "," + (int)x.getY() + "," + (int)(x.getX() + x.getWidth()) + "," + (int)(x.getY() + x.getHeight()));
					}
				}
			}

			result.put("image", diagramImage);
			result.put("imagemap", imagemap);

			return result;

		} catch (IOException e) {
			e.printStackTrace();
			throw new TUIKProcessEngineException(e);
		}	
	}	

	/**
	 * Returns the image and the image map (active tasks) of the process diagram with the given process id and highlights the active tasks
	 * of the user and its assigned groups
	 * @param processId
	 * @param username
	 * @param groups
	 * @return HashMap
	 * 	Keys: "image" [class : BufferedImage] is the diagram of the process
	 * 		  "imagemap" [class : HashMap] ; 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */
	public Map<String, Object> getProcessDiagramForInstanceWithMapAll(String processId, String username, List<String> groups)
	{
		List<String> taskIds = this.getTaskIdsInvolved(username, groups);

		return this.getProcessDiagramForInstanceWithMap(processId, null, taskIds, false);
	}		

	/**
	 * Returns the image and the image map (active tasks of the given user and its assigned groups) of the process diagram with the given process id 
	 * @param processId
	 * @param username
	 * @param groups
	 * @return HashMap
	 * 	Keys: "image" [class : BufferedImage] is the diagram of the process
	 * 		  "imagemap" [class : HashMap] ; 
	 * 				key  : [String] is the task Id of the active task
	 * 				value: [String] is the imagemap coordinates of the task
	 */

	public Map<String, Object> getProcessDiagramForInstanceWithMapInvolved(String processId, String username, List<String> groups)
	{
		List<String> taskIds = this.getTaskIdsInvolved(username, groups);

		return this.getProcessDiagramForInstanceWithMap(processId, null, taskIds, true);
	}		

	/**
	 * Return active process by instances
	 * 
	 * @param processInstanceIds
	 * @return
	 *  
	 */
	public List<HistoricProcessInstance> getActiveProcessByIds(Set<String> processInstanceIds) 
	{	
		if (processInstanceIds.isEmpty()) {
			throw new TUIKProcessEngineException("Process Instance Ids is empty");
		}

		HistoryService historyService = pec.getHistoryService();
		HistoricProcessInstanceQuery query = historyService.createHistoricProcessInstanceQuery()
				.includeProcessVariables();
		if (processInstanceIds != null) {
			query.processInstanceIds(processInstanceIds);
		}

		query.unfinished();

		List<HistoricProcessInstance> lp = query.orderByProcessInstanceStartTime().desc().list();
		return lp;
	}
	
	
	public synchronized void addCommentToTask(String userId, String taskId, String comment, String commentType) 
	{
		Authentication.setAuthenticatedUserId(userId);
		processEngine.getTaskService().addComment(taskId, null,commentType, comment);
		Authentication.setAuthenticatedUserId(null);
	}

	public synchronized void addCommentToProcessInstance(String userId, String processInstanceId, String comment, String commentType) 
	{
		Authentication.setAuthenticatedUserId(userId);
		processEngine.getTaskService().addComment(null, processInstanceId,commentType, comment);
		Authentication.setAuthenticatedUserId(null);
	}
	
	public Comment getComment(String commentId) 
	{
		return processEngine.getTaskService().getComment(commentId);
	}
	
	/**
	 * Returns comments on a task with the given type. If type is given null only "comment" type of comments are returned
	 * @param taskId
	 * @param commentType
	 * 		native comment types;
	 * 		CommentEntity.TYPE_EVENT = "event";
	 * 		CommentEntity.TYPE_COMMENT = "comment";
	 * @return
	 */
	public List<Comment> getTaskComments(String taskId, String commentType) 
	{
		if (commentType == null) {
			return processEngine.getTaskService().getTaskComments(taskId);
		} else {
			return processEngine.getTaskService().getTaskComments(taskId, commentType);
		}
	}

	/**
	 * Returns comments on a process instance with the given type. If type is given null only "comment" type of comments are returned
	 * @param taskId
	 * @param commentType
	 * 		native comment types;
	 * 		CommentEntity.TYPE_EVENT = "event";
	 * 		CommentEntity.TYPE_COMMENT = "comment";
	 * @return
	 */
	public List<Comment> getProcessInstanceComments(String processInstanceId, String commentType) 
	{
		if (commentType == null) {
			return processEngine.getTaskService().getProcessInstanceComments(processInstanceId);
		} else {
			return processEngine.getTaskService().getProcessInstanceComments(processInstanceId, commentType);
		}
	}
	
	/**
	 * Returns events on a task.
	 * Event Types: 
	 * 		Event.ACTION_ADD_ATTACHMENT	"AddAttachment"
	 * 		Event.ACTION_ADD_COMMENT	"AddComment"
	 * 		Event.ACTION_ADD_GROUP_LINK	"AddGroupLink"
	 * 		Event.ACTION_ADD_USER_LINK	"AddUserLink"
	 * 		Event.ACTION_DELETE_ATTACHMENT	"DeleteAttachment"
	 * 		Event.ACTION_DELETE_GROUP_LINK	"DeleteGroupLink"
	 * 		Event.ACTION_DELETE_USER_LINK	"DeleteUserLink"
	 * @param taskId
	 * @return
	 */
	public List<Event> getTaskEvents(String taskId) 
	{
		return processEngine.getTaskService().getTaskEvents(taskId);
	}	
	
	
}

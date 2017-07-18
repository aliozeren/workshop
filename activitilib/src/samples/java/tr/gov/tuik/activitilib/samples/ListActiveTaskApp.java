package tr.gov.tuik.activitilib.samples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.TaskService;
import org.activiti.engine.impl.cfg.ProcessEngineConfigurationImpl;
import org.activiti.engine.impl.cmd.SignalCmd;
import org.activiti.engine.impl.interceptor.CommandExecutor;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;
import tr.gov.tuik.activitilib.utils.TUIKSetActivitiIdCmd;

public class ListActiveTaskApp 
{

	private final static Logger logger = Logger.getLogger(ListActiveTaskApp.class);

	public static void main( String[] args )
	{

		TaskService taskService = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService();
		
		List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("agem");
		
		for (Task t : tasks) {
			logger.info("Task Owner:" + t.getAssignee() + ", Task ID:" + t.getId() + ", Task Name:" + t.getName()+ ", Execution Id Name:" + t.getExecutionId());
	    	Map<String, Object> vars = new HashMap<String, Object>();
	    	vars.put("goBack", "0");
    		taskService.complete(t.getId(),vars);
		}
		

		TUIKProcessEngine.destroy();
	}

}

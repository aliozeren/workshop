package tr.gov.tuik.activitilib.samples;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.activiti.engine.task.TaskQuery;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class ListActiveTaskApp 
{

	private final static Logger logger = Logger.getLogger(ListActiveTaskApp.class);

	public static void main( String[] args )
	{


		TaskQuery taskquery = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService().createTaskQuery();

		TaskService taskService = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService();
		List<Task> tasks = taskquery.list();
		
		for (Task t : tasks) {
			logger.info("Task Owner:" + t.getAssignee() + ", Task ID:" + t.getId() + ", Task Name:" + t.getName());
			taskService.setAssignee(t.getId(), "kermit");
			taskService.complete(t.getId());
		}


		TUIKProcessEngine.destroy();
	}

}

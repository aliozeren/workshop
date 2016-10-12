package tr.gov.tuik.activitilib;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

public class ListTaskApp 
{

	private final static Logger logger = Logger.getLogger(ListTaskApp.class);

    public static void main( String[] args )
    {
    	List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("gonzo");

    	TaskService taskService = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService();
    	for (Task t : tasks) {
    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
    	}
    	
    	TUIKProcessEngine.destroy();
    }

}

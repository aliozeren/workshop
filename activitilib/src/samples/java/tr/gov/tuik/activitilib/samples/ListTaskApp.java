package tr.gov.tuik.activitilib.samples;

import java.util.List;

import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class ListTaskApp 
{

	private final static Logger logger = Logger.getLogger(ListTaskApp.class);

    public static void main( String[] args )
    {
    	List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("ali");

    	for (Task t : tasks) {
    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
    		TUIKProcessEngine.getInstance().completeTask(t.getId(),null,false,null);
    	}
    	
    	TUIKProcessEngine.destroy();
    }

}

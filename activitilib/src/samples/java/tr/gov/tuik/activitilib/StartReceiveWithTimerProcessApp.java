package tr.gov.tuik.activitilib;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.TaskService;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

public class StartReceiveWithTimerProcessApp 
{
	private final static Logger logger = Logger.getLogger(StartReceiveWithTimerProcessApp.class);

    public static void main( String[] args )
    {
    	ProcessEngine engine = TUIKProcessEngine.getInstance().getProcessEngine();
    	
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("responsible", "mehmet");
    	TUIKProcessEngine.getInstance().startProcessInstance("test", vars);
    	
    	List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("mehmet");

    	TaskService taskService = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService();
    	for (Task t : tasks) {
    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
    		taskService.complete(t.getId());
    	}
    	
//    	List<Execution> processes = engine.getRuntimeService().createExecutionQuery().orderByProcessInstanceId().desc().list();
//    	for (Execution p : processes) {
//    		System.out.println(p.getId());
//    		engine.getRuntimeService().signal(p.getId());
//    	}
    	
    	
    	TUIKProcessEngine.destroy();

    }
}

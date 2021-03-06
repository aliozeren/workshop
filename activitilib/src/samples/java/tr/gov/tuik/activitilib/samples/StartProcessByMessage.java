package tr.gov.tuik.activitilib.samples;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class StartProcessByMessage 
{
	private final static Logger logger = Logger.getLogger(StartProcessByMessage.class);

    public static void main( String[] args )
    {
    	ProcessEngine engine = TUIKProcessEngine.getInstance().getProcessEngine();
    	
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("degisken", "evet");
    	TUIKProcessEngine.getInstance().startProcessInstanceByMessage("evetTaskMessage", vars);
    	
//    	List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("mehmet");
//
//    	TaskService taskService = TUIKProcessEngine.getInstance().getProcessEngine().getTaskService();
//    	for (Task t : tasks) {
//    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
//    		taskService.complete(t.getId());
//    	}
    	
//    	List<Execution> processes = engine.getRuntimeService().createExecutionQuery().orderByProcessInstanceId().desc().list();
//    	for (Execution p : processes) {
//    		System.out.println(p.getId());
//    		engine.getRuntimeService().signal(p.getId());
//    	}
    	
    	
    	TUIKProcessEngine.destroy();

    }
}

package tr.gov.tuik.activitilib.samples;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.task.Task;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class TestTimerApp 
{
	private final static Logger logger = Logger.getLogger(TestTimerApp.class);

	public static void main( String[] args ) throws InterruptedException
	{
		
    	ProcessEngine engine = TUIKProcessEngine.getInstance().getProcessEngine();
    	
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("timerP", "PT1M");
    	vars.put("taskOwner", "kermit");
    	
    	TUIKProcessEngine.getInstance().startProcessInstance("testTimer", vars);
    	
    	logger.info("Number of process instances: " + engine.getRuntimeService().createProcessInstanceQuery().count());
    	
    	Thread.sleep(30000L);

    	List<Task> tasks = TUIKProcessEngine.getInstance().getUserTasks("kermit");

    	for (Task t : tasks) {
    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
    	}

    	logger.info("First task query...");
    	
    	Thread.sleep(50000L);
    	
    	logger.info("Second task query...");

    	tasks = TUIKProcessEngine.getInstance().getUserTasks("kermit");

    	for (Task t : tasks) {
    		logger.info("Task ID:" + t.getId() + ", Task Name:" + t.getName());
    	}
    	
    	TUIKProcessEngine.destroy();
	}
}

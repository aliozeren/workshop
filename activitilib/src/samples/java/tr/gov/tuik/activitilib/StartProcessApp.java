package tr.gov.tuik.activitilib;

import java.util.List;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

public class StartProcessApp 
{
	private final static Logger logger = Logger.getLogger(StartProcessApp.class);

    public static void main( String[] args )
    {
    	ProcessEngine engine = TUIKProcessEngine.getInstance().getProcessEngine();
    	
//    	Map<String, Object> vars = new HashMap<String, Object>();
//    	vars.put("responsible", "mehmet");
//    	TUIKProcessEngine.getInstance().startProcessInstance("eventTestProcess", vars);
    	
//    	TUIKProcessEngine.getInstance().startProcessInstance("testParallelExecution");

    	logger.info("Number of process instances: " + engine.getRuntimeService().createProcessInstanceQuery().count());
    	
    	List<Execution> processes = engine.getRuntimeService().createExecutionQuery().orderByProcessInstanceId().desc().list();
    	
    	for (Execution p : processes) {
    		System.out.println(p.getId());
    		engine.getRuntimeService().signal(p.getId());
    	}
    	TUIKProcessEngine.destroy();
    }
}

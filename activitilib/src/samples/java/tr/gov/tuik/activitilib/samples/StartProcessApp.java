package tr.gov.tuik.activitilib.samples;

import java.util.HashMap;
import java.util.Map;

import org.activiti.engine.runtime.ProcessInstance;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class StartProcessApp 
{
	private final static Logger logger = Logger.getLogger(StartProcessApp.class);

    public static void main( String[] args )
    {
 //   	ProcessEngine engine = TUIKProcessEngine.getInstance().getProcessEngine();
    	
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("var1", "mustafa");
    	vars.put("var2", "kemal");
    	ProcessInstance x = TUIKProcessEngine.getInstance().startProcessInstance("testRedirect", vars);
    	
    	System.out.println(x.getProcessInstanceId());
    	
//    	logger.info("Number of process instances: " + engine.getRuntimeService().createProcessInstanceQuery().count());
//    	
//    	List<Execution> processes = engine.getRuntimeService().createExecutionQuery().orderByProcessInstanceId().desc().list();
//    	
//    	for (Execution p : processes) {
//    		System.out.println(p.getId());
//    		engine.getRuntimeService().signal(p.getId());
//    	}
    	TUIKProcessEngine.destroy();
    }
}

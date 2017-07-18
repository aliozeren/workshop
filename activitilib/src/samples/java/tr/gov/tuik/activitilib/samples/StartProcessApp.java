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
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("goBack", "1");
    	ProcessInstance x = TUIKProcessEngine.getInstance().startProcessInstance("testRedirect_2", vars);
    	
    	System.out.println(x.getProcessInstanceId());
    	
    	TUIKProcessEngine.destroy();
    }
}

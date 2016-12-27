package tr.gov.tuik.activitilib.samples;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class StartReceiveWithTimerProcessApp 
{
	private final static Logger logger = Logger.getLogger(StartReceiveWithTimerProcessApp.class);

    public static void main( String[] args )
    {
    	Map<String, Object> vars = new HashMap<String, Object>();
    	vars.put("timerP", "T1M");
    	TUIKProcessEngine.getInstance().startProcessInstance("testTimer", vars);
    	
    	TUIKProcessEngine.destroy();

    }
}

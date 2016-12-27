package tr.gov.tuik.activitilib.samples;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.TUIKFormService;
import tr.gov.tuik.activitilib.TUIKProcessEngine;

public class GetStartFormDataAppRendered 
{

	private final static Logger logger = Logger.getLogger(GetStartFormDataAppRendered.class);

    public static void main( String[] args )
    {
    	Object startForm = TUIKFormService.getInstance().getRenderedTaskForm("227506");

    	logger.info(startForm);
    	
    	TUIKProcessEngine.destroy();
    }
    
}

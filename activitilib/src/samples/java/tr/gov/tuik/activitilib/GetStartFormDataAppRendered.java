package tr.gov.tuik.activitilib;

import org.apache.log4j.Logger;

public class GetStartFormDataAppRendered 
{

	private final static Logger logger = Logger.getLogger(GetStartFormDataAppRendered.class);

    public static void main( String[] args )
    {
    	Object startForm = TUIKFormService.getInstance().getRenderedStartForm("formPropTest");

    	logger.info(startForm);
    	
    	TUIKProcessEngine.destroy();
    }
    
}

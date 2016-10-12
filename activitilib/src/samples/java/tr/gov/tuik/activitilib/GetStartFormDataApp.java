package tr.gov.tuik.activitilib;

import java.io.IOException;

import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.zk.DynamicModel;

public class GetStartFormDataApp 
{

	private final static Logger logger = Logger.getLogger(GetStartFormDataApp.class);

    public static void main( String[] args ) throws IOException
    {

		for (Object property : TUIKFormService.getInstance().getRenderedStartForm("formPropTest")) {
			DynamicModel dm = (DynamicModel) property;
			logger.info("Populating form field " + dm.getLabel().getId() );
			logger.info("Variable : " + dm.getComponent().getId());
			logger.info(dm);
		}
    	
    	TUIKProcessEngine.destroy();
    }

}

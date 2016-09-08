package tr.gov.tuik.activitilib;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.apache.log4j.Logger;

import tr.gov.tuik.activitilib.zk.TUIKTestFormType;

public class GetStartFormDataApp 
{

	private final static Logger logger = Logger.getLogger(GetStartFormDataApp.class);

    public static void main( String[] args )
    {
    	StartFormData startForm = TUIKFormService.getInstance().getStartForm("formPropTest");

		for (FormProperty property : startForm.getFormProperties()) {
			logger.info("====================================================");
			logger.info("Property Type : " + property.getType());
			logger.info("Class Type : " + property.getClass());
			if (property.getType() instanceof TUIKTestFormType) {
				TUIKTestFormType t = (TUIKTestFormType) property.getType();
				logger.info(" --> Width " + t.getWidth());
				logger.info(" --> Height " + t.getHeight());
			}
			String propertyId = property.getId();
			String propertyValue = property.getValue();
			logger.info("Populating form field " + propertyId + " with value " + propertyValue);
		}
    	
    	TUIKProcessEngine.destroy();
    }

}

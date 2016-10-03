package tr.gov.tuik.activitilib;

import org.activiti.engine.form.FormProperty;
import org.activiti.engine.form.StartFormData;
import org.apache.log4j.Logger;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.impl.XulElement;

import tr.gov.tuik.activitilib.types.ActivitiFormTypeInterface;

public class GetStartFormDataApp 
{

	private final static Logger logger = Logger.getLogger(GetStartFormDataApp.class);

    public static void main( String[] args )
    {
    	StartFormData startForm = TUIKFormService.getInstance().getStartForm("formPropTest");

		for (FormProperty property : startForm.getFormProperties()) {
			logger.info("====================================================");
			String propertyId = property.getId();
			String propertyValue = property.getValue();
			logger.info("Populating form field " + propertyId + " with value " + propertyValue);
//			logger.info("Property Type : " + property.getType());
//			logger.info("Class Type : " + property.getClass());
			if (property.getType() instanceof ActivitiFormTypeInterface) {
				ActivitiFormTypeInterface prop= (ActivitiFormTypeInterface) property.getType();
				logger.info("Variable : " + prop.getVariable());
				Object ele= prop.renderInput(property);
				logger.info(ele.toString());
			}
			
		}
    	
    	TUIKProcessEngine.destroy();
    }

}

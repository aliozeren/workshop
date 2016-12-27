package tr.gov.tuik.activitilib.samples;

import java.io.IOException;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Comboitem;

import tr.gov.tuik.activitilib.TUIKFormService;
import tr.gov.tuik.activitilib.TUIKProcessEngine;
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
			if (dm.getComponent() instanceof Combobox) {
				Combobox c= (Combobox) dm.getComponent();
				logger.info("Options : ");
				for (Component x : c.getChildren()) {
					logger.info("[" + ((Comboitem)x).getValue() + " , " + ((Comboitem)x).getLabel() + "]");
				}
			}
			logger.info(dm);
		}
    	
    	TUIKProcessEngine.destroy();
    }

}

package tr.gov.tuik.activitilib.zk;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Label;

import tr.gov.tuik.activitilib.types.AbstractCommonFormType;

public class ZKInputUtils 
{

	private final static Logger logger = Logger.getLogger(ZKInputUtils.class);

	private static ZKInputUtils instance;

	private ZKInputUtils() {
	}

	public static ZKInputUtils getInstance() 
	{
		if (instance == null) {
			logger.debug("ZKInputUtils instance is created...");
			instance= new ZKInputUtils();
		}
		return instance;
	}
	
	
	public DynamicModel getDynamicModel(AbstractCommonFormType input,  Component component )
	{	
		Label label= new Label(input.getLabel()); 
		label.setId("label_" + input.getId());
		return  new DynamicModel(label, component);
	}
	
	public HtmlBasedComponent createHtmlBasedComponent(HtmlBasedComponent component, AbstractCommonFormType formType)
	{
		
		assert ( component != null && formType != null);

		
		component.setId(formType.getId());
		component.setHeight(formType.getHeight());
		component.setWidth(formType.getWidth());
		component.setClass(formType.getStyleClass());
//		component.addEventHandler(name, );
		return component;
	}
}

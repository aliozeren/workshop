package tr.gov.tuik.activitilib.zk;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Div;
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
	
	
	public Div getInputDiv(AbstractCommonFormType input)
	{
		Div div= new Div();
		
		div.setId("div_" + input.getId());
		Label label= new Label(input.getLabel());
		label.setId("label_" + input.getId());
		div.appendChild(label);
	
		return div;
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

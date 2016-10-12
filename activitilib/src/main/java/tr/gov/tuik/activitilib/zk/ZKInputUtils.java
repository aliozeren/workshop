package tr.gov.tuik.activitilib.zk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.HtmlBasedComponent;
import org.zkoss.zul.Label;
import org.zkoss.zul.impl.InputElement;

import tr.gov.tuik.activitilib.types.AbstractCommonFormType;
import tr.gov.tuik.activitilib.types.FormToMapConverter;

public class ZKInputUtils implements FormToMapConverter
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


		//		how can we set these properties to a ZK Input
		//		component.setXYZ(formType.isReadable());
		//		component.setXYZ(formType.isReadable());
		//		component.setXYZ(formType.isReadable());

		component.setId(formType.getId());
		component.setHeight(formType.getHeight());
		component.setWidth(formType.getWidth());
		component.setClass(formType.getStyleClass());

		return component;
	}


	public Map<String, String> formToMap(Object formProperties) 
	{
		@SuppressWarnings("unchecked")
		List<DynamicModel> list = (List<DynamicModel>) formProperties;
		
		Map<String, String> map = new HashMap<String, String>();
		for (DynamicModel model : list) {
			Component component= model.getComponent();
			if (component != null && component instanceof InputElement ) {
				map.put(component.getId(), ((InputElement)component).getText());
			}
		}

		return map;	
	}


}

package tr.gov.tuik.activitilib.zk;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.zkoss.zk.ui.Component;
import org.zkoss.zul.Checkbox;
import org.zkoss.zul.Combobox;
import org.zkoss.zul.Label;
import org.zkoss.zul.impl.InputElement;
import org.zkoss.zul.impl.XulElement;

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


	/**
	 * @param input
	 * @param component
	 * @return
	 */
	public DynamicModel getDynamicModel(AbstractCommonFormType input,  Component component )
	{	
		Label label= new Label(input.getLabel()); 
		label.setId("label_" + input.getId());
		return  new DynamicModel(label, component);
	}

	/**
	 * @param component
	 * @param formType
	 * @return
	 */
	public XulElement createHtmlBasedComponent(XulElement component, AbstractCommonFormType formType)
	{

		assert ( component != null && formType != null);

		if (component instanceof InputElement) {
			if (formType.isRequired()) {
				((InputElement) component).setConstraint("no empty");
			}
			if (!formType.isWriteable()) {
				((InputElement) component).setReadonly(true);
			}
		}

		if (!formType.isReadable()) {
			component.setVisible(false);
		}

		component.setId(formType.getId());
		component.setHeight(formType.getHeight());
		component.setWidth(formType.getWidth());
		component.setClass(formType.getStyleClass());

		return component;
	}



	/* (non-Javadoc)
	 * @see tr.gov.tuik.activitilib.types.FormToMapConverter#formToMap(java.lang.Object)
	 */
	public Map<String, String> formToMap(Object formProperties) 
	{
		@SuppressWarnings("unchecked")
		List<DynamicModel> list = (List<DynamicModel>) formProperties;

		Map<String, String> map = new HashMap<String, String>();
		for (DynamicModel model : list) {
			Component component = model.getComponent();
			
			if (component == null) {
				continue;
			}
			
			
			
			if (component instanceof Combobox) {
				if (((Combobox) component).getSelectedItem() != null) {
					map.put(component.getId(), ((Combobox) component).getSelectedItem().getValue().toString());
				}
			} else if (component instanceof Checkbox) {
				if (((Checkbox) component).isChecked()) {
					map.put(component.getId(), "true");
				} else {
					map.put(component.getId(), "false");
				}
				
			} // InputElement condition must be the last condition
			else if (component instanceof InputElement) { 
				map.put(component.getId(), ((InputElement) component).getText());
			}
		}

		return map;
	}


}

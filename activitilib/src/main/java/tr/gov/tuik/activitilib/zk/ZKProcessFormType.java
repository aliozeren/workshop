package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Textbox;

import tr.gov.tuik.activitilib.types.AbstractProcessFormType;

public class ZKProcessFormType extends AbstractProcessFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;


	public DynamicModel renderInput(FormProperty property, String value) 
	{
		Textbox component = new Textbox();
		component= (Textbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		if (value != null) {
			component.setValue(value);
		}
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}
	
}

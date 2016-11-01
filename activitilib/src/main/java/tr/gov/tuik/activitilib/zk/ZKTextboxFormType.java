package tr.gov.tuik.activitilib.zk;

import org.activiti.engine.form.FormProperty;
import org.zkoss.zul.Textbox;

import tr.gov.tuik.activitilib.types.AbstractTextboxFormType;

public class ZKTextboxFormType extends AbstractTextboxFormType  
{

	private static final long serialVersionUID = 3868249214623992954L;


	public DynamicModel renderInput(FormProperty property) 
	{
		Textbox component = new Textbox();
		component= (Textbox) ZKInputUtils.getInstance().createHtmlBasedComponent(component, this);
		
		return  ZKInputUtils.getInstance().getDynamicModel(this, component);
	}
	
}
